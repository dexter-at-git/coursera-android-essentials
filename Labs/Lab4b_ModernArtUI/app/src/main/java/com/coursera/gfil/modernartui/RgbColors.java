package com.coursera.gfil.modernartui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class RgbColors {

    private int mAlpha;
    private int mRed;
    private int mGreen;
    private int mBlue;

    RgbColors(Drawable drawable) {
        ColorDrawable colorDrawable = (ColorDrawable) drawable;
        int color = colorDrawable.getColor();
        mAlpha = colorDrawable.getAlpha();
        mRed = Color.red(color);
        mGreen = Color.green(color);
        mBlue = Color.blue(color);
    }

    public int getAlpha() {
        return mAlpha;
    }

    private int getRed(int progress) {
        double step = 0;
        if (progress != 0) {
            int scale = 255 - mRed;
            step = scale * progress / 100.00;
        }
        return mRed - (int) Math.round(step);
    }

    private int getGreen(int progress) {
        double step = 0;
        if (progress != 0) {
            int scale = 255 - mGreen;
            step = scale * progress / 100.00;
        }
        return mGreen + (int) Math.round(step);
    }

    private int getBlue(int progress) {
        double step = 0;
        if (progress != 0) {
            int scale = 255 - mBlue;
            step = scale * progress / 100.00;
        }
        return mBlue;
    }

    public int getColor(int progress) {
        int alpha = getAlpha();
        int red = getRed(progress);
        int green = getGreen(progress);
        int blue = getBlue(progress);

        return Color.argb(alpha, red, green, blue);
    }
}