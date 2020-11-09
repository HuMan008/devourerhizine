package com.petroun.devourerhizine.model;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@ToString
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseEntity {
    @XmlElement(name = "response-id")
    private String responseId;

    @XmlElement(name = "request-flow")
    private String requestFlow;

    @XmlElement(name = "response-flow")
    private String responseFlow;

    @XmlElement(name = "corpartner-id")
    private String copartnerId;

    @XmlElement(name = "card")
    private String card;

    @XmlElement(name = "money")
    private String money;

    @XmlElement(name = "balance")
    private String balance;

    @XmlElement(name = "taxbal")
    private String taxbal;

    @XmlElement(name = "parameters")
    private ReqParameters reqParameters;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "code")
    private String code;

    @XmlElement(name = "time")
    private String time;

    @XmlElement(name = "strBRCode")
    private String strBRCode;

    @XmlElement(name = "nBRValidSeconds")
    private String nBRValidSeconds;

    @XmlElement(name = "strQRCode")
    private String strQRCode;

    @XmlElement(name = "nQRValidSeconds")
    private String nQRValidSeconds;

    @XmlElement(name = "BusiExtend")
    private String BusiExtend;

}
