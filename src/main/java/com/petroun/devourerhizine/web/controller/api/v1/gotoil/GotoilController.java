package com.petroun.devourerhizine.web.controller.api.v1.gotoil;

import cn.gotoil.bill.web.annotation.Authentication;
import cn.gotoil.bill.web.interceptor.authentication.AuthenticationType;
import cn.gotoil.bill.web.message.BillApiResponse;
import com.petroun.devourerhizine.classes.tools.DateUtils;
import com.petroun.devourerhizine.config.GTConfig;
import com.petroun.devourerhizine.provider.gt.GTGateWay;
import com.petroun.devourerhizine.service.Oil.GotoilService;
import com.petroun.devourerhizine.web.controller.api.v1.Controller;
import com.petroun.devourerhizine.web.message.reqeust.gotoil.QRRefuelRequest;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;

/**
 * 国通
 *
 * @author think <syj247@qq.com>、
 * @date 2020/11/9、9:27
 */
@RestController
@Authentication(authenticationType = AuthenticationType.Signature)
public class GotoilController extends Controller {

    @Resource
    GotoilService gotoilService;

    @Autowired
    GTGateWay gtGateWay;

    @Autowired
    GTConfig gtConfig;
    /**
     * 二维码加油
     *
     * @param request
     * @return
     */
    @RequestMapping("QRcode")
    @Authentication(authenticationType = AuthenticationType.None)
    public Object QRCodeAction(@Valid @RequestBody QRRefuelRequest request) {
        String qrcode = gtGateWay.getQRCode(request.getMobile(),request.getNotifyUrl()
        ,request.getFee(),request.getOutTime(),gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword());

        return new BillApiResponse(qrcode);
    }

    @GetMapping("mq")
    @Authentication(authenticationType = AuthenticationType.None)
    public Object asAction(String id, int redo) {
        System.out.println(DateUtils.simpleDateTimeWithMilliSecondNoSymbolFormatter().format(new Date()) + "\t" + id);
        return gotoilService.appendGotoilQueue(id, redo);
    }
}
