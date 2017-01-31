package zup.omdb.control.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import java.util.HashMap;
import java.util.Map;

import zup.omdb.R;
import zup.omdb.control.enumaration.Frags;
import zup.omdb.control.listener.IBackPressListener;
import zup.omdb.control.listener.IMovieActivityListener;
import zup.omdb.util.CustomLog;
import zup.omdb.util.Fonts;
import zup.omdb.view.fragment.FragmentAbstract;

/**
 * Created by Yumi.
 * <p>
 * <p>
 * Registra os Fragmentos e o BackPress
 */
public abstract class FragmentActivityAbstract extends AppCompatActivity implements IMovieActivityListener {

    public FragmentManager fragMan = null;
    private Map<String, FragmentAbstract> mapFrags = new HashMap<String, FragmentAbstract>();
    private FragmentAbstract frag;

    protected IBackPressListener iBackPressListener;

    private boolean onPause;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        Fonts.initFonts(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        fragMan = getSupportFragmentManager();
    }

    public void registerBackPress(final IBackPressListener iBackPressListener) {
        this.iBackPressListener = iBackPressListener;
    }

    public IBackPressListener getBackPressListener() {
        return iBackPressListener;
    }

    public boolean isOnPause() {
        return onPause;
    }

    public void setOnPause(final boolean onPause) {
        this.onPause = onPause;
    }

    @Override
    protected void onPause() {
        super.onPause();
        setOnPause(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setOnPause(false);
    }

    //Register
    public FragmentAbstract registerFragment(final Frags frag) {
        return registerFragment(frag, null);
    }

    public FragmentAbstract registerFragment(final Frags frag, final String tagFragMan) {

        String tag = tagFragMan;
        if (tag == null) {
            tag = frag.getName();
        }

        final Fragment fragment = fragMan.findFragmentByTag(tag);

        FragmentAbstract abstractFragment = null;

        if (fragment == null) {
            try {
                abstractFragment = frag.getClassFrag().newInstance();
            } catch (InstantiationException e) {
                CustomLog.error("InstantiationException registerFragment " + e.getMessage());
            } catch (IllegalAccessException e) {
                CustomLog.error("IllegalAccessException registerFragment " + e.getMessage());
            }
        } else {
            if (fragment instanceof FragmentAbstract) {
                abstractFragment = (FragmentAbstract) fragment;
            }
        }

        if (abstractFragment != null) {
            this.frag = abstractFragment;
            mapFrags.put(frag.getName(), abstractFragment);
        }

        return abstractFragment;
    }

    //Remove
    protected void removeFragment(Fragment fragment) {

        FragmentTransaction fragTransa = fragMan.beginTransaction();

        fragTransa.remove(fragment);

        fragTransa.commitAllowingStateLoss();
    }

    //Replace
    public void replaceFragment(final Frags frag, final boolean addBackStack) {
        replaceFragment(frag, R.id.content_frame, addBackStack);
    }

    public boolean replaceFragment(final Frags frag, final int resId, final boolean addBackStack) {
        return replaceFragment(frag, resId, addBackStack, null, null);
    }

    public boolean replaceFragment(final Frags frag, final int resId, final boolean addBackStack, final Integer animResIdEnter, final Integer animResIdExit) {

        if (isOnPause()) {
            return false;
        }

        boolean ret = false;

        Fragment fragment = mapFrags.get(frag.getName());

        if (fragment != null) {

            FragmentTransaction fragTransa = fragMan.beginTransaction();

            // setting animation
            if (animResIdEnter != null && animResIdExit != null) {
                fragTransa.setCustomAnimations(animResIdEnter, animResIdExit);
            }

            fragTransa.replace(resId, fragment, frag.getName());


            // adding backstack
            if (addBackStack) {
                fragTransa.addToBackStack(frag.getName());
            }

            fragTransa.commitAllowingStateLoss();
            ret = true;
        }

        return ret;
    }

    @Override
    public void onBackPressed() {
        if (iBackPressListener != null) {
            iBackPressListener.onBackPress();

            if (iBackPressListener != null && iBackPressListener.isFirstBack()) {
                super.onBackPressed();
            }

        } else {
            super.onBackPressed();
        }
    }

    public void popBackStack() {
        try {
            fragMan.popBackStackImmediate();
        } catch (IllegalStateException ignored) {
            // There's no way to avoid getting this if saveInstanceState has already been called.
            CustomLog.error("EXCEPTION PopBackStack IllegalStateException: " + ignored.getMessage());
        }
    }

    public void popBackStackStart() {

        if (fragMan.getBackStackEntryCount() > 0) {
            try {
                fragMan.popBackStackImmediate(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } catch (IllegalStateException ignored) {
                // There's no way to avoid getting this if saveInstanceState has already been called.
                CustomLog.error("EXCEPTION PopBackStack IllegalStateException: " + ignored.getMessage());
            }

        }
    }

    public String recentFragmentName() {
        if (fragMan.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry entry = fragMan.getBackStackEntryAt(fragMan.getBackStackEntryCount()-1);
            return entry.getName();
        }
        return "";
    }

    public synchronized FragmentAbstract getFragment(final Frags frag) {

        if (!mapFrags.containsKey(frag.getName())) {
            return null;
        }

        return mapFrags.get(frag.getName());
    }


    public FragmentAbstract getFrag() {
        return frag;
    }
}
