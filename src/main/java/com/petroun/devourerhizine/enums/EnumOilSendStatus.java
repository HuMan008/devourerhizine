package com.petroun.devourerhizine.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum EnumOilSendStatus {
    Sending((byte)0,"发送中"),
    fail((byte)1,"失败"),
    success((byte)2,"成功"),
    ;


    byte code;
    String name;
}
