package zup.omdb.util;

import zup.omdb.control.app.MovieApp;

/**
 * Created by Yumi.
 * <p>
 * <p>
 * Classe de auxilio de resources
 */
public class ResourceUtils {

    public static String getStringByRes(final int res) {
        if (res != 0) {
            try {
                return MovieApp.getContext().getResources().getString(res);
            } catch (Exception e) {
                CustomLog.error("Resource not found exeption getStringByRes");
                return "";
            }
        } else {
            CustomLog.error("Resource not found exeption getStringByRes");
            return "";
        }
    }
}
