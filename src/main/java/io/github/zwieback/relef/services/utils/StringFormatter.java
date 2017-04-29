package io.github.zwieback.relef.services.utils;

import java.text.DecimalFormat;

public final class StringFormatter {

    private static final DecimalFormat DOUBLE_FORMAT = new DecimalFormat("0.000");

    private StringFormatter() {
    }

    public static String formatDouble(Double value) {
        return value == null ? null : DOUBLE_FORMAT.format(value);
    }
}
