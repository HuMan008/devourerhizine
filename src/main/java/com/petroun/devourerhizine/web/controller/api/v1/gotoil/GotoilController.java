package com.petroun.devourerhizine.web.controller.api.v1.gotoil;

import cn.gotoil.bill.web.annotation.Authentication;
import cn.gotoil.bill.web.interceptor.authentication.AuthenticationType;
import com.petroun.devourerhizine.web.controller.api.v1.Controller;
import com.petroun.devourerhizine.web.message.reqeust.gotoil.QRRefuelRequest;
import org.springframework.web.bind.annotation.RestController;

/**
 * 国通
 *
 * @author think <syj247@qq.com>、
 * @date 2020/11/9、9:27
 */
@RestController
@Authentication(authenticationType = AuthenticationType.Signature)
public class GotoilController extends Controller {

    /**
     * 二维码加油
     *
     * @param request
     * @return
     */
    public Object QRAction(QRRefuelRequest request) {
        return null;
    }
}
