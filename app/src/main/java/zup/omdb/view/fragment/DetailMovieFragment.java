package zup.omdb.view.fragment;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;
import zup.omdb.R;
import zup.omdb.control.listener.IBackPressListener;
import zup.omdb.model.bo.MovieBO;
import zup.omdb.model.bo.OmdbBO;
import zup.omdb.model.request.Movie;
import zup.omdb.util.Constants;
import zup.omdb.util.CustomLog;
import zup.omdb.util.DisplayLayout;
import zup.omdb.util.ErrorMessage;
import zup.omdb.util.Fonts;
import zup.omdb.view.listener.IDialogListener;
import zup.omdb.util.ResourceUtils;
import zup.omdb.util.Utils;
import zup.omdb.view.fragment.dialog.DialogWarning;

/**
 * Created by Yumi.
 * <p>
 * <p>
 * Fragmento de detalhe dos filmes
 */
public class DetailMovieFragment extends FragmentAbstract {

    @BindView(R.id.txt_name_movie)
    TextView txtNameMovie;
//    @BindView(R.id.txt_title_movie_og)
//    TextView txtTitleMovieOriginal;
//    @BindView(R.id.txt_name_movie_og)
//    TextView txtNameMovieOriginal;
    @BindView(R.id.image_toolbar)
    ImageView imgPosterMovie;
    @BindView(R.id.img_title_movie)
    ImageView imgTitleMovie;
    @BindView(R.id.img_star)
    ImageView imgStar;
    @BindView(R.id.txt_star)
    TextView txtStar;
    @BindView(R.id.txt_title_rated)
    TextView txtTitleRated;
    @BindView(R.id.txt_name_rated)
    TextView txtNameRated;
    @BindView(R.id.img_metascore)
    ImageView imgMetascore;
    @BindView(R.id.txt_megascore)
    TextView txtMetascore;
//    @BindView(R.id.txt_title_released)
//    TextView txtTitleReleased;
//    @BindView(R.id.txt_name_released)
//    TextView txtNameReleased;
    @BindView(R.id.txt_title_year)
    TextView txtTitleYear;
    @BindView(R.id.txt_name_year)
    TextView txtNameYear;
    @BindView(R.id.txt_name_time)
    TextView txtNameTime;
    @BindView(R.id.txt_title_genre)
    TextView txtTitleGenre;
    @BindView(R.id.txt_name_genre)
    TextView txtNameGenre;
    @BindView(R.id.txt_title_director)
    TextView txtTitleDirector;
    @BindView(R.id.txt_name_director)
    TextView txtNameDirector;
    @BindView(R.id.txt_title_writer)
    TextView txtTitleWriter;
    @BindView(R.id.txt_name_writer)
    TextView txtNameWriter;
    @BindView(R.id.txt_title_actor)
    TextView txtTitleActor;
    @BindView(R.id.txt_name_actor)
    TextView txtNameActor;
    @BindView(R.id.txt_title_language)
    TextView txtTitleLanguage;
    @BindView(R.id.txt_name_language)
    TextView txtNameLanguage;
    @BindView(R.id.txt_title_awards)
    TextView txtTitleAwards;
    @BindView(R.id.txt_name_awards)
    TextView txtNameAwards;
    @BindView(R.id.txt_name_type)
    TextView txtNameType;
    @BindView(R.id.progress_detail)
    ProgressBar progressTitle;
    @BindView(R.id.img_title_holder)
    ImageView imgTitleHolder;
    @BindView(R.id.rl_rated)
    View rlRated;
    @BindView(R.id.rl_director)
    View rlDirector;
    @BindView(R.id.rl_genre)
    View rlGenre;
    @BindView(R.id.rl_actor)
    View rlActor;
    @BindView(R.id.rl_awards)
    View rlAwards;
    @BindView(R.id.rl_language)
    View rlLanguage;
    @BindView(R.id.rl_writer)
    View rlWriter;

    private Movie movie;
    private ImageLoadingListener imageLoaderTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = inflater.inflate(R.layout.detail_screen, null);

        DisplayLayout.startDisplay(this.getContext(), (ViewGroup) findViewById(R.id.layout_detail), Fonts.getRobotoRegular(), Typeface.NORMAL);

        rootView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.main_in_left));
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        normalBackPress();
        startView();
    }

    private void startActionBarScreen(final String text) {
        final AppCompatActivity activity = initActionBar();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (toolbar != null) {
            toolbar.setTitle(text);
        }

        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(final AppBarLayout appBarLayout, final int verticalOffset) {
                if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange() - 35) {
                    collapsingToolbar.setTitleEnabled(false);
                    collapsingToolbar.setTitle(text);
                } else {
                    collapsingToolbar.setTitleEnabled(true);
                    collapsingToolbar.setTitle(" ");
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_detail, menu);
    }

    private void dismissProgress() {
        fragmentListener.showLoading(false);
        normalBackPress();
    }

    public void deleteMovie() {
        try {
            MovieBO.getInstance().deleteMovie(movie);
            fragmentListener.showMainScreen();
        } catch (SQLException e) {
//            e.printStackTrace();
            CustomLog.error("SQLException " + e.getMessage());
            Utils.toast(rootView, ErrorMessage.DATABASE_ERROR);
            dismissProgress();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if (fragmentListener != null) {
                    fragmentListener.showMainScreen();
                }
                return true;
            case R.id.action_delete:
                new DialogWarning(getContext(), ResourceUtils.getStringByRes(R.string.dialog_message), null, null, 1, Constants.NO_AND_YES, new IDialogListener() {
                    @Override
                    public void onClickDialog(int idDialog, boolean confirme, Object... params) {
                        if (confirme) {
                            deleteMovie();
                        } else {
                            dismissProgress();
                        }
                    }
                });
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startView() {
        imageLoaderTitle = new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progressTitle.setVisibility(View.VISIBLE);
                imgTitleHolder.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                progressTitle.setVisibility(View.GONE);
                imgTitleHolder.setVisibility(View.VISIBLE);

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                progressTitle.setVisibility(View.GONE);
                imgTitleHolder.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

                progressTitle.setVisibility(View.GONE);
                imgTitleHolder.setVisibility(View.VISIBLE);
            }
        };

        movie = OmdbBO.getInstance().getItemMovie();

        startActionBarScreen(movie.getTitle());
        txtNameMovie.setText(movie.getTitle());
        txtNameType.setText(movie.getTypeStr());

        Utils.imageLoader(imgPosterMovie, movie.getPath(), null);
        Utils.imageLoader(imgTitleMovie, movie.getPath(), imageLoaderTitle);

        if(Utils.verifyCanShow(movie.getMetascore())){
            imgMetascore.setVisibility(View.VISIBLE);
            txtMetascore.setVisibility(View.VISIBLE);
            txtMetascore.setText(movie.getMetascore());
        } else {
            imgMetascore.setVisibility(View.GONE);
            txtMetascore.setVisibility(View.GONE);
        }
        if(Utils.verifyCanShow(movie.getImdbRating())){
            imgStar.setVisibility(View.VISIBLE);
            txtStar.setVisibility(View.VISIBLE);
            txtStar.setText(movie.getImdbRating());
        } else {
            imgStar.setVisibility(View.GONE);
            txtStar.setVisibility(View.GONE);
        }

        if(Utils.verifyCanShow(movie.getRuntime())) {
            txtNameTime.setText(movie.getRuntime());
        } else {
            txtNameTime.setText("--");
        }

        if(Utils.verifyCanShow(movie.getYear())) {
            txtNameYear.setText(movie.getYear());
        } else {
            txtNameYear.setText("--");
        }

        showExitItem(movie.getGenre(), txtNameGenre, rlGenre);
        showExitItem(movie.getDirector(), txtNameDirector, rlDirector);
        showExitItem(movie.getWriter(), txtNameWriter, rlWriter);
        showExitItem(movie.getActors(), txtNameActor, rlActor);
        showExitItem(movie.getLanguage(), txtNameLanguage, rlLanguage);
        showExitItem(movie.getAwards(), txtNameAwards, rlAwards);
        showExitItem(movie.getRated(), txtNameRated, rlRated);

//        txtTitleMovieOriginal.setTypeface(Fonts.getRobotoMedium());
//        txtTitleReleased.setTypeface(Fonts.getRobotoMedium());
        txtTitleYear.setTypeface(Fonts.getRobotoMedium());
        txtTitleGenre.setTypeface(Fonts.getRobotoMedium());
        txtTitleDirector.setTypeface(Fonts.getRobotoMedium());
        txtTitleWriter.setTypeface(Fonts.getRobotoMedium());
        txtTitleActor.setTypeface(Fonts.getRobotoMedium());
        txtTitleLanguage.setTypeface(Fonts.getRobotoMedium());
        txtTitleActor.setTypeface(Fonts.getRobotoMedium());
        txtTitleAwards.setTypeface(Fonts.getRobotoMedium());
        txtTitleRated.setTypeface(Fonts.getRobotoMedium());
    }

    private void showExitItem(String texto, TextView textView, View view) {
        if(Utils.verifyCanShow(texto)){
            view.setVisibility(View.VISIBLE);
            textView.setText(texto);
        } else {
            view.setVisibility(View.GONE);
        }
    }


    private void normalBackPress() {
        registerBackPress(new IBackPressListener() {
            @Override
            public void onBackPress() {
                if (fragmentListener != null) {
                    fragmentListener.showMainScreen();
                }
            }

            @Override
            public boolean isFirstBack() {
                return false;
            }
        });

    }

}
