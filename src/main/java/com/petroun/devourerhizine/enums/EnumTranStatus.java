package com.petroun.devourerhizine.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum EnumTranStatus {
    Trading((byte)0,"交易中"),
    Overdue((byte)1,"过期"),
    Success((byte)2,"成功"),
    QueryFail((byte)3,"查询失败"),
    ;


    byte code;
    String name;
}
