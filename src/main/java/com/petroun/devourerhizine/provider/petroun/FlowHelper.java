package com.petroun.devourerhizine.provider.petroun;

import cn.gotoil.bill.provider.bill.BillFlow;

public class FlowHelper {
    public static boolean isNormal(BillFlow flow) {
        if (!isNetworkNormal(flow)) {
            return false;
        }

        if (flow.getBillObject() == null) {
            return false;
        }

        return flow.getBillObject().getStatus().intValue() == 0;
    }

    public static boolean isNetworkNormal(BillFlow flow) {
        if (flow == null) {
            return false;
        }

        if (flow.getHttpcode() < 200 || flow.getHttpcode() >= 300) {
            return false;
        }

        return true;
    }

    public static boolean rechargeNeedRetry(BillFlow flow) {
        return true;
    }
}
