package awais.instagrabber.utils;

import android.graphics.Color;

import androidx.annotation.ColorInt;

public final class ColorUtils {
    public static float colorBrightness(@ColorInt int color) {
        return ((float) Color.red(color) / 255 * 299 + (float) Color.green(color) / 255 * 587 + (float) Color.blue(color) / 255 * 114) / 1000;
    }

    public static int darken(@ColorInt int color) {
        if (colorBrightness(color) < 0.25) {
            return androidx.core.graphics.ColorUtils.setAlphaComponent(0, (int) (0.25 * 255));
        }
        return Color.rgb(Math.max(Color.red(color) - 30, 0), Math.max(Color.green(color) - 30, 0), Math.max(Color.blue(color) - 30, 0));
    }
}
