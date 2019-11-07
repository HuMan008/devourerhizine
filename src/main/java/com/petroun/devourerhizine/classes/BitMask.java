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

package com.petroun.devourerhizine.classes;

public class BitMask {

    public static boolean isSeted(int value, int bit) {
        return ((value >> bit) & 0x01) == 0x01;
    }

    public static int setBit(int value, int bit, boolean bitSet) {
        int mask = 0x01 << bit;
        if (bitSet) {
            return value | mask;
        } else {
            return value & (~mask);
        }
    }


}
