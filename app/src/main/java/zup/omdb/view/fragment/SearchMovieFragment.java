package zup.omdb.view.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zup.omdb.R;
import zup.omdb.control.enumaration.EnumPlotMovie;
import zup.omdb.control.enumaration.EnumResponseMovie;
import zup.omdb.control.listener.IBackPressListener;
import zup.omdb.model.bo.MovieBO;
import zup.omdb.model.bo.OmdbBO;
import zup.omdb.model.endpoint.MovieEndpoint;
import zup.omdb.model.request.Movie;
import zup.omdb.model.service.ApiClient;
import zup.omdb.util.CustomLog;
import zup.omdb.util.DisplayLayout;
import zup.omdb.util.ErrorMessage;
import zup.omdb.util.Fonts;
import zup.omdb.util.ResourceUtils;
import zup.omdb.util.Utils;

/**
 * Created by Yumi.
 * <p>
 * <p>
 * Fragmento Principal
 */
public class SearchMovieFragment extends FragmentAbstract implements View.OnClickListener {

    private static final int MAX_SIZE = 16;
    @BindView(R.id.edt_movie_name)
    EditText edtMovie;
    @BindView(R.id.bt_confirm_search)
    Button btConfirmSearch;
    private Movie movieData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = inflater.inflate(R.layout.search_screen, null);

        DisplayLayout.startDisplay(this.getContext(), (ViewGroup) findViewById(R.id.layout_search), Fonts.getRobotoRegular(), Typeface.NORMAL);

        rootView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.main_in_left));
        registerBackPress(null);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        startActionBarScreen();
        startView();
    }

    private void startActionBarScreen() {

        final AppCompatActivity activity = initActionBar();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (toolbar != null) {
            toolbar.setTitle(ResourceUtils.getStringByRes(R.string.search_title));
        }
    }

    private void startView() {
        Utils.showKeyBoard(getContext(), true, edtMovie);

        edtMovie.setText("");

        btConfirmSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_confirm_search:
                if (verifyEditText()) {
                    String movie = edtMovie.getText().toString().trim();
                    try {
                        final Movie foundMovie = MovieBO.getInstance().getMovieByName(movie);
                        if (foundMovie == null) {
                            goneKeyboard();
                            downloadMovie(movie);
                        } else {
                            Utils.toast(rootView, String.format(ErrorMessage.EXIST_ITEM, Utils.maxSizeText(foundMovie.getTitle(), MAX_SIZE)));
                        }
                    } catch (SQLException e) {
                        CustomLog.error("getMovieByName " + e.getMessage());
                        e.printStackTrace();
                        goneKeyboard();
                        downloadMovie(movie);
                    }
                } else {
                    Utils.toast(rootView, ErrorMessage.TEXT_INCOMPLETE);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onPause() {
        goneKeyboard();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        goneKeyboard();
        super.onDestroy();
    }

    private void goneKeyboard() {
        edtMovie.clearFocus();
        Utils.showKeyBoard(getContext(), false, edtMovie);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if (fragmentListener!= null) {
                    fragmentListener.showMainScreen();
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void downloadMovie(final String movie) {
        showLoading();
        MovieEndpoint apiService = ApiClient.getClient().create(MovieEndpoint.class);
        Call<Movie> match = apiService.getDataMovie(movie, EnumPlotMovie.FULL.getName(), EnumResponseMovie.JSON.getName());
        match.enqueue(new Callback<Movie>() {
                          @Override
                          public void onResponse(Call<Movie> call, Response<Movie> response) {
                              if (response != null) {
                                  if (response.isSuccessful()) {
                                      if (response.body() != null) {
                                          movieData = response.body();
                                          if (StringUtils.isEmpty(movieData.getResponse()) || movieData.getResponse().equalsIgnoreCase("false")) {
                                              //RESPOSTA FALSE
                                              Utils.toast(rootView, String.format(ErrorMessage.MOVIE_NOT_FOUND, Utils.maxSizeText(movie, MAX_SIZE)));
                                          } else {
                                              if (MovieBO.getInstance().getMovieByImdbID(movieData.getImdbID()) == null) {
                                                  //RESPOSTA TRUE
                                                  movieData.setName(movie);

                                                  CustomLog.error("MovieEndpoint " + movieData.getName());
                                                  CustomLog.error("MovieEndpoint " + movieData.getTitle());
                                                  CustomLog.error("MovieEndpoint " + movieData.getPoster());

                                                  boolean okDownload = OmdbBO.getInstance().downloadImage(movieData, getContext());
                                                  try {
                                                      MovieBO.getInstance().saveMovie(movieData);

                                                  } catch (SQLException e) {
                                                      CustomLog.error("SQLException " + e.getMessage());

                                                      Utils.toast(rootView, ErrorMessage.DATABASE_ERROR);
                                                  }
                                                  CustomLog.error("okDownload " + okDownload);
                                                  if (okDownload) {
                                                      //Continua mostrando o LOADING e espera o retorno do download Receiver
                                                      return;
                                                  } else {
                                                      Utils.toast(rootView, ErrorMessage.IMAGE_DOWNLOAD);
                                                      showDetailScreen();
                                                  }
                                              } else {
                                                  Utils.toast(rootView, String.format(ErrorMessage.EXIST_ITEM, Utils.maxSizeText(movie, MAX_SIZE)));
                                              }
                                          }
                                      }
                                  } else {
                                      Utils.toast(rootView, String.format(ErrorMessage.ERROR_UNKNOWN, response.errorBody()));
                                  }

                              }

                              goneLoading();
                          }

                          @Override
                          public void onFailure(Call<Movie> call, Throwable t) {
                              CustomLog.error("onFailure Throwable " + t.getMessage() + " cause " + t.getCause());
                              goneLoading();
                              Utils.toast(rootView, ErrorMessage.ERROR_INTERNET);
                          }
                      }

        );
    }

    private void goneLoading() {
        fragmentListener.showLoading(false);
        registerBackPress(null);
    }

    private void showLoading() {
        fragmentListener.showLoading(true);
        registerBackPress(new IBackPressListener() {
            @Override
            public void onBackPress() {
                //DO NOTHING
            }

            @Override
            public boolean isFirstBack() {
                return false;
            }
        });
    }


    private boolean verifyEditText() {
        if (edtMovie.getText() != null && edtMovie.getText().toString().trim().length() > 0) {
            return true;
        }
        return false;
    }

    public void completedDownload(boolean status) {
        CustomLog.error("completedDownload " + status);
        goneLoading();
        if (!status) {
            Utils.toast(rootView, ErrorMessage.ERROR_DOWNLOAD);
        }
        showDetailScreen();
    }

    private void showDetailScreen() {
        OmdbBO.getInstance().setItemMovie(movieData);
        fragmentListener.showDetailMovieScreen();

    }
}
