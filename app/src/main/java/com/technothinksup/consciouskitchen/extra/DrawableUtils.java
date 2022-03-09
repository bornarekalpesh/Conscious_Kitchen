package com.technothinksup.consciouskitchen.extra;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import androidx.core.content.ContextCompat;

import com.applandeo.materialcalendarview.CalendarUtils;
import com.technothinksup.consciouskitchen.R;


/**
 * Created by Prasad Sawant.
 */

public final class DrawableUtils {

    public static Drawable getCircleDrawableWithText(Context context, String string) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.sample_circle);
        Drawable text = CalendarUtils.getDrawableText(context, string, null, android.R.color.white, 8);

        Drawable[] layers = {background, text};
        return new LayerDrawable(layers);
    }

    public static Drawable getCircleDrawable(Context context, String string) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.sample_red_circle);
        Drawable text = CalendarUtils.getDrawableText(context, string, null, android.R.color.white, 8);

        Drawable[] layers = {background, text};
        return new LayerDrawable(layers);
    }

    private DrawableUtils() {
    }
}
