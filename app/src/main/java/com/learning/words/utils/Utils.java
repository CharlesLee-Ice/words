package com.learning.words.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by liqilin on 2017/2/24.
 */

public class Utils {
    private static int mScreenHeight = 0;
    private static int mScreenWidth = 0;

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        float px = dp * ((float) displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public static float convertPixelToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static int getScreenHeight(Context context) {
        if (mScreenHeight != 0) {
            return mScreenHeight;
        }

        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        mScreenHeight = metrics.heightPixels;
        mScreenWidth = metrics.widthPixels;

        return mScreenHeight;
    }

    public static int getScreenWidth(Context context) {
        if (mScreenWidth != 0) {
            return mScreenWidth;
        }

        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        mScreenHeight = metrics.heightPixels;
        mScreenWidth = metrics.widthPixels;

        return mScreenWidth;
    }
}
