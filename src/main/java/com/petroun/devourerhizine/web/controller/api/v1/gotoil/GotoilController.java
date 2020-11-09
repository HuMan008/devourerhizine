package com.petroun.devourerhizine.web.controller.api.v1.gotoil;

import cn.gotoil.bill.web.annotation.Authentication;
import cn.gotoil.bill.web.interceptor.authentication.AuthenticationType;
import com.petroun.devourerhizine.service.Oil.GotoilService;
import com.petroun.devourerhizine.web.controller.api.v1.Controller;
import com.petroun.devourerhizine.web.message.reqeust.gotoil.QRRefuelRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    /**
     * 二维码加油
     *
     * @param request
     * @return
     */
    public Object QRAction(QRRefuelRequest request) {
        return null;
    }

    @GetMapping("mq")
    @Authentication(authenticationType = AuthenticationType.None)
    public Object asAction(String id, int redo) {
        return gotoilService.appendGotoilQueue(id, redo);
    }
}
