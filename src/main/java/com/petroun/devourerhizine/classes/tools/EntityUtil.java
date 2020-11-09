package com.petroun.devourerhizine.classes.tools;

import com.petroun.devourerhizine.model.MyParameter;
import com.petroun.devourerhizine.model.ReqParameters;
import com.petroun.devourerhizine.model.entity.InvokeThirdLogWithBLOBs;

import java.util.*;

public class EntityUtil {
    public static InvokeThirdLogWithBLOBs createInvokeThirdLog(String requestFlow, byte type, String url){
        InvokeThirdLogWithBLOBs invokeThirdLog = new InvokeThirdLogWithBLOBs();
        invokeThirdLog.setCreatedAt(new Date());
        invokeThirdLog.setIpAddress(url);
        invokeThirdLog.setTransNo(requestFlow);
        invokeThirdLog.setType(type);
        return invokeThirdLog;
    }

    public static List<String[]> formatResult(String resStr){
        List<String[]> list = new ArrayList<>();
        String[] array1 = resStr.split("\\|");
        for(String arr : array1){
            list.add(arr.split("~"));
        }
        return list;
    }

    public static String ReqParametersByKey(ReqParameters reqParameters, String key){
        List<MyParameter> list = reqParameters.getParameter();
        if(list == null || list.size() == 0){
            return null;
        }else {
            for (MyParameter myParameter : list) {
                if(key.equals(myParameter.getName())){
                    return myParameter.getValue();
                }
            }
            return null;
        }

    }
}
