package com.petroun.devourerhizine.web.controller.api.v1.gotoil;

import cn.gotoil.bill.exception.BillException;
import cn.gotoil.bill.web.annotation.Authentication;
import cn.gotoil.bill.web.interceptor.authentication.AuthenticationType;
import cn.gotoil.bill.web.message.BillApiResponse;
import com.petroun.devourerhizine.classes.tools.DateUtils;
import com.petroun.devourerhizine.service.oil.GotoilService;
import com.petroun.devourerhizine.service.oil.OilService;
import com.petroun.devourerhizine.web.controller.api.v1.Controller;
import com.petroun.devourerhizine.web.message.reqeust.gotoil.QRRefuelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
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
    OilService oilService;


    /**
     * 二维码加油
     *
     * @param request
     * @return
     */
    @RequestMapping("QRcode")
    @Authentication(authenticationType = AuthenticationType.Signature)
    public Object QRCodeAction(@Valid @RequestBody QRRefuelRequest request, BindingResult bindingResult) {
        String qrcode = oilService.getQRCode(request.getNotifyUrl(),request.getFee(),request.getOutTime(),request.getMobile());
        if(StringUtils.isEmpty(qrcode)){
            throw new BillException(9999,"获取二维码失败");
        }
        return new BillApiResponse(qrcode);
    }

   /* @GetMapping("mq1")
    @Authentication(authenticationType = AuthenticationType.None)
    public Object as1Action(String id,int redo) {
        System.out.println(DateUtils.simpleDateTimeWithMilliSecondNoSymbolFormatter().format(new Date()) + "\t" + id);
        return gotoilService.appendGotoilTransSucessQueue(id, redo);
    }

    @GetMapping("mq2")
    @Authentication(authenticationType = AuthenticationType.None)
    public Object as2Action(String id, int redo) {
        System.out.println(DateUtils.simpleDateTimeWithMilliSecondNoSymbolFormatter().format(new Date()) + "\t" + id);
        return gotoilService.appendGotoilQueryQueue(id);
    }*/
}
