package zup.omdb.util;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Yumi.
 * <p>
 * <p>
 * Inicializa as fontes customizadas que serão usadas
 */
public final class Fonts {


    private Fonts() {
        // do nothing
    }

    private static Typeface robotoRegular;
    private static Typeface robotoBold;
    private static Typeface robotoMedium;

    /**
     * Method to initiate system's font
     * @param context - Application context
     */
    public static void initFonts(final Context context) {
        robotoRegular		= Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        robotoBold			= Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
        robotoMedium		= Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
    }

    public static Typeface getRobotoMedium() {
        return robotoMedium;
    }

    public static Typeface getRobotoBold() {
        return robotoBold;
    }

    public static Typeface getRobotoRegular() {
        return robotoRegular;
    }
}