package zup.omdb.control.activity;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import zup.omdb.R;
import zup.omdb.control.app.MovieApp;
import zup.omdb.control.enumaration.Frags;
import zup.omdb.model.bo.OmdbBO;
import zup.omdb.util.Constants;
import zup.omdb.util.DisplayLayout;
import zup.omdb.util.Utils;
import zup.omdb.view.fragment.MainFragment;
import zup.omdb.view.fragment.SearchMovieFragment;

/**
 * Created by Yumi.
 * <p>
 * <p>
 * Activity Principal que controla o fluxo dos fragmentos
 */
public class MainActivity extends FragmentActivityAbstract {

    @BindView(R.id.progress_init_app)
    View viewLoading;
    private BroadcastReceiver onComplete;
    @BindView(R.id.splash)
    View splashScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        DisplayLayout.startDisplay(this, (ViewGroup) findViewById(R.id.layout_main_activity));
        initSplashScreen(true);

        showLoading(false);
        showMainScreen();


        registerBroadcast();
    }

    @Override
    public void showLoading(boolean visible) {
        if (!visible) {
            viewLoading.setVisibility(View.GONE);
        } else {
            viewLoading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showMainScreen() {
        popBackStackStart();
        registerFragment(Frags.MAIN);
        replaceFragment(Frags.MAIN, false);
    }

    @Override
    public void showSearchMovieScreen() {
        registerFragment(Frags.SEARCH);
        replaceFragment(Frags.SEARCH, true);
    }

    @Override
    public void showDetailMovieScreen() {
        registerFragment(Frags.DETAIL);
        replaceFragment(Frags.DETAIL, true);
    }

    @Override
    public void showAboutScreen() {
        registerFragment(Frags.ABOUT);
        replaceFragment(Frags.ABOUT, true);
    }

    private void registerBroadcast() {
        onComplete = new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {
                OmdbBO.getInstance().queryStatus();
                if (Frags.SEARCH.getName().equals(recentFragmentName())) {
                    SearchMovieFragment fragment = (SearchMovieFragment) getFragment(Frags.SEARCH);
                    if (fragment == null) {
                        return;
                    }

                    fragment.completedDownload(OmdbBO.getInstance().queryStatus());
                }

            }
        };
        registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(onComplete);
    }

    private void initSplashScreen(boolean delaySplashScreen) {

        final Activity activity = this;

        if (delaySplashScreen) {

            MovieApp.getInstance().getHandler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    splashScreen.setVisibility(View.GONE);
                    Utils.checkSMSPermission(activity, Constants.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                }
            }, Constants.splashDelay);
        } else {
            splashScreen.setVisibility(View.GONE);
            Utils.checkSMSPermission(this, Constants.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isSuccessful = true;

        switch (requestCode) {
            case Constants.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
                MainFragment fragment = (MainFragment) getFragment(Frags.MAIN);
                if (fragment == null) {
                    return;
                }
                if (permissions != null && permissions.length > 0 && grantResults != null && grantResults.length > 0) {
                    for (int item : grantResults) {
                        if (item < 0) {
                            isSuccessful = false;
                            break;
                        }
                    }
                }
                if (isSuccessful) {
                    //fragment.allowedPermission(requestCode);
                } else {
                    this.finish();
                }
                break;
            default:
                break;
        }


    }
}
