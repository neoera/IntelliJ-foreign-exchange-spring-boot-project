package com.openpayd.ots.util;

public class AtsUtils {

    public static boolean isStringNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isDoubleNullOrZero(Double val) {
        return val == null || val == 0;
    }

}
