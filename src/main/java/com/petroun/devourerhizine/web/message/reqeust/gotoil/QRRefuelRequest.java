package com.petroun.devourerhizine.web.message.reqeust.gotoil;

import cn.gotoil.bill.web.annotation.ThirdValidation;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 二维码加油请求
 *
 * @author think <syj247@qq.com>、
 * @date 2020/11/9、9:30
 */
@Data
public class QRRefuelRequest {

    /**
     * 加油用户手机号
     */
    @NotNull(message = "mobile can not be null")
    @ThirdValidation(regex = "^1\\d{10}$")
    String mobile;


    /**
     * 加油金额 单位分
     */
    @NotNull(message = "fee can not be null")
    @Min(value = 0, message = "fee min 0") int fee;
    /**
     * 超时时间 秒
     */
    @NotNull(message = "outTime can not be null")
    int outTime = 20;

    /**
     * 通知地址
     */
    @NotNull(message = "notifyUrl can not be null")
    String notifyUrl;

}
