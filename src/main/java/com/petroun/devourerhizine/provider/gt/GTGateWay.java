package com.petroun.devourerhizine.provider.gt;

import cn.gotoil.bill.exception.BillException;
import cn.gotoil.bill.tools.date.DateUtils;
import com.petroun.devourerhizine.classes.tools.EntityUtil;
import com.petroun.devourerhizine.classes.tools.HttpUtils;
import com.petroun.devourerhizine.classes.tools.XmlUtils;
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
import com.petroun.devourerhizine.service.Oil.GotoilService;
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


    @Autowired
    MobileCardService mobileCardService;

    @Autowired
    GotoilService gotoilService;



    public String getUserToken(String cardNo,String pwd,String copartnerId,String copartnerPwd){
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setRequestId("UserPayLogonNew");
        requestEntity.setMoney("0");
        requestEntity.setCopartnerId(copartnerId);
        requestEntity.setPassword(pwd);
        requestEntity.setCard(cardNo);
        requestEntity.setRequestFlow("");

        GTRequestSigner.signedRequest(requestEntity,copartnerPwd);

        InvokeThirdLogWithBLOBs invokeThirdLogWithBLOBs = EntityUtil.createInvokeThirdLog(cardNo, EnumGtOil.UseToken.getCode(),requestEntity.getRequestId());

        try{
            ResponseEntity responseEntity = null;

            String ex = XmlUtils.toStr(requestEntity,false,true);
            invokeThirdLogWithBLOBs.setRequest(ex);
            logger.debug("请求request-->{}", ex);

            Response response = HttpUtils.okHttpPost(gtConfig.getUrl(),ex);
            String token = null;
            if (response != null && response.isSuccessful()) {
                String resTxt = response.body().string();
                logger.debug("应答response-->{}", resTxt);
                invokeThirdLogWithBLOBs.setResponse(resTxt);

                responseEntity = XmlUtils.parseBean(resTxt, ResponseEntity.class);
                if ("0".equals(responseEntity.getCode())) {
                    String strExtend4 = EntityUtil.ReqParametersByKey(responseEntity.getReqParameters(),"strExtend4");
                    if(StringUtils.isEmpty(strExtend4)){
                        return null;
                    }
                    String[] codeAndSed = strExtend4.split("\\|");
                    token = codeAndSed[0];
                    return token;
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

    public String getQRCode(String mobile,String sendUrl,int amount,int sed,String copartnerId,String copartnerPwd){
        ViewCardAndUse cardAndUser = cardService.getOilCard(sendUrl,mobile,amount);
        OilMobileCardInfo mobileCard = cardAndUser.getOilMobileCardInfo();
        OilCardUse oilCardUse = cardAndUser.getOilCardUse();
        String token = getUserToken(mobileCard.getMobile(),("06"+mobileCard.getSalt()),copartnerId,copartnerPwd);
        if(!StringUtils.isEmpty(token)){
            //判断是否需要充值
            if(mobileCard.getBalance().compareTo(amount) <0){
                if(!recharge(mobileCard.getCardNo(),amount,copartnerId,copartnerPwd)){
                    throw new BillException(9999,"充值失败");
                }
            }


            RequestEntity requestEntity = new RequestEntity();
            requestEntity.setRequestId("GetAPPQRcodeOrBarcode");
            requestEntity.setMoney("0");
            requestEntity.setCopartnerId(copartnerId);
            requestEntity.setRequestFlow("");
            requestEntity.setPassword("");
            requestEntity.setCard(mobileCard.getMobile());

            ReqParameters reqParameters = new ReqParameters();
            reqParameters.add("strTransCert",token);
            reqParameters.add("strExtend1",mobileCard.getCardNo());
            reqParameters.add("strExtend2",String.valueOf(sed));
            requestEntity.setReqParameters(reqParameters);

            GTRequestSigner.signedRequest(requestEntity,copartnerPwd);

            InvokeThirdLogWithBLOBs invokeThirdLogWithBLOBs = EntityUtil.createInvokeThirdLog(oilCardUse.getId(), EnumGtOil.QRcode.getCode(),requestEntity.getRequestId());


            try{
                ResponseEntity responseEntity = null;

                String ex = XmlUtils.toStr(requestEntity,false,true);
                logger.debug("请求request-->{}", ex);
                invokeThirdLogWithBLOBs.setRequest(ex);

                Response response = HttpUtils.okHttpPost(gtConfig.getUrl(),ex);
                if (response != null && response.isSuccessful()) {
                    String resTxt = response.body().string();
                    logger.debug("应答response-->{}", resTxt);
                    invokeThirdLogWithBLOBs.setResponse(resTxt);
                    responseEntity = XmlUtils.parseBean(resTxt, ResponseEntity.class);
                    if ("0".equals(responseEntity.getCode())) {
                        String strBRCode = EntityUtil.ReqParametersByKey(responseEntity.getReqParameters(),"strQRCode");
                        String nQRValidSeconds = EntityUtil.ReqParametersByKey(responseEntity.getReqParameters(),"nQRValidSeconds");
                        String time = responseEntity.getTime();
                        if(StringUtils.isEmpty(strBRCode)){
                            return null;
                        }
                        String QRCode = strBRCode;
                        int QRCodeSed = Integer.valueOf(nQRValidSeconds);
                        cardService.updateCardUse(oilCardUse,time,QRCodeSed,strBRCode);
                        return QRCode;
                    }
                }else{
                    String resTxt =response == null ?"应答为空": response.body().string();
                    invokeThirdLogWithBLOBs.setResponse(resTxt);
                }
                return null;
            }catch (Exception ex){
                logger.error("{}", ex);
                invokeThirdLogWithBLOBs.setResponse(ex.toString());
            }finally {
                invokeThirdLogMapper.insert(invokeThirdLogWithBLOBs);
            }
        }else{
            throw new BillException(9999,"token获取失败");
        }
        return null;
    }

    public OilCardUse queryCardUserByRemote(String id,String copartnerId,String copartnerPwd){
        OilCardUse oilCardUse = cardService.queryById(id);
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setRequestId("UserHistoryQueryFYYPXF");
        requestEntity.setMoney("0");
        requestEntity.setCopartnerId(copartnerId);
        requestEntity.setRequestFlow("");
        requestEntity.setPassword("");
        requestEntity.setCard("");
        requestEntity.setExtend("");
        ReqParameters reqParameters = new ReqParameters();
        reqParameters.add("BuExtend1",oilCardUse.getCardNo());
        reqParameters.add("BuExtend2", DateUtils.simpleDatetimeFormatter().format(oilCardUse.getCreatedAt()));
        //过期时间加2分钟
        Date endDate = com.petroun.devourerhizine.classes.tools.DateUtils.DateAddSed(oilCardUse.getValidityTime(),60*2);
        reqParameters.add("BuExtend3", DateUtils.simpleDatetimeFormatter().format(endDate));
        reqParameters.add("BuExtend6", "1");
        reqParameters.add("BuExtend7", "10");
        requestEntity.setReqParameters(reqParameters);
        GTRequestSigner.signedRequest(requestEntity,copartnerPwd);

        InvokeThirdLogWithBLOBs invokeThirdLogWithBLOBs = EntityUtil.createInvokeThirdLog(oilCardUse.getId(), EnumGtOil.QueryTrans.getCode(),requestEntity.getRequestId());
        /**
         * BuExtend1	卡号
         * BuExtend2	开始时间（格式：2015-10-27 00:00:00）
         * BuExtend3	结束时间（格式：2015-10-27 23:59:59）
         * BuExtend6	开始条数  下标从1开始
         * BuExtend7	结束条数
         */


        try{
            ResponseEntity responseEntity = null;

            String ex = XmlUtils.toStr(requestEntity,false,true);
            logger.debug("请求request-->{}", ex);
            invokeThirdLogWithBLOBs.setRequest(ex);
            Response response = HttpUtils.okHttpPost(gtConfig.getUrl(),ex);

            //成功
            if (response != null && response.isSuccessful()) {
                String resTxt = response.body().string();
                invokeThirdLogWithBLOBs.setResponse(resTxt);
                logger.debug("应答response-->{}",resTxt);
                responseEntity = XmlUtils.parseBean(resTxt,ResponseEntity.class);
                if("0".equals(responseEntity.getCode())) {
                    /**
                     * BusiExtend 返回报文描述：
                     * 每个字段之间以“~”隔开，每一行已“|”隔开，报文格式如下：
                     * 终端号码~交易时间~业务id~业务名称~交易价值~交易商户~交易后余额~交易流水号~用户油库交易时平均单价
                     * ~消费油量~交易后油量~交易金额~代理流水~加油币编号~加油币名称~油品当前单价|
                     */
                    String str = EntityUtil.ReqParametersByKey(responseEntity.getReqParameters(), "BusiExtend");
                    List<String[]> result = EntityUtil.formatResult(str);
                    String[] resultDetail = result.get(0);
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

                    String stationName = queryStaionName(updateOilCardUser.getStation(), copartnerId, copartnerPwd);
                    if(!StringUtils.isEmpty(stationName)){
                        updateOilCardUser.setStationName(stationName);
                    }
                    if(cardService.updateOilCardUse(updateOilCardUser)) {
                        if (cardService.unbundlingNotInTrading(updateOilCardUser.getId())) {
                            //todo 成功通知
                            gotoilService.appendGotoilQueue(updateOilCardUser.getId(), 0);
                            return updateOilCardUser;
                        }
                    }
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

    public String queryStaionName(String stationId,String copartnerId,String copartnerPwd){
        /**
         * 查询站点
         */
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setRequestId("StationQueryByID");
        requestEntity.setRequestFlow("");
        requestEntity.setMoney("0");
        requestEntity.setCopartnerId(copartnerId);
        requestEntity.setPassword("");
        requestEntity.setCard("GtdeFUser001");
        requestEntity.setExtend("");
        ReqParameters StatsionreqParameters = new ReqParameters();
        StatsionreqParameters.add("BuExtend1",stationId);
        requestEntity.setReqParameters(StatsionreqParameters);

        GTRequestSigner.signedRequest(requestEntity,copartnerPwd);
        InvokeThirdLogWithBLOBs queryStationLog = EntityUtil.createInvokeThirdLog(stationId, EnumGtOil.QueryStation.getCode(), requestEntity.getRequestId());
        try {
            String ex = XmlUtils.toStr(requestEntity,false,true);
            queryStationLog.setRequest(ex);
            Response stationResponse = HttpUtils.okHttpPost(gtConfig.getUrl(), ex);
            if (stationResponse != null && stationResponse.isSuccessful()) {
                String resTxt = stationResponse.body().string();
                logger.debug("应答response-->{}", resTxt);
                ResponseEntity responseEntity = XmlUtils.parseBean(resTxt,ResponseEntity.class);
                if ("0".equals(responseEntity.getCode())) {
                    /**
                     * String str = EntityUtil.ReqParametersByKey(responseEntity.getReqParameters(), "BusiExtend");
                     *             List<String[]> result = EntityUtil.formatResult(str);
                     *             String[] resultDetail = result.get(0);
                     */
                    String stationstr = EntityUtil.ReqParametersByKey(responseEntity.getReqParameters(), "BusiExtend");
                    List<String[]> stationStrs = EntityUtil.formatResult(stationstr);
                    String[] stationResultDetail = stationStrs.get(0);
                    return stationResultDetail[2];
                }
                queryStationLog.setResponse(resTxt);
                invokeThirdLogMapper.insert(queryStationLog);
            } else {
                String stationResTxt = stationResponse == null ? "应答为空" : stationResponse.body().string();
                invokeThirdLogMapper.insert(queryStationLog);
            }
        }catch (Exception e){
            logger.error("{}", e);
            queryStationLog.setResponse(e.toString());
        }finally {
            invokeThirdLogMapper.insert(queryStationLog);
        }
        return null;
    }

    public OilMobileCardInfo phoneRegister(String copartnerId,String copartnerPwd){
        OilMobileCardInfo mbcard =  mobileCardService.getNewMobileCard();
        mbcard.setStatus(EnumCardStatus.Fail.getCode());
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setRequestId("UserRegNew");
        requestEntity.setRequestFlow("");
        requestEntity.setMoney("0");
        requestEntity.setCopartnerId(copartnerId);
        requestEntity.setPassword(EntityUtil.getGTPassword(mbcard.getMobile()));
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

                    // List<OilMobileCardDetail> insertDetails = userBindCardQuery(mbcard,copartnerId,copartnerPwd);
                    if(setPayPwd(mbcard.getMobile(),mbcard.getSalt(),copartnerId,copartnerPwd)) {
                        mbcard.setUserId(userid);
                        if(registerCard40(mbcard.getMobile(), copartnerId, copartnerPwd)){
                            List<OilMobileCardDetail> insertDetails = userBindCardQuery(mbcard,copartnerId,copartnerPwd);
                            mbcard.setStatus(EnumCardStatus.Enable.getCode());
                            mobileCardService.insertMobileCard(mbcard);
                            mobileCardService.insertOrUpdateMobileCardDetails(insertDetails);
                        }else{
                            mobileCardService.insertMobileCard(mbcard);
                        }
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

    /**
     * 查询加油卡余额
     * @param mbcard
     * @param copartnerId
     * @param copartnerPwd
     * @return
     */
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
                //System.out.println(resTxt);
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
                invokeThirdLogWithBLOBs.setResponse(resTxt);
                responseEntity = XmlUtils.parseBean(resTxt,ResponseEntity.class);
                if(responseEntity.getCode().equals("0")){
                    OilMobileCardInfo cardInfo = mobileCardService.getMobileCardInfoByCardNo(cardNo);
                    List<OilMobileCardDetail> insertDetails = userBindCardQuery(cardInfo,copartnerId,copartnerPwd);
                    if(insertDetails == null){
                        throw new BillException(9999,"查询卡信息失败");
                    }
                    return mobileCardService.insertOrUpdateMobileCardDetails(insertDetails);
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
        reqParameters.add("strNewPwd",pwd);
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

    public boolean registerCard40(String mobile,String copartnerId,String copartnerPwd){
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setRequestId("CreateOilStock");
        requestEntity.setRequestFlow("");
        requestEntity.setMoney("0");
        requestEntity.setCopartnerId(copartnerId);
        requestEntity.setPassword("");
        requestEntity.setCard(mobile);
        requestEntity.setExtend("");
        requestEntity.setExtend2("");

        ReqParameters reqParameters = new ReqParameters();
        reqParameters.add("nFlag","1011");
        requestEntity.setReqParameters(reqParameters);
        GTRequestSigner.signedRequest(requestEntity,copartnerPwd);
        InvokeThirdLogWithBLOBs invokeThirdLogWithBLOBs = EntityUtil.createInvokeThirdLog(mobile, EnumGtOil.RegisterCard40.getCode(),requestEntity.getRequestId());
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
