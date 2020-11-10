package com.getir.rig.util;

import org.apache.commons.lang3.StringUtils;

public class RigUtils {

    private RigUtils() {
    }

    private static final String PLUS = "+";
    private static final int PHONE_NUMBER_VALID_LENGTH = 10;

    public static boolean isStringNullOrEmpty(String str) { return str == null || str.isEmpty(); }

    public static boolean isDoubleNullOrZero(Double val) { return val == null || val == 0; }

    public static boolean isLongNullOrZero(Long val) { return val == null || val == 0; }

    public static boolean isValidPhoneNumber(String phoneNumber){

        if (StringUtils.isEmpty(phoneNumber)){
            return false;
        }

        if (phoneNumber.startsWith(PLUS)){
            phoneNumber = phoneNumber.replace(PLUS, StringUtils.EMPTY);
        }

        return phoneNumber.length() >= PHONE_NUMBER_VALID_LENGTH && !allCharactersSame(phoneNumber) && StringUtils.isNumeric(phoneNumber);
    }

    private static boolean allCharactersSame(String s)
    {
        int n = s.length();
        for (int i = 1; i < n; i++)
            if (s.charAt(i) != s.charAt(0))
                return false;

        return true;
    }

}
