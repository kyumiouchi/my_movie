package zup.omdb.view.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import butterknife.ButterKnife;
import zup.omdb.R;
import zup.omdb.control.listener.IBackPressListener;
import zup.omdb.util.DisplayLayout;
import zup.omdb.util.Fonts;

/**
 * Created by Yumi.
 * <p>
 * <p>
 * Fragmento About da aplicação
 */

public class AboutFragment extends FragmentAbstract  {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = inflater.inflate(R.layout.about_screen, null);

        DisplayLayout.startDisplay(this.getContext(), (ViewGroup) findViewById(R.id.layout_about), Fonts.getRobotoRegular(), Typeface.NORMAL);

        rootView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.main_in_left));
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        normalBackPress();
        startView();
        startActionBarScreen();
    }

    private void startActionBarScreen() {
        final AppCompatActivity activity = initActionBar();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (toolbar != null) {
            toolbar.setTitle(R.string.action_about);
        }
    }


//    private void dismissProgress() {
//        fragmentListener.showLoading(false);
//        normalBackPress();
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if(fragmentListener != null){
                    fragmentListener.showMainScreen();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startView() {
        //DO NOTHING
    }


    private void normalBackPress() {
        registerBackPress(new IBackPressListener() {
            @Override
            public void onBackPress() {
                if(fragmentListener != null){
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
