package com.petroun.devourerhizine.model;

import lombok.Data;

import java.util.TreeMap;
import javax.xml.bind.annotation.*;
@Data
@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestEntity {
    @XmlElement(name = "request-id")
    private String requestId;

    @XmlElement(name = "request-flow")
    private String requestFlow;

    @XmlElement(name = "copartner-id")
    private String copartnerId;

    @XmlElement(name = "card")
    private String card;

    @XmlElement(name = "password")
    private String password;

    @XmlElement(name = "money")
    private String money;

    @XmlElement(name = "time")
    private String time;

    @XmlElement(name = "mac")
    private String mac;

    @XmlElement(name = "parameters")
    private ReqParameters reqParameters;

    @XmlElement(name = "md5")
    private String md5;

    @XmlElement(name = "seq")
    private String seq;

    @XmlElement(name = "md5-2")
    private String md52;

    @XmlElement(name = "extend")
    private String extend;

    @XmlElement(name = "extend2")
    private String extend2;


}
