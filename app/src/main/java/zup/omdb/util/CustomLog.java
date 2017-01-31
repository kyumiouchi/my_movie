package zup.omdb.util;

import android.util.Log;

import zup.omdb.R;
import zup.omdb.control.app.MovieApp;

/**
 * Created by Yumi.
 *
 *
 * Classe para controle de exibição d Log
 */
public class CustomLog {

    private static final boolean LOG_ENABLE = MovieApp.DEBUG_ENVIRONMENT;
    private static final String LOG_TAG = ResourceUtils.getStringByRes(R.string.app_name);
    private static int MAX_LOG_SIZE = 2000;

    public static void error(String msg) {
        if (LOG_ENABLE) {
            if(msg.length() > MAX_LOG_SIZE){
                for(int i = 0; i <= msg.length() / MAX_LOG_SIZE; i++) {
                    int start = i * MAX_LOG_SIZE;
                    int end = (i+1) * MAX_LOG_SIZE;
                    end = end > msg.length() ? msg.length() : end;
                    Log.e(LOG_TAG, msg.substring(start, end));
                }
            } else {
                Log.e(LOG_TAG, msg);
            }
        }
    }
}
