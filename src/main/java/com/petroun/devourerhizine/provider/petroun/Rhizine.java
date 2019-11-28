package com.petroun.devourerhizine.provider.petroun;

import cn.gotoil.bill.exception.BillException;
import cn.gotoil.bill.provider.bill.BillClient;
import cn.gotoil.bill.provider.bill.BillFlow;
import com.petroun.devourerhizine.provider.cnpc.Director;

import java.util.HashMap;
import java.util.Map;

public class Rhizine {

    private static final Integer CNPC_BUSSID = 40000;
    private static final Integer SINOPEC_BUSSID = 40001;
    private static final Integer POINT = 1;
    private static final String RHIZINE_HOST = "http://172.200.209.32:8080";
    private static final String RHIZINE_XU = "1530452732732000155";
    private static final String RHIZINE_SECRET = "8d97daa4569470b882cadcf284cff5d11e758498";

    private static final BillClient billClient() {
        BillClient billClient = new BillClient();
        billClient.setHost(RHIZINE_HOST);
        billClient.setXU(RHIZINE_XU);
        billClient.setSecret(RHIZINE_SECRET);
        return billClient;
    }

    private static Integer bussid(Director director) {
        if (director.getCode().equals(Director.CNPC.getCode())) {
            return CNPC_BUSSID;
        }

        if (director.getCode().equals(Director.SINOPEC.getCode())) {
            return SINOPEC_BUSSID;
        }

        return 0;
    }

    private static String title(Director director) {
        if (director.getCode().equals(Director.CNPC.getCode())) {
            return "中石油昆仑卡充值";
        }

        if (director.getCode().equals(Director.SINOPEC.getCode())) {
            return "中石化加油卡充值";
        }

        return "加油卡充值";
    }

    public static BillFlow deduction(Long uid, String flowid, Integer face, Integer promo, Integer promoid, Director director) {

        if (face < 0) {
            throw new BillException(1, "face error");
        }

        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("flowid", flowid);
        params.put("bussid", bussid(director));
        params.put("promoid", promoid);
        params.put("promo", promo);
        params.put("point", POINT);
        params.put("face", 0 - face);
        params.put("title", title(director));

        return billClient().postJsonAsFlow("/api/v1/transaction", params);
    }

    public static BillFlow revocation(Long id, String flowid) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("flowid", flowid);
        return billClient().postJsonAsFlow("/api/v1/transaction/returns", params);
    }
}
