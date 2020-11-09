package com.petroun.devourerhizine.model;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

@Data
@XmlRootElement(name = "parameter")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReqParameters {

    private java.util.List<MyParameter> parameter;

    public boolean add(String key,String value){
        if(parameter == null){
            parameter = new ArrayList<>();
        }
        MyParameter p = new MyParameter();
        p.setName(key);
        p.setValue(value);
        return parameter.add(p);
    }
}

