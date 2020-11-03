package com.petroun.devourerhizine.classes.tools;

import com.petroun.devourerhizine.model.entity.InvokeThirdLogWithBLOBs;

import java.util.Date;

public class EntityUtil {
    public static InvokeThirdLogWithBLOBs createInvokeThirdLog(String requestFlow, byte type, String url){
        InvokeThirdLogWithBLOBs invokeThirdLog = new InvokeThirdLogWithBLOBs();
        invokeThirdLog.setCreatedAt(new Date());
        invokeThirdLog.setIpAddress(url);
        invokeThirdLog.setTransNo(requestFlow);
        invokeThirdLog.setType(type);
        return invokeThirdLog;
    }
}
