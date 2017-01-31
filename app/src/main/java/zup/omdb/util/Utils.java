package zup.omdb.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Locale;

import zup.omdb.control.app.MovieApp;
import zup.omdb.model.request.Movie;

/**
 * Created by Yumi.
 * <p>
 * <p>
 * Classe de utilidade - contem códigos padrões
 */
public class Utils {

    private static transient Snackbar toastExitApplication;

    public static TextView configToolbarTitleView(AppCompatActivity activity, Toolbar toolbar) {
        final ActionBar actionBar = activity.getSupportActionBar();
        CharSequence actionbarTitle = null;

        if (actionBar != null) {
            actionbarTitle = actionBar.getTitle();
        }

        actionbarTitle = TextUtils.isEmpty(actionbarTitle) ? toolbar.getTitle() : actionbarTitle;
        if (TextUtils.isEmpty(actionbarTitle)) {
            return null;
        }

        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                CharSequence title = textView.getText();
                if (!TextUtils.isEmpty(title) && actionbarTitle.equals(title) && textView.getId() == View.NO_ID) {
                    textView.setTypeface(Fonts.getRobotoMedium(), Typeface.NORMAL);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, DisplayLayout.displayWidth(activity, 36));
                    return textView;
                }
            }
        }
        return null;
    }

    public static void exitApplication(final Activity activity, View viewGroup) {
        if (toastExitApplication == null && viewGroup != null && activity != null) {
            final android.content.res.Resources res = activity.getResources();

            toastExitApplication = Snackbar.make(viewGroup, ErrorMessage.EXIT_MESSAGE, Snackbar.LENGTH_SHORT).setAction(Constants.ACTION, null);
            toastExitApplication.show();
            MovieApp.getInstance().getHandler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    toastExitApplication = null;
                }
            }, 1800);
        } else {
            toastExitApplication.dismiss();
            toastExitApplication = null;
            activity.finish();
        }
    }

    public static void toast(View viewGroup, String message) {
        Snackbar toast = Snackbar.make(viewGroup, message, Snackbar.LENGTH_LONG).setAction(Constants.ACTION, null);
        toast.show();
    }

    public static void showKeyBoard(Context context, boolean show, EditText deviceName) {
        if (context == null) {
            context = MovieApp.getContext();
        }
        if (show) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
//            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.showSoftInput(deviceName, InputMethodManager.SHOW_IMPLICIT);
        } else {
            if (deviceName != null) {
                deviceName.clearFocus();
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(deviceName.getWindowToken(), 0);
            } else {
                InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }


        }
    }

    public static void toast(Context context, String text, int time) {
        if (context == null) {
            return;
        }
        final Toast toast = Toast.makeText(context, text, time);
        toastMakeText(toast);
    }

    private static void toastMakeText(final Toast toast) {
        final View view = toast.getView();

        if (view instanceof ViewGroup) {
            final ViewGroup viewGroup = (ViewGroup) view;

            final View viewChild = viewGroup.getChildAt(0);

            if (viewChild instanceof TextView) {
                final TextView txView = (TextView) viewChild;
                txView.setGravity(android.view.Gravity.CENTER);
            }

            toast.setView(viewGroup);
            toast.show();
        }
    }

    public static String saveFile(Movie movie) {
        final File directory = getDir();

        if (!directory.exists() && !directory.mkdirs()) {
            CustomLog.error("Can't create directory to save image");
            return "";
        }

        String newName = Constants.FILE_NAME + System.currentTimeMillis() + Constants.EXTENSION;
        final String fileName = directory.getPath() + File.separator + newName;
        Uri uri = Uri.fromFile(new File(fileName));
        CustomLog.error("---Uri" + uri.toString());
        movie.setPath(uri.toString());
        return newName;
    }

    private static File getDir() {
        return new File(Environment.getExternalStorageDirectory(), Constants.FILE_DIR_NAME);
    }

    public static void imageLoader(ImageView imageView, String url, ImageLoadingListener imageLoadingListener) {

        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true).build();

        //download and display image from url
        imageLoader.displayImage(url, imageView, options, imageLoadingListener);
    }

    public static void checkSMSPermission(Activity activity, int myPermissionsRequestSmsSendNumber) {
        if (activity != null) {
            CustomLog.error(" [" + myPermissionsRequestSmsSendNumber + "]MSG PASS 0 ");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)) {
                // Do something for KITKAT and above versions

                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    CustomLog.error(" [" + myPermissionsRequestSmsSendNumber + "]MSG PASS 1");
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    ActivityCompat.requestPermissions(activity, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, myPermissionsRequestSmsSendNumber);
                } else {

                    // No explanation needed, we can request the permission.
                    CustomLog.error(" [" + myPermissionsRequestSmsSendNumber + "]MSG PASS 2");
                    ActivityCompat.requestPermissions(activity, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, myPermissionsRequestSmsSendNumber);

                    // MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
        }
    }

    public static Configuration setAppLocale(final String language, final Context context) {
        final Locale appLocale = new Locale(language);
        Locale.setDefault(appLocale);
        final Configuration configuration = new Configuration();
        configuration.locale = appLocale;
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        return configuration;
    }

    public static String maxSizeText(String movie, int sizeMax) {
        if (StringUtils.isNotEmpty(movie) && movie.length() > sizeMax) {
            return movie.substring(0, sizeMax) + "...";
        }
        return movie;
    }

    public static boolean verifyCanShow(String texto) {
        CustomLog.error("verifyCanShow " + texto);
        if(StringUtils.isNotEmpty(texto) && !texto.trim().equalsIgnoreCase("N/A")){
            CustomLog.error("verifyCanShow true");
            return true;
        }
        CustomLog.error("verifyCanShow false");
        return false;
    }
}
