package com.petroun.devourerhizine.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class MyParameter{
    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "value")
    private String value;
}

