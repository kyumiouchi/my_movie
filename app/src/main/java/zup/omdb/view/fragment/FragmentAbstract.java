package zup.omdb.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import zup.omdb.R;
import zup.omdb.control.activity.FragmentActivityAbstract;
import zup.omdb.control.listener.IBackPressListener;
import zup.omdb.control.listener.IMovieActivityListener;
import zup.omdb.util.Utils;

public class FragmentAbstract extends Fragment {
    protected transient View rootView;
    protected Toolbar toolbar;

    public IMovieActivityListener fragmentListener;

    public FragmentAbstract() {
        setRetainInstance(true);
    }

    public Context getContext() {
        return getActivity();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewCompat.requestApplyInsets(view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if ( getActivity() != null && getActivity() instanceof IMovieActivityListener) {
            fragmentListener = (IMovieActivityListener) getActivity();
        }
    }

    public AppCompatActivity initActionBar() {
        if (rootView != null && getActivity() instanceof AppCompatActivity) {
            final AppCompatActivity activity = (AppCompatActivity) getActivity();
            toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
            final ActionBar ab = activity.getSupportActionBar();

            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(true);
            }
            setHasOptionsMenu(true);//create menu

            Utils.configToolbarTitleView(activity, toolbar);

            return activity;
        } else {
            return null;
        }
    }

    public View findViewById(int resId) {

        View objRet = null;

        if(rootView != null) {
            objRet = rootView.findViewById(resId);
        }

        return objRet;
    }

    public void registerBackPress(final IBackPressListener backPressListener) {
        if (getActivity() instanceof FragmentActivityAbstract) {
            ((FragmentActivityAbstract) getActivity()).registerBackPress(backPressListener);
        }
    }

}
