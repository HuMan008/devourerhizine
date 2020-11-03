package com.petroun.devourerhizine.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum EnumGtOil {
    UseToken((byte)1,"用户TOKEN"),
    QRcode((byte)2,"二维码"),
    QueryTrans((byte)3,"交易查询"),
    ;


    byte code;
    String name;
}
