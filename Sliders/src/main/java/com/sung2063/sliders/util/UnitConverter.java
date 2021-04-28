package com.sung2063.sliders.util;

import android.content.Context;

public class UnitConverter {

    public static float convertTextPXToSP(Context context, int textInPX) {
        return textInPX / context.getResources().getDisplayMetrics().scaledDensity;
    }

}
