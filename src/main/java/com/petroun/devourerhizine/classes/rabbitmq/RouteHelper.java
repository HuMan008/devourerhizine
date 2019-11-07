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

package com.petroun.devourerhizine.classes.rabbitmq;

@SuppressWarnings("unused")
public class RouteHelper {

    /**
     *
     * @param args
     * @return
     */
    public static String build(String... args) {
        if (args.length < 1) {
            return "";
        }

        if (args.length == 1) {
            return args[0];
        }

        return String.join(".", args);
    }
}
