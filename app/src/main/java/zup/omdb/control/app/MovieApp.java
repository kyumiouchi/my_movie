package zup.omdb.control.app;

import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDexApplication;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import zup.omdb.model.database.DataBaseManager;

/**
 * Created by Yumi.
 * <p>
 * <p>
 * Classe aplication
 */

public class MovieApp extends MultiDexApplication {

    private static MovieApp singleton;
    private static Context appContext;
    private Handler handler;
    private MovieApp context;

    public static final boolean DEBUG_ENVIRONMENT = false;

    @Override
    public void onCreate() {
        super.onCreate();

        initialize(this);
        setContext(this);

        handler = new Handler();

        imageLoader();
        startDataBase();
    }

    private void startDataBase() {
        DataBaseManager.init(MovieApp.this);
    }

    private void imageLoader() {

        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP
    }

    private void initialize(MovieApp scoreboardApp) {
        singleton = scoreboardApp;
    }

    public static MovieApp getInstance() {
        return singleton;
    }


    private void setContext(Context appContext) {
        this.appContext = appContext;
    }

    public static Context getContext() {
        return appContext;
    }

    public Handler getHandler() {
        return handler;
    }
}
