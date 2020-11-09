package com.petroun.devourerhizine.provider.gt;

import cn.gotoil.bill.exception.BillException;
import cn.gotoil.bill.tools.date.DateUtils;
import com.petroun.devourerhizine.classes.tools.EntityUtil;
import com.petroun.devourerhizine.classes.tools.HttpUtils;
import com.petroun.devourerhizine.classes.tools.XmlUtils;
import com.petroun.devourerhizine.config.GTConfig;
import com.petroun.devourerhizine.config.GTRequestSigner;
import com.petroun.devourerhizine.enums.EnumCardStatus;
import com.petroun.devourerhizine.enums.EnumGtOil;
import com.petroun.devourerhizine.enums.EnumTranStatus;
import com.petroun.devourerhizine.model.ReqParameters;
import com.petroun.devourerhizine.model.RequestEntity;
import com.petroun.devourerhizine.model.ResponseEntity;
import com.petroun.devourerhizine.model.View.ViewCardAndUse;
import com.petroun.devourerhizine.model.entity.InvokeThirdLogWithBLOBs;
import com.petroun.devourerhizine.model.entity.OilCardUse;
import com.petroun.devourerhizine.model.entity.OilMobileCardDetail;
import com.petroun.devourerhizine.model.entity.OilMobileCardInfo;
import com.petroun.devourerhizine.model.mapper.InvokeThirdLogMapper;
import com.petroun.devourerhizine.service.Oil.CardService;
import com.petroun.devourerhizine.service.Oil.MobileCardService;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class GTGateWay {

    private final static Logger logger = LoggerFactory.getLogger(GTGateWay.class);

    @Autowired
    GTConfig gtConfig;

    @Autowired
    InvokeThirdLogMapper invokeThirdLogMapper;

    @Autowired
    CardService cardService;

    /*@Autowired
    Connection connection;*/

    @Autowired
    MobileCardService mobileCardService;



    public String getUserToken(String cardNo,String pwd,String copartnerId,String copartnerPwd){
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setRequestId("UserPayLogonNew");
        requestEntity.setMoney("0");
        requestEntity.setCopartnerId(copartnerId);
        requestEntity.setPassword(pwd);
        requestEntity.setCard(cardNo);
        requestEntity.setRequestFlow("");
       /* ReqParameters reqParameters = new ReqParameters();

        requestEntity.setReqParameters(reqParameters);*/
        GTRequestSigner.signedRequest(requestEntity,copartnerPwd);

        InvokeThirdLogWithBLOBs invokeThirdLogWithBLOBs = EntityUtil.createInvokeThirdLog("", EnumGtOil.UseToken.getCode(),requestEntity.getRequestId());

        try{
            ResponseEntity responseEntity = null;

            String ex = XmlUtils.toStr(requestEntity,false,true);
            logger.debug("请求request-->{}", ex);

            Response response = HttpUtils.okHttpPost(gtConfig.getUrl(),ex);
            System.out.println(response.body().string());
            String resTxt = response.body().string();
            responseEntity = XmlUtils.parseBean(resTxt,responseEntity.getClass());
            String token = "";
            return token;
        }catch (Exception ex){
            logger.error("{}", ex);
            invokeThirdLogWithBLOBs.setResponse(ex.toString());
        }finally {
            invokeThirdLogMapper.insert(invokeThirdLogWithBLOBs);
        }
        return null;
    }

    public String getQRCode(String mobile,String sendUrl,int amount,int sed,String copartnerId,String copartnerPwd){
        ViewCardAndUse cardAndUser = cardService.getOilCard(mobile,amount);
        OilMobileCardInfo mobileCard = cardAndUser.getOilMobileCardInfo();
        OilCardUse oilCardUse = cardAndUser.getOilCardUse();
        String token = getUserToken(mobileCard.getCardNo(),(mobileCard.getMobile()+mobileCard.getSalt()),copartnerId,copartnerPwd);
        if(!StringUtils.isEmpty(token)){
            //判断是否需要充值
            if(amount < mobileCard.getBalance()){
                if(!recharge(mobileCard.getCardNo(),amount,copartnerId,copartnerPwd)){
                    throw new BillException(9999,"充值失败");
                }
            }


            RequestEntity requestEntity = new RequestEntity();
            requestEntity.setRequestId("GetQRcodeOrBarcode");
            requestEntity.setMoney("0");
            requestEntity.setCopartnerId(copartnerId);
            //requestEntity.setPassword(oilCardInfo.getCardPwd());
            //requestEntity.setCard(mobileCard.getCardNo());
            GTRequestSigner.signedRequest(requestEntity,copartnerPwd);

            InvokeThirdLogWithBLOBs invokeThirdLogWithBLOBs = EntityUtil.createInvokeThirdLog(oilCardUse.getId(), EnumGtOil.QRcode.getCode(),requestEntity.getRequestId());
            ReqParameters reqParameters = new ReqParameters();

            reqParameters.add("strTransCert",token);
            reqParameters.add("strExtend2",String.valueOf(sed));
            requestEntity.setReqParameters(reqParameters);

            try{
                ResponseEntity responseEntity = null;

                String ex = XmlUtils.toStr(requestEntity,false,true);
                logger.debug("请求request-->{}", ex);
                invokeThirdLogWithBLOBs.setRequest(ex);

                Response response = HttpUtils.okHttpPost(gtConfig.getUrl(),ex);
                System.out.println(response.body().string());
                if (response != null && response.isSuccessful()) {
                    String resTxt = response.body().string();
                    logger.debug("应答response-->{}", resTxt);
                    responseEntity = XmlUtils.parseBean(resTxt, ResponseEntity.class);
                    if ("0".equals(responseEntity.getCode())) {
                        String QRCode = "";
                        int QRCodeSed = 100;
                        String time = "";
                        cardService.updateCardUse(oilCardUse,time,QRCodeSed);
                    }else{

                    }
                }else{
                    String resTxt =response == null ?"应答为空": response.body().string();
                    invokeThirdLogWithBLOBs.setResponse(resTxt);
                }


                // 获取失败

                return null;
            }catch (Exception ex){
                logger.error("{}", ex);
                invokeThirdLogWithBLOBs.setResponse(ex.toString());
            }finally {
                invokeThirdLogMapper.insert(invokeThirdLogWithBLOBs);
            }
        }else{
            //TOKEN获取失败
        }
        return null;
    }

    public OilCardUse queryCardUserByRemote(String id,String copartnerId,String copartnerPwd){
        OilCardUse oilCardUse = cardService.queryById(id);
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setRequestId("UserHistoryQueryFYALL");
        requestEntity.setMoney("0");
        requestEntity.setCopartnerId(copartnerId);
        //requestEntity.setPassword(oilCardInfo.getCardPwd());
        requestEntity.setCard(oilCardUse.getCardNo());
        GTRequestSigner.signedRequest(requestEntity,copartnerPwd);

        InvokeThirdLogWithBLOBs invokeThirdLogWithBLOBs = EntityUtil.createInvokeThirdLog(oilCardUse.getId(), EnumGtOil.QueryTrans.getCode(),requestEntity.getRequestId());
        /**
         * BuExtend1	卡号
         * BuExtend2	开始时间（格式：2015-10-27 00:00:00）
         * BuExtend3	结束时间（格式：2015-10-27 23:59:59）
         * BuExtend6	开始条数  下标从1开始
         * BuExtend7	结束条数
         */
        ReqParameters reqParameters = new ReqParameters();
        reqParameters.add("BuExtend1",oilCardUse.getCardNo());
        reqParameters.add("BuExtend2", DateUtils.simpleDatetimeFormatter().format(oilCardUse.getCreatedAt()));
        //过期时间加2分钟
        Date endDate = com.petroun.devourerhizine.classes.tools.DateUtils.DateAddSed(DateUtils.simpleDatetimeFormatter().format(oilCardUse.getValidityTime()),60*2);
        reqParameters.add("BuExtend3", DateUtils.simpleDatetimeFormatter().format(endDate));
        reqParameters.add("BuExtend6", "1");
        reqParameters.add("BuExtend7", "10");
        requestEntity.setReqParameters(reqParameters);

        try{
            ResponseEntity responseEntity = null;

            String ex = XmlUtils.toStr(requestEntity,false,true);
            logger.debug("请求request-->{}", ex);

            Response response = HttpUtils.okHttpPost(gtConfig.getUrl(),ex);

            //成功
            if (response != null && response.isSuccessful()) {
                String resTxt = response.body().string();
                logger.debug("应答response-->{}",resTxt);
                responseEntity = XmlUtils.parseBean(resTxt,responseEntity.getClass());
                if("0".equals(responseEntity.getCode())){
                    /**
                     * BusiExtend 返回报文描述：
                     * 每个字段之间以“~”隔开，每一行已“|”隔开，报文格式如下：
                     * 终端号码~交易时间~业务id~业务名称~交易价值~交易商户~交易后余额~交易流水号~用户油库交易时平均单价
                     * ~消费油量~交易后油量~交易金额~代理流水~加油币编号~加油币名称~油品当前单价|
                     */
                    String[] strs = responseEntity.getBusiExtend().split("|");
                    String result = strs[strs.length-1];
                    String[] resultDetail = result.split("~");
                    OilCardUse updateOilCardUser = new OilCardUse();
                    updateOilCardUser.setId(oilCardUse.getId());
                    updateOilCardUser.setStatus(EnumTranStatus.success.getCode());
                    updateOilCardUser.setStation(resultDetail[0]);
                    updateOilCardUser.setTransactionTime(DateUtils.simpleDatetimeFormatter().parse(resultDetail[1]));
                    updateOilCardUser.setBusinessId(resultDetail[2]);
                    updateOilCardUser.setBusinessName(resultDetail[3]);
                    updateOilCardUser.setFace(Integer.valueOf(resultDetail[4]));
                    updateOilCardUser.setMerchant(resultDetail[5]);
                    updateOilCardUser.setBalance(Integer.valueOf(resultDetail[6]));
                    updateOilCardUser.setFlowid(resultDetail[7]);
                    updateOilCardUser.setRise(resultDetail[8]);
                    updateOilCardUser.setRiseAfter(resultDetail[9]);
                    updateOilCardUser.setAmount(Integer.valueOf(resultDetail[10]));
                    updateOilCardUser.setOilPrice(Integer.valueOf(resultDetail[11]));

                    /**
                     * 查询站点
                     */
                    requestEntity = new RequestEntity();
                    requestEntity.setRequestId("UserHistoryQueryFYALL");
                    requestEntity.setMoney("0");
                    requestEntity.setCopartnerId(copartnerId);
                    requestEntity.setCard(updateOilCardUser.getStation());
                    GTRequestSigner.signedRequest(requestEntity,copartnerPwd);

                    InvokeThirdLogWithBLOBs queryStationLog = EntityUtil.createInvokeThirdLog(oilCardUse.getId(), EnumGtOil.QueryStation.getCode(),requestEntity.getRequestId());
                    Response stationResponse = HttpUtils.okHttpPost(gtConfig.getUrl(),ex);
                    if (stationResponse != null && stationResponse.isSuccessful()) {
                        resTxt = response.body().string();
                        logger.debug("应答response-->{}",resTxt);
                        responseEntity = XmlUtils.parseBean(resTxt,responseEntity.getClass());
                        if("0".equals(responseEntity.getCode())){
                            String[] stationStrs = responseEntity.getBusiExtend().split("|");
                            String stationResult = stationStrs[strs.length-1];
                            String[] stationResultDetail = stationResult.split("~");
                            updateOilCardUser.setStationName(stationResultDetail[2]);
                            if(cardService.updateOilCardUse(updateOilCardUser)){
                                cardService.unbundlingNotInTrading(updateOilCardUser.getId());
                                //todo 成功通知

                               /* MQPublisher.publish(connection, MQDefiner.EX_GOTOIL, MQDefiner.RK_GOTOIL_BIND, "ID",
                                        MQPublisher.DelayInterval.IMMEDIATELY);*/
                                return updateOilCardUser;
                            }
                        }
                    }
                }
            }

        }catch (Exception ex){
            logger.error("{}", ex);
            invokeThirdLogWithBLOBs.setResponse(ex.toString());
        }finally {
            invokeThirdLogMapper.insert(invokeThirdLogWithBLOBs);
        }

        return null;
    }

    /*public OilMobileCardInfo phoneRegisterQuery(String copartnerId,String copartnerPwd){
        OilMobileCardInfo mbcard =  mobileCardService.getNewMobileCard();
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setRequestId("UserPhoneIsRegisterQuery");
        requestEntity.setRequestFlow("");
        requestEntity.setMoney("0");
        requestEntity.setCopartnerId(copartnerId);
        requestEntity.setPassword("");
        requestEntity.setCard("");
        requestEntity.setExtend("");
        requestEntity.setExtend2("");
        ReqParameters reqParameters = new ReqParameters();
        reqParameters.add("BuExtend1","15826164847");
        requestEntity.setReqParameters(reqParameters);
        GTRequestSigner.signedRequest(requestEntity,copartnerPwd);

        InvokeThirdLogWithBLOBs invokeThirdLogWithBLOBs = EntityUtil.createInvokeThirdLog(mbcard.getMobile(), EnumGtOil.QueryMobile.getCode(),requestEntity.getRequestId());
        try{
            ResponseEntity responseEntity = null;

            String ex = XmlUtils.toStr(requestEntity,false,true);
            logger.debug("请求request-->{}", ex);
            invokeThirdLogWithBLOBs.setRequest(ex);
            Response response = HttpUtils.okHttpPost(gtConfig.getUrl(),ex);
            //System.out.println(response.body().string());
            if (response != null && response.isSuccessful()) {
                String resTxt = response.body().string();
                invokeThirdLogWithBLOBs.setResponse(resTxt);
                System.out.println(resTxt);
            }
        }catch (Exception ex){
            logger.error("{}", ex);
            invokeThirdLogWithBLOBs.setResponse(ex.toString());
        }finally {
            invokeThirdLogMapper.insert(invokeThirdLogWithBLOBs);
        }
        return null;
    }*/

    public OilMobileCardInfo phoneRegister(String copartnerId,String copartnerPwd){
        OilMobileCardInfo mbcard =  mobileCardService.getNewMobileCard();
        mbcard.setStatus(EnumCardStatus.Fail.getCode());
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setRequestId("UserRegNew");
        requestEntity.setRequestFlow("");
        requestEntity.setMoney("0");
        requestEntity.setCopartnerId(copartnerId);
        String pwd = mbcard.getMobile()+mbcard.getSalt();
        requestEntity.setPassword(EntityUtil.getGTPwd(pwd)+pwd);
        requestEntity.setCard(mbcard.getMobile());
        requestEntity.setExtend("");
        requestEntity.setExtend2("");
        ReqParameters reqParameters = new ReqParameters();
        reqParameters.add("BuExtend1",mbcard.getMobile());
        requestEntity.setReqParameters(reqParameters);
        GTRequestSigner.signedRequest(requestEntity,copartnerPwd);

        InvokeThirdLogWithBLOBs invokeThirdLogWithBLOBs = EntityUtil.createInvokeThirdLog(mbcard.getMobile(), EnumGtOil.RegisterMobile.getCode(),requestEntity.getRequestId());
        try{
            ResponseEntity responseEntity = null;

            String ex = XmlUtils.toStr(requestEntity,false,true);
            invokeThirdLogWithBLOBs.setRequest(ex);
            logger.debug("请求request-->{}", ex);

            Response response = HttpUtils.okHttpPost(gtConfig.getUrl(),ex);
            if (response != null && response.isSuccessful()) {
                String resTxt = response.body().string();
                responseEntity = XmlUtils.parseBean(resTxt,ResponseEntity.class);
                if(responseEntity.getCode().equals("0")){
                    String userid = EntityUtil.ReqParametersByKey(responseEntity.getReqParameters(),"strExtend1");
                    mbcard.setUserId(userid);
                    List<OilMobileCardDetail> insertDetails = userBindCardQuery(mbcard,copartnerId,copartnerPwd);
                    if(setPayPwd(mbcard.getMobile(),EntityUtil.getGTPwd(mbcard.getSalt()+mbcard.getMobile()),copartnerId,copartnerPwd)) {
                        mbcard.setStatus(EnumCardStatus.Enable.getCode());
                        mobileCardService.insertMobileCard(mbcard);
                        mobileCardService.insertOrUpdateMobileCardDetails(insertDetails);
                    }else{
                        mobileCardService.insertMobileCard(mbcard);
                    }
                }else {//注册失败
                    mobileCardService.insertMobileCard(mbcard);
                }
                invokeThirdLogWithBLOBs.setResponse(resTxt);
            }else{
                String resTxt =response == null ?"应答为空": response.body().string();
                invokeThirdLogWithBLOBs.setResponse(resTxt);
            }
        }catch (Exception ex){
            logger.error("{}", ex);
            invokeThirdLogWithBLOBs.setResponse(ex.toString());
        }finally {
            invokeThirdLogMapper.insert(invokeThirdLogWithBLOBs);
        }
        return null;
    }

    public List<OilMobileCardDetail> userBindCardQuery(OilMobileCardInfo mbcard,String copartnerId,String copartnerPwd){
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setRequestId("UserBindCardQuery");
        requestEntity.setRequestFlow("");
        requestEntity.setMoney("0");
        requestEntity.setCopartnerId(copartnerId);
        requestEntity.setPassword("");
        requestEntity.setCard("");
        requestEntity.setExtend("");
        requestEntity.setExtend2("");
        ReqParameters reqParameters = new ReqParameters();
        reqParameters.add("BuExtend1",mbcard.getMobile());
        requestEntity.setReqParameters(reqParameters);
        GTRequestSigner.signedRequest(requestEntity,copartnerPwd);

        InvokeThirdLogWithBLOBs invokeThirdLogWithBLOBs = EntityUtil.createInvokeThirdLog(mbcard.getMobile(), EnumGtOil.UserBindCardQuery.getCode(),requestEntity.getRequestId());
        try{
            ResponseEntity responseEntity = null;

            String ex = XmlUtils.toStr(requestEntity,false,true);
            invokeThirdLogWithBLOBs.setRequest(ex);
            logger.debug("请求request-->{}", ex);

            Response response = HttpUtils.okHttpPost(gtConfig.getUrl(),ex);
            if (response != null && response.isSuccessful()) {
                String resTxt = response.body().string();
                System.out.println(resTxt);
                responseEntity = XmlUtils.parseBean(resTxt,ResponseEntity.class);
                if(responseEntity.getCode().equals("0")){
                    String busiExtend = EntityUtil.ReqParametersByKey(responseEntity.getReqParameters(),"BusiExtend");
                    if(StringUtils.isEmpty(busiExtend)){
                        return null;
                    }
                    List<String[]> result = EntityUtil.formatResult(busiExtend);
                    Date now = new Date();
                    List<OilMobileCardDetail> insertDetails = new ArrayList<>();
                    for(String[] str : result){
                       OilMobileCardDetail detail = new OilMobileCardDetail();
                       detail.setMobile(mbcard.getMobile());
                       detail.setCreatedAt(now);
                       detail.setUpdatedAt(now);

                       detail.setCardNo(str[0]);
                       detail.setCardType(Byte.valueOf(str[1]));
                       detail.setUserUid(str[2]);
                       detail.setOilNumber(str[3]);
                       detail.setCardStatus(Byte.valueOf(str[4]));
                       detail.setCardBalance(Integer.valueOf(str[5]) * 100);
                       detail.setPayBalance(Integer.valueOf(str[6]) * 100);

                       insertDetails.add(detail);
                    }

                    invokeThirdLogWithBLOBs.setResponse(resTxt);
                    return insertDetails;
                }
            }else{
                String resTxt =response == null ?"应答为空": response.body().string();
                invokeThirdLogWithBLOBs.setResponse(resTxt);
            }
        }catch (Exception ex){
            logger.error("{}", ex);
            invokeThirdLogWithBLOBs.setResponse(ex.toString());
        }finally {
            invokeThirdLogMapper.insert(invokeThirdLogWithBLOBs);
        }
        return null;
    }

    /**
     * 充值
     * @param cardNo
     * @param amount
     * @param copartnerId
     * @param copartnerPwd
     */
    public boolean recharge(String cardNo,int amount,String copartnerId,String copartnerPwd){
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setRequestId("260");
        requestEntity.setRequestFlow("cz"+EntityUtil.getId(4));
        requestEntity.setMoney(String.valueOf(amount));
        requestEntity.setCopartnerId(copartnerId);
        requestEntity.setPassword("");
        requestEntity.setCard(cardNo);
        requestEntity.setExtend("");
        requestEntity.setExtend2("");

        GTRequestSigner.signedRequest(requestEntity,copartnerPwd);
        InvokeThirdLogWithBLOBs invokeThirdLogWithBLOBs = EntityUtil.createInvokeThirdLog(cardNo, EnumGtOil.Recharge.getCode(),requestEntity.getRequestId());
        try{
            ResponseEntity responseEntity = null;

            String ex = XmlUtils.toStr(requestEntity,false,true);
            invokeThirdLogWithBLOBs.setRequest(ex);
            logger.debug("请求request-->{}", ex);

            Response response = HttpUtils.okHttpPost(gtConfig.getUrl(),ex);
            if (response != null && response.isSuccessful()) {
                String resTxt = response.body().string();
                System.out.println(resTxt);
                responseEntity = XmlUtils.parseBean(resTxt,ResponseEntity.class);
                if(responseEntity.getCode().equals("0")){
                    OilMobileCardInfo cardInfo = mobileCardService.getMobileCardInfoByCardNo(cardNo);
                    List<OilMobileCardDetail> insertDetails = userBindCardQuery(cardInfo,copartnerId,copartnerPwd);
                    if(insertDetails == null){
                        throw new BillException(9999,"查询卡信息失败");
                    }
                    return mobileCardService.insertOrUpdateMobileCardDetails(insertDetails);
                }
                invokeThirdLogWithBLOBs.setResponse(resTxt);
            }else{
                String resTxt = response == null ?"应答为空": response.body().string();
                invokeThirdLogWithBLOBs.setResponse(resTxt);
            }
        }catch (Exception ex){
            logger.error("{}", ex);
            invokeThirdLogWithBLOBs.setResponse(ex.toString());
        }finally {
            invokeThirdLogMapper.insert(invokeThirdLogWithBLOBs);
        }
        return false;
    }

    public boolean setPayPwd(String mobile,String pwd,String copartnerId,String copartnerPwd){
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setRequestId("UserResetPayPwdNew");
        requestEntity.setRequestFlow("");
        requestEntity.setMoney("0");
        requestEntity.setCopartnerId(copartnerId);
        requestEntity.setPassword("");
        requestEntity.setCard(mobile);
        requestEntity.setExtend("");
        requestEntity.setExtend2("");

        ReqParameters reqParameters = new ReqParameters();
        reqParameters.add("strNewPwd",EntityUtil.getGTPwd(pwd));
        requestEntity.setReqParameters(reqParameters);
        GTRequestSigner.signedRequest(requestEntity,copartnerPwd);
        InvokeThirdLogWithBLOBs invokeThirdLogWithBLOBs = EntityUtil.createInvokeThirdLog(mobile, EnumGtOil.SetPayPwd.getCode(),requestEntity.getRequestId());

        try{
            ResponseEntity responseEntity = null;

            String ex = XmlUtils.toStr(requestEntity,false,true);
            invokeThirdLogWithBLOBs.setRequest(ex);
            logger.debug("请求request-->{}", ex);

            Response response = HttpUtils.okHttpPost(gtConfig.getUrl(),ex);
            if (response != null && response.isSuccessful()) {
                String resTxt = response.body().string();
                //System.out.println(resTxt);
                invokeThirdLogWithBLOBs.setResponse(resTxt);
                responseEntity = XmlUtils.parseBean(resTxt,ResponseEntity.class);
                if(responseEntity.getCode().equals("0")){
                   return true;
                }
            }else{
                String resTxt = response == null ?"应答为空": response.body().string();
                invokeThirdLogWithBLOBs.setResponse(resTxt);
            }
        }catch (Exception ex){
            logger.error("{}", ex);
            invokeThirdLogWithBLOBs.setResponse(ex.toString());
        }finally {
            invokeThirdLogMapper.insert(invokeThirdLogWithBLOBs);
        }

        return false;
    }
}
