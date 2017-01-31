package zup.omdb.view.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zup.omdb.R;
import zup.omdb.control.listener.IBackPressListener;
import zup.omdb.model.bo.MovieBO;
import zup.omdb.model.bo.OmdbBO;
import zup.omdb.model.request.Movie;
import zup.omdb.util.CustomLog;
import zup.omdb.util.DisplayLayout;
import zup.omdb.util.Fonts;
import zup.omdb.util.ResourceUtils;
import zup.omdb.util.Utils;
import zup.omdb.view.adapter.MainAdapter;
import zup.omdb.view.listener.IMainFragment;

/**
 * Created by Yumi.
 * <p>
 * <p>
 * Fragmento Principal
 */
public class MainFragment extends FragmentAbstract implements View.OnClickListener, IMainFragment {

    @BindView(R.id.float_button)
    FloatingActionButton fab;
    @BindView(R.id.menu_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.nested_scroll_main)
    View scrollMain;
    @BindView(R.id.rl_no_item)
    View viewNoItem;

    private MainAdapter adapter;
    private List<Movie> itemList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = inflater.inflate(R.layout.main_screen, null);

        DisplayLayout.startDisplay(this.getContext(), (ViewGroup) findViewById(R.id.layout_main), Fonts.getRobotoRegular(), Typeface.NORMAL);

        rootView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.main_in_left));
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        startActionBarScreen();
        exitBackPress();
        startView();
    }

    private void startActionBarScreen() {

        final AppCompatActivity activity = initActionBar();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        if (toolbar != null) {
            toolbar.setTitle(ResourceUtils.getStringByRes(R.string.app_name));
        }
    }

    private void startView() {
        try {
            itemList = MovieBO.getInstance().getAllPersistedMovies();
        } catch (SQLException e) {
            e.printStackTrace();
            CustomLog.error("ERROR " + e.getMessage());
            itemList = new ArrayList<>();
        }

        visibilityList();

        adapter = new MainAdapter(getActivity(), itemList, this);

        if (mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(llm);
            mRecyclerView.setAdapter(adapter);
        }

        fab.setOnClickListener(this);
    }

    private void visibilityList() {

        if (itemList == null || itemList.size() <= 0) {
            scrollMain.setVisibility(View.GONE);
            viewNoItem.setVisibility(View.VISIBLE);
        } else {
            scrollMain.setVisibility(View.VISIBLE);
            viewNoItem.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            fragmentListener.showAboutScreen();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.float_button:
                fragmentListener.showSearchMovieScreen();
                break;
            default:
                break;
        }
    }

    @Override
    public void setOnClick(int posit) {
        OmdbBO.getInstance().setItemMovie(itemList.get(posit));

        fragmentListener.showDetailMovieScreen();
    }

    private void exitBackPress() {
        registerBackPress(new IBackPressListener() {
            @Override
            public void onBackPress() {
                if (getActivity() != null) {
                    Utils.exitApplication(getActivity(), rootView);
                }
            }

            @Override
            public boolean isFirstBack() {
                return false;
            }
        });
    }
}
