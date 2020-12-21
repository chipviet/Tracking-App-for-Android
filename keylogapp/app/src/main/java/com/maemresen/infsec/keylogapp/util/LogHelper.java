package com.maemresen.infsec.keylogapp.util;

public class LogHelper {


    private final static String LOG_TAG_PREFIX = "Keylogapp";

    public static String getLogTag(Class<?> clazz) {
        return String.format("%s-%s", LOG_TAG_PREFIX, clazz.getSimpleName());
    }
}
