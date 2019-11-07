/*
 * ******************************************************
 *  * Copyright (C) 2018 cluries  <cluries#me.com>
 *  *
 *  * This file is part of Devourer.
 *  *
 *  * Devourer can not be copied and/or distributed without the express
 *  * permission of cluries
 *  ******************************************************
 */

package com.petroun.devourerhizine.provider.cnpc;

import java.util.HashMap;
import java.util.Map;

public class CnpcConfiger {

    private static final Map<String, String> headers = new HashMap<>();

    static {
        headers.put("User-Agent", "Devourer Version 1.0 PetroGuotong");
//        headers.put("Wish", "Good good study, day day up");
    }

    public static Map<String, String> getHeaders() {
        return headers;
    }

}
