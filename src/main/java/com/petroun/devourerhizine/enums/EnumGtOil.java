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
    QueryStation((byte)4,"站点查询"),
    QueryMobile((byte)5,"手机号是否注册"),
    RegisterMobile((byte)6,"手机号注册"),
    ;


    byte code;
    String name;
}
