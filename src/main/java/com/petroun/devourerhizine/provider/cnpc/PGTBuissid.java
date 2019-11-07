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

import cn.gotoil.bill.exception.BillException;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class PGTBuissid {

//'OIL_TYPE'    => [
//        5001 => "0#",
//        5002 => "93#",
//        5003 => "97#",
//        5004 => "98#",
//        5005 => "89#",
//        5006 => "92#",
//        5007 => "95#",
//        5008 => "-10#",
//        5009 => "-20#",
//        5010 => "-35#",
//        6000 => "储值卡"
//        ],

    private static Map<String, String> BuissidMapsCNPC = new HashMap<>();
    private static Map<String, String> BuissidMapsSINO = new HashMap<>();

    static {
        BuissidMapsCNPC.put("6000", "905");
        BuissidMapsSINO.put("6000", "173");

        BuissidMapsCNPC.put("5001", "901");
        BuissidMapsCNPC.put("5006", "902");
        BuissidMapsCNPC.put("5007", "903");
        BuissidMapsCNPC.put("5004", "904");


        BuissidMapsSINO.put("5001", "911");
        BuissidMapsSINO.put("5006", "912");
        BuissidMapsSINO.put("5007", "913");
        BuissidMapsSINO.put("5004", "914");
    }

    public static String buissidFromPGTEXT(String pgtext, Director director) {
        if (StringUtils.isEmpty(pgtext)) {
            return null;
        }

        String id = null;
        if (director.getCode().equals(Director.CNPC.getCode())) {
            id = BuissidMapsCNPC.getOrDefault(pgtext, null);
        }else if (director.getCode().equals(Director.SINOPEC.getCode())) {
            id = BuissidMapsSINO.getOrDefault(pgtext, null);
        }

        if (null == id) {
            throw new BillException(2566, "Unkown PGTEXT");
        }

        return id;
    }
}
