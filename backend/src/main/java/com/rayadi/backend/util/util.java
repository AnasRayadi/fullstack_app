package com.rayadi.backend.util;

import java.util.regex.Pattern;

public class util {
    public boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean emailIsValid(String emailAddress) {
        String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
}
                                                                                                                                                                                                                                                                                                                                                                                                                                      