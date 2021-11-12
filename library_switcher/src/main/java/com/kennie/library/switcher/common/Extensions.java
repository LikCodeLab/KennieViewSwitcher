package com.kennie.library.switcher.common;

import android.content.Context;
import android.util.TypedValue;

public class Extensions {

    public static float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    public static float toPx(Context context, float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }

    public static boolean isLollipopAndAbove() {
        return false;
    }
}
