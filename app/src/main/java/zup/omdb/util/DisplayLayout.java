package zup.omdb.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public final class DisplayLayout {

    private static final transient int DEFAULT_HEIGHT = 1230;
    private static final transient int DEFAULT_WIDTH = 720;
    private static final transient int TOP_BAR = 0;

    private static transient int heightInit = 1230;
    private static transient int widthInit = 720;
    private static transient Integer heightEnd;
    private static transient Integer widthEnd;
    private static transient Typeface font;
    private static transient int fontStyle;
    private static transient StandardOrientationEnum standardOrientationEnum;

    private DisplayLayout() {
    }

    public static int getWidthInit() {
        return widthInit;
    }

    public static void setWidthInit(int widthInit) {
        widthInit = widthInit;
    }

    public static Integer getHeightEnd() {
        return heightEnd;
    }

    public static void setHeightEnd(Integer heightEnd) {
        heightEnd = heightEnd;
    }

    public static void startDisplay(Context context, ViewGroup viewGroup) throws IllegalArgumentException {
        if(context != null && viewGroup != null) {
            startMetrics(context);
            changeViewHeightWidth(viewGroup);
        } else {
            throw new IllegalArgumentException("The Context or ViewGroup is null");
        }
    }

    public static void setFont(ViewGroup viewGroup, Typeface typeface, int typefaceStyle) throws IllegalArgumentException {
        if(viewGroup == null) {
            throw new IllegalArgumentException("The Context or ViewGroup is null");
        } else {
            setFont(typeface, typefaceStyle);
            setFont(viewGroup);
        }
    }

    public static void startDisplayByWidth(Context context, ViewGroup viewGroup) throws IllegalArgumentException {
        if(context != null && viewGroup != null) {
            startMetrics(context);
            changeViewByWidthValue(viewGroup);
        } else {
            throw new IllegalArgumentException("The Context or ViewGroup is null");
        }
    }

    public static void startDisplayByWidth(Context context, ViewGroup viewGroup, Typeface typeface, int typefaceStyle) throws IllegalArgumentException {
        if(context != null && viewGroup != null) {
            setFont(typeface, typefaceStyle);
            startDisplayByWidth(context, viewGroup);
        } else {
            throw new IllegalArgumentException("The Context or ViewGroup is null");
        }
    }

    public static void startDisplay(Context context, ViewGroup viewGroup, Typeface typeface, int typefaceStyle) throws IllegalArgumentException {
        if(context != null && viewGroup != null) {
            setFont(typeface, typefaceStyle);
            startDisplay(context, viewGroup);
        } else {
            throw new IllegalArgumentException("The Context or ViewGroup is null");
        }
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        if(context instanceof Activity) {
            ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        } else if(context instanceof Application) {
            displaymetrics = context.getResources().getDisplayMetrics();
        }

        return displaymetrics;
    }

    private static boolean verifyNotWrapMatch(int param) {
        return param != -2 && param != -1;
    }

    private static int getTopBar(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return resourceId > 0 && context.getResources().getDimensionPixelSize(resourceId) != 48?context.getResources().getDimensionPixelSize(resourceId):0;
    }

    public static void startMetrics(Context context) {
        int widthDisplay = widthInit;
        int heightDisplay = heightInit;
        if(standardOrientationEnum != null) {
            if(standardOrientationEnum.equals(StandardOrientationEnum.PORTRAIT)) {
                widthDisplay = widthDisplay;
                heightDisplay = heightDisplay;
            } else if(standardOrientationEnum.equals(StandardOrientationEnum.LANDSCAPE)) {
                heightDisplay = widthDisplay;
                widthDisplay = heightDisplay;
            }
        } else {
            widthDisplay = widthDisplay;
            heightDisplay = heightDisplay;
        }

        DisplayMetrics displaymetrics = getDisplayMetrics(context);
        int heightTopBar = getTopBar(context);

        heightEnd = Integer.valueOf(displaymetrics.heightPixels - heightTopBar);
        widthEnd = Integer.valueOf(displaymetrics.widthPixels);

    }

    private static void changeViewHeightWidth(ViewGroup views) throws IllegalArgumentException {
        setValuesWidthHeight(views);
        setMargins(views);
        setPadding(views);

        for(int i = 0; i < views.getChildCount(); ++i) {
            changeViewWidthHeight(views.getChildAt(i));
        }

    }

    private static void changeViewByWidthValue(ViewGroup views) throws IllegalArgumentException {
        setWidthValue(views);
        setMargins(views);
        setPadding(views);

        for(int i = 0; i < views.getChildCount(); ++i) {
            changeViewByWidth(views.getChildAt(i));
        }

    }

    private static void setFont(ViewGroup views) throws IllegalArgumentException {
        for(int i = 0; i < views.getChildCount(); ++i) {
            setFont(views.getChildAt(i));
        }

    }

    private static void setFont(View view) throws IllegalArgumentException {
        if(view == null) {
            throw new IllegalArgumentException("The view is null");
        } else {
            if(view instanceof ViewGroup) {
                setFont((ViewGroup)view);
            } else if(view instanceof TextView) {
                TextView textView = (TextView)view;
                textView.setTypeface(Typeface.DEFAULT, 0);
                if(font != null) {
                    textView.setTypeface(font, fontStyle);
                }
            }

        }
    }

    private static void changeViewWidthHeight(View view) throws IllegalArgumentException {
        if(view == null) {
            throw new IllegalArgumentException("The view is null");
        } else {
            if(view instanceof ViewGroup) {
                changeViewHeightWidth((ViewGroup)view);
            } else {
                setValuesWidthHeight(view);
                setMargins(view);
                setPadding(view);
                if(view instanceof TextView) {
                    TextView textView = (TextView)view;
                    setTextSize(textView);
                    textView.setTypeface(Typeface.DEFAULT, 0);
                    if(font != null) {
                        textView.setTypeface(font, fontStyle);
                    }
                }
            }

        }
    }

    private static void changeViewByWidth(View view) throws IllegalArgumentException {
        if(view == null) {
            throw new IllegalArgumentException("The view is null");
        } else {
            if(view instanceof ViewGroup) {
                changeViewByWidthValue((ViewGroup)view);
            } else {
                setWidthValue(view);
                setMargins(view);
                setPadding(view);
                if(view instanceof TextView) {
                    TextView textView = (TextView)view;
                    setTextSize(textView);
                    textView.setTypeface(Typeface.DEFAULT, 0);
                    if(font != null) {
                        textView.setTypeface(font, fontStyle);
                    }
                }
            }

        }
    }

    private static void setTextSize(TextView textView) {
        if(textView != null) {
            textView.setTextSize(0, (float) displayWidth((double)textView.getTextSize()));
        }

    }

    private static int displayHeight(double value) {
        return (int) Math.round((double) heightEnd.intValue() * value / (double) heightInit);
    }

    private static int displayWidth(double value) {
        int width = DisplayLayout.widthEnd.intValue();
        if(standardOrientationEnum != null) {
            if(StandardOrientationEnum.PORTRAIT.equals(standardOrientationEnum) && DisplayLayout.widthEnd.intValue() > heightEnd.intValue()) {
                width = heightEnd.intValue();
            } else if(heightEnd.intValue() > DisplayLayout.widthEnd.intValue()) {
                width = heightEnd.intValue();
            }
        }

        return (int) Math.round((double)width * value / (double) widthInit);
    }

    public static void setValuesWidthHeight(View view) throws IllegalArgumentException {
        if(view == null) {
            throw new IllegalArgumentException("The view is null");
        } else {
            ViewGroup.LayoutParams param = view.getLayoutParams();
            if(view.getParent() instanceof ViewPager && param instanceof ViewPager.LayoutParams) {
                ViewPager.LayoutParams params = (ViewPager.LayoutParams)param;
                if(verifyNotWrapMatch(params.width)) {
                    params.width = displayHeight((double)params.width);
                }

                if(verifyNotWrapMatch(params.height)) {
                    params.height = displayHeight((double)params.height);
                }

                view.setLayoutParams(params);
            } else if(param != null) {
                if(verifyNotWrapMatch(param.width)) {
                    param.width = displayHeight((double)param.width);
                }

                if(verifyNotWrapMatch(param.height)) {
                    param.height = displayHeight((double)param.height);
                }

                view.setLayoutParams(param);
            }

        }
    }

    public static void setWidthValue(View view) throws IllegalArgumentException {
        if(view == null) {
            throw new IllegalArgumentException("The view is null");
        } else {
            ViewGroup.LayoutParams param = view.getLayoutParams();
            if(view.getParent() instanceof ViewPager && param instanceof ViewPager.LayoutParams) {
                ViewPager.LayoutParams params = (ViewPager.LayoutParams)param;
                if(verifyNotWrapMatch(params.width)) {
                    params.width = displayWidth((double)params.width);
                }

                if(verifyNotWrapMatch(params.height)) {
                    params.height = displayWidth((double)params.height);
                }

                view.setLayoutParams(params);
            } else if(param != null) {
                if(verifyNotWrapMatch(param.width)) {
                    param.width = displayWidth((double)param.width);
                }

                if(verifyNotWrapMatch(param.height)) {
                    param.height = displayWidth((double)param.height);
                }

                view.setLayoutParams(param);
            }

        }
    }

    public static void setMargins(View view) throws IllegalArgumentException {
        if(view == null) {
            throw new IllegalArgumentException("The view is null");
        } else {
            ViewGroup.LayoutParams param = view.getLayoutParams();
            if(view.getParent() instanceof ViewGroup && param instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)param;
                if(params.leftMargin != 0) {
                    params.leftMargin = displayWidth((double)params.leftMargin);
                }

                if(params.topMargin != 0) {
                    params.topMargin = displayHeight((double)params.topMargin);
                }

                if(params.rightMargin != 0) {
                    params.rightMargin = displayWidth((double)params.rightMargin);
                }

                if(params.bottomMargin != 0) {
                    params.bottomMargin = displayHeight((double)params.bottomMargin);
                }

                view.setLayoutParams(params);
            }

        }
    }

    public static void setMargins(View view, int left, int top, int right, int bottom) throws IllegalArgumentException {
        if(view == null) {
            throw new IllegalArgumentException("The view is null");
        } else {
            ViewGroup.LayoutParams param = view.getLayoutParams();
            if(view.getParent() instanceof ViewGroup && param instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)param;
                if(params.leftMargin != 0) {
                    params.leftMargin = displayWidth((double)left);
                }

                if(params.topMargin != 0) {
                    params.topMargin = displayHeight((double)top);
                }

                if(params.rightMargin != 0) {
                    params.rightMargin = displayWidth((double)right);
                }

                if(params.bottomMargin != 0) {
                    params.bottomMargin = displayHeight((double)bottom);
                }

                view.setLayoutParams(params);
            }

        }
    }

    public static void setPadding(View view) throws IllegalArgumentException {
        if(view == null) {
            throw new IllegalArgumentException("The view is null");
        } else {
            int start = 0;
            int top = 0;
            int end = 0;
            int bottom = 0;
            if(view.getPaddingLeft() != 0) {
                start = displayWidth((double)view.getPaddingLeft());
            }

            if(view.getPaddingTop() != 0) {
                top = displayHeight((double)view.getPaddingTop());
            }

            if(view.getPaddingRight() != 0) {
                end = displayWidth((double)view.getPaddingRight());
            }

            if(view.getPaddingBottom() != 0) {
                bottom = displayHeight((double)view.getPaddingBottom());
            }

            view.setPadding(start, top, end, bottom);
        }
    }

    public static void setPadding(View view, int left, int top, int right, int bottom) throws IllegalArgumentException {
        if(view == null) {
            throw new IllegalArgumentException("The view is null");
        } else {
            int start = 0;
            int topPadding = 0;
            int end = 0;
            int bottomPadding = 0;
            if(view.getPaddingLeft() != 0) {
                start = displayWidth((double)left);
            }

            if(view.getPaddingTop() != 0) {
                topPadding = displayHeight((double)top);
            }

            if(view.getPaddingRight() != 0) {
                end = displayWidth((double)right);
            }

            if(view.getPaddingBottom() != 0) {
                bottomPadding = displayHeight((double)bottom);
            }

            view.setPadding(start, topPadding, end, bottomPadding);
        }
    }

    public static Integer getHeightScreen(Context context) throws IllegalArgumentException {
        if(context == null) {
            throw new IllegalArgumentException("The context is null");
        } else {
            startMetrics(context);
            return heightEnd;
        }
    }

    public static Integer getWidthScreen(Context context) throws IllegalArgumentException {
        if(context == null) {
            throw new IllegalArgumentException("The context is null");
        } else {
            startMetrics(context);
            return widthEnd;
        }
    }

    public static int displayHeight(Context context, double value) throws IllegalArgumentException {
        if(context == null) {
            throw new IllegalArgumentException("The context is null");
        } else {
            startMetrics(context);
            return displayHeight(value);
        }
    }

    public static int displayWidth(Context context, double value) throws IllegalArgumentException {
        if(context == null) {
            throw new IllegalArgumentException("The context is null");
        } else {
            startMetrics(context);
            return displayWidth(value);
        }
    }

    public static Typeface getFont() {
        return font;
    }

    public static void setFont(Typeface font, int fontStyle) throws IllegalArgumentException {
        if(font == null) {
            throw new IllegalArgumentException("The Typeface is null");
        } else {
            DisplayLayout.font = font;
            if(fontStyle != 1 && fontStyle != 3 && fontStyle != 2 && fontStyle != 0) {
                DisplayLayout.fontStyle = 0;
            }

            fontStyle = fontStyle;
        }
    }

    public static StandardOrientationEnum isLandscape() {
        return standardOrientationEnum;
    }

    public static void setStandardOrientationEnum(StandardOrientationEnum standardOrientationEnum) {
        standardOrientationEnum = standardOrientationEnum;
    }

    public static enum StandardOrientationEnum {
        LANDSCAPE,
        PORTRAIT;

        private StandardOrientationEnum() {
        }
    }
}
