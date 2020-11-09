package com.petroun.devourerhizine.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum EnumCardStatus {
    Enable((byte)0,"启用"),
    Disable((byte)1,"禁用"),
    Useing((byte)2,"使用中"),
    Fail((byte)3,"注册失败"),
    ;


    byte code;
    String name;
}
