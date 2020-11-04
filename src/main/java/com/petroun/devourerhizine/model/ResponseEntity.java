package com.petroun.devourerhizine.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseEntity {
    @XmlElement(name = "Response-id")
    private String responseId;

    @XmlElement(name = "money")
    private String money;

    @XmlElement(name = "Request-flow")
    private String requestFlow;

    @XmlElement(name = "Response-flow")
    private String responseFlow;

    @XmlElement(name = "Corpartner-id")
    private String copartnerId;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "code")
    private String code;

    @XmlElement(name = "card")
    private String card;

    @XmlElement(name = "taxbal")
    private String taxbal;

    @XmlElement(name = "time")
    private String time;

    @XmlElement(name = "balance")
    private String balance;

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
