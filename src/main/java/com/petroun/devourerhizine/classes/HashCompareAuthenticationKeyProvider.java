package com.petroun.devourerhizine.classes;

import java.util.regex.Pattern;

public class HashCompareAuthenticationKeyProvider {

    private static final Pattern pattern = Pattern.compile("^\\d{19}$");

    public static String key(String xu) {
        if ("1530099893935000183".equals(xu)) {
            return "bb0468561d34d94b71ca5ff29e134238b28cfe5d";
        }

        return null;
    }

    /**
     * @param xu
     * @return
     */
    public static Boolean uvalidate(String xu) {
        if (null == xu) {
            return false;
        }

        return pattern.matcher(xu).matches();
    }
}
