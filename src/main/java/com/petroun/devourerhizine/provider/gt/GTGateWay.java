package com.petroun.devourerhizine.provider.gt;

import cn.gotoil.bill.exception.BillException;
import cn.gotoil.bill.tools.date.DateUtils;
import com.petroun.devourerhizine.classes.tools.EntityUtil;
import com.petroun.devourerhizine.classes.tools.HttpUtils;
import com.petroun.devourerhizine.classes.tools.MathUtils;
import com.petroun.devourerhizine.classes.tools.XmlUtils;
import com.petroun.devourerhizine.enums.EnumCardStatus;
import com.petroun.devourerhizine.enums.EnumGtOil;
import com.petroun.devourerhizine.enums.EnumTranStatus;
import com.petroun.devourerhizine.model.ReqParameters;
import com.petroun.devourerhizine.model.View.gt.ViewCardAndUse;
import com.petroun.devourerhizine.model.View.gt.ViewOilTrans;
import com.petroun.devourerhizine.model.View.gt.ViewQRCode;
import com.petroun.devourerhizine.model.entity.InvokeThirdLogWithBLOBs;
import com.petroun.devourerhizine.model.entity.OilCardUse;
import com.petroun.devourerhizine.model.entity.OilMobileCardDetail;
import com.petroun.devourerhizine.model.entity.OilMobileCardInfo;
import com.petroun.devourerhizine.model.mapper.InvokeThirdLogMapper;
import com.petroun.devourerhizine.service.oil.CardService;
import com.petroun.devourerhizine.service.oil.GotoilService;
import com.petroun.devourerhizine.service.oil.MobileCardService;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    private static final String OILUSERTOKEN = "OilUserToken:";


    /**
     * ??????usertoken
     * @param cardNo
     * @param pwd
     * @param copartnerId
     * @param copartnerPwd
     * @return
     */
    public String getUserToken(String cardNo,String pwd,String copartnerId,String copartnerPwd){

        String redisToken = stringRedisTemplate.opsForValue().get(OILUSERTOKEN+cardNo);
        long timeout = stringRedisTemplate.getExpire(OILUSERTOKEN+cardNo);

        if(!StringUtils.isEmpty(redisToken) && timeout > 60 * 2 ){
            return redisToken;
        }

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
            logger.debug("??????request-->{}", ex);

            Response response = HttpUtils.okHttpPost(gtConfig.getUrl(),ex);
            String token = null;
            if (response != null && response.isSuccessful()) {
                String resTxt = response.body().string();
                logger.debug("??????response-->{}", resTxt);
                invokeThirdLogWithBLOBs.setResponse(resTxt);

                responseEntity = XmlUtils.parseBean(resTxt, ResponseEntity.class);
                if ("0".equals(responseEntity.getCode())) {
                    String strExtend4 = EntityUtil.ReqParametersByKey(responseEntity.getReqParameters(),"strExtend4");
                    if(StringUtils.isEmpty(strExtend4)){
                        return null;
                    }
                    String[] codeAndSed = strExtend4.split("\\|");
                    token = codeAndSed[0];
                    String sed = codeAndSed[1];
                    stringRedisTemplate.opsForValue().set(OILUSERTOKEN+cardNo,token,Long.valueOf(sed), TimeUnit.SECONDS);
                    return token;
                }
            }else{
                String resTxt =response == null ?"????????????": response.body().string();
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
     * ???????????????
     * @param cardAndUser
     * @param sed
     * @param token
     * @param copartnerId
     * @param copartnerPwd
     * @return
     */
    public ViewQRCode getQRCode(ViewCardAndUse cardAndUser,int sed,String token,String copartnerId,String copartnerPwd){
        OilMobileCardInfo mobileCard = cardAndUser.getOilMobileCardInfo();
        OilCardUse oilCardUse = cardAndUser.getOilCardUse();
        if(!StringUtils.isEmpty(token)){
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
                logger.debug("??????request-->{}", ex);
                invokeThirdLogWithBLOBs.setRequest(ex);

                Response response = HttpUtils.okHttpPost(gtConfig.getUrl(),ex);
                if (response != null && response.isSuccessful()) {
                    String resTxt = response.body().string();
                    logger.debug("??????response-->{}", resTxt);
                    invokeThirdLogWithBLOBs.setResponse(resTxt);
                    responseEntity = XmlUtils.parseBean(resTxt, ResponseEntity.class);
                    if ("0".equals(responseEntity.getCode())) {
                        String strBRCode = EntityUtil.ReqParametersByKey(responseEntity.getReqParameters(),"strQRCode");
                        String nQRValidSeconds = EntityUtil.ReqParametersByKey(responseEntity.getReqParameters(),"nQRValidSeconds");
                        String time = responseEntity.getTime();
                        if(StringUtils.isEmpty(strBRCode)){
                            return null;
                        }
                        ViewQRCode viewQRCode = new ViewQRCode();
                        viewQRCode.setNQRValidSeconds(nQRValidSeconds);
                        viewQRCode.setStrBRCode(strBRCode);
                        viewQRCode.setTime(time);

                        return viewQRCode;
                    }
                }else{
                    String resTxt =response == null ?"????????????": response.body().string();
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
            throw new BillException(9999,"token????????????");
        }
        return null;
    }

    /**
     * ??????????????????
     * @param id
     * @param copartnerId
     * @param copartnerPwd
     * @return
     */
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
        //???????????????2??????
        Date endDate = com.petroun.devourerhizine.classes.tools.DateUtils.DateAddSed(oilCardUse.getValidityTime(),60*2);
        reqParameters.add("BuExtend3", DateUtils.simpleDatetimeFormatter().format(endDate));
        reqParameters.add("BuExtend6", "1");
        reqParameters.add("BuExtend7", "10");
        requestEntity.setReqParameters(reqParameters);
        GTRequestSigner.signedRequest(requestEntity,copartnerPwd);

        InvokeThirdLogWithBLOBs invokeThirdLogWithBLOBs = EntityUtil.createInvokeThirdLog(oilCardUse.getId(), EnumGtOil.QueryTrans.getCode(),requestEntity.getRequestId());
        /**
         * BuExtend1	??????
         * BuExtend2	????????????????????????2015-10-27 00:00:00???
         * BuExtend3	????????????????????????2015-10-27 23:59:59???
         * BuExtend6	????????????  ?????????1??????
         * BuExtend7	????????????
         */

        String resCode ="-1";
        try{
            ResponseEntity responseEntity = null;

            String ex = XmlUtils.toStr(requestEntity,false,true);
            logger.debug("??????request-->{}", ex);
            invokeThirdLogWithBLOBs.setRequest(ex);
            Response response = HttpUtils.okHttpPost(gtConfig.getUrl(),ex);


            //??????
            if (response != null && response.isSuccessful()) {
                String resTxt = response.body().string();
                invokeThirdLogWithBLOBs.setResponse(resTxt);

                logger.debug("??????response-->{}",resTxt);



                responseEntity = XmlUtils.parseBean(resTxt,ResponseEntity.class);
                resCode = responseEntity.getCode();
                if("0".equals(responseEntity.getCode())) {

                    /**
                     * BusiExtend ?????????????????????
                     * ????????????????????????~???????????????????????????|?????????????????????????????????
                     * ~????????????  ~????????????  ~??????id   ~????????????            ~????????????
                     * ~????????????  ~???????????????~???????????????~?????????????????????????????????~????????????
                     * ~???????????????~????????????  ~???????????? ~???????????????           ~???????????????
                     * ~??????????????????|
                     */
                    String str = EntityUtil.ReqParametersByKey(responseEntity.getReqParameters(), "BusiExtend");
                    List<String[]> result = EntityUtil.formatResult(str);
                    String[] resultDetail = result.get(0);

                    OilCardUse updateOilCardUser = new OilCardUse();
                    updateOilCardUser.setId(oilCardUse.getId());
                    updateOilCardUser.setStatus(EnumTranStatus.Success.getCode());
                    updateOilCardUser.setTerminalId(resultDetail[0]);
                    updateOilCardUser.setTransactionTime(DateUtils.simpleDatetimeFormatter().parse(resultDetail[1]));
                    updateOilCardUser.setBusinessId(resultDetail[2]);
                    updateOilCardUser.setBusinessName(resultDetail[3]);

                    updateOilCardUser.setFace(MathUtils.multiply100(resultDetail[4]));
                    updateOilCardUser.setMerchant(resultDetail[5]);
                    updateOilCardUser.setBalance(MathUtils.multiply100(resultDetail[6]));
                    updateOilCardUser.setFlowid(resultDetail[7]);
                    updateOilCardUser.setRise(resultDetail[9]);
                    updateOilCardUser.setRiseAfter(resultDetail[10]);
                    updateOilCardUser.setAmount(MathUtils.multiply100(resultDetail[11]));
                    updateOilCardUser.setOilPrice(MathUtils.multiply100(resultDetail[15]));

                    return updateOilCardUser;
                }

            }else{
                String resTxt =response == null ?"????????????": response.body().string();
                invokeThirdLogWithBLOBs.setResponse(resTxt);
            }

        }catch (Exception ex){
            logger.error("{}", ex);
            invokeThirdLogWithBLOBs.setResponse(ex.toString());
        }finally {
            invokeThirdLogMapper.insert(invokeThirdLogWithBLOBs);
        }
        if(!"60009".equals(resCode)){//????????????   60009=?????????
            OilCardUse updateOilCardUser = new OilCardUse();
            updateOilCardUser.setId(oilCardUse.getId());
            updateOilCardUser.setStatus(EnumTranStatus.QueryFail.getCode());
            return updateOilCardUser;
        }

        return null;
    }


    /**
     * ????????????
     * @param terminalId
     * @param copartnerId
     * @param copartnerPwd
     * @return
     */
    public ViewOilTrans queryStaionName(String terminalId,String copartnerId,String copartnerPwd){

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setRequestId("StationQueryByID");
        requestEntity.setRequestFlow("");
        requestEntity.setMoney("0");
        requestEntity.setCopartnerId(copartnerId);
        requestEntity.setPassword("");
        requestEntity.setCard("GtdeFUser001");
        requestEntity.setExtend("");
        ReqParameters StatsionreqParameters = new ReqParameters();
        StatsionreqParameters.add("BuExtend1",terminalId);
        requestEntity.setReqParameters(StatsionreqParameters);

        GTRequestSigner.signedRequest(requestEntity,copartnerPwd);
        InvokeThirdLogWithBLOBs queryStationLog = EntityUtil.createInvokeThirdLog(terminalId, EnumGtOil.QueryStation.getCode(), requestEntity.getRequestId());
        try {
            String ex = XmlUtils.toStr(requestEntity,false,true);
            queryStationLog.setRequest(ex);
            Response stationResponse = HttpUtils.okHttpPost(gtConfig.getUrl(), ex);
            if (stationResponse != null && stationResponse.isSuccessful()) {
                String resTxt = stationResponse.body().string();
                logger.debug("??????response-->{}", resTxt);
                queryStationLog.setResponse(resTxt);
                ResponseEntity responseEntity = XmlUtils.parseBean(resTxt,ResponseEntity.class);
                if ("0".equals(responseEntity.getCode())) {
                    /**
                     * String str = EntityUtil.ReqParametersByKey(responseEntity.getReqParameters(), "BusiExtend");
                     *             List<String[]> result = EntityUtil.formatResult(str);
                     *             String[] resultDetail = result.get(0);
                     */
                    ViewOilTrans viewOilTrans = new ViewOilTrans();
                    String stationstr = EntityUtil.ReqParametersByKey(responseEntity.getReqParameters(), "BusiExtend");
                    List<String[]> stationStrs = EntityUtil.formatResult(stationstr);
                    String[] stationResultDetail = stationStrs.get(0);
                    viewOilTrans.setStationId(stationResultDetail[1]);
                    viewOilTrans.setStationName(stationResultDetail[2]);
                    return viewOilTrans;
                }
            } else {
                String stationResTxt = stationResponse == null ? "????????????" : stationResponse.body().string();
                queryStationLog.setResponse(stationResTxt);
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

    /**
     * ???????????????
     * @param copartnerId
     * @param copartnerPwd
     * @return
     */
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
            logger.debug("??????request-->{}", ex);

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
                }else {//????????????
                    mobileCardService.insertMobileCard(mbcard);
                }
                invokeThirdLogWithBLOBs.setResponse(resTxt);
            }else{
                String resTxt =response == null ?"????????????": response.body().string();
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
     * ?????????????????????
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
            logger.debug("??????request-->{}", ex);

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
                       detail.setCardBalance(MathUtils.multiply100(str[5]));
                       detail.setPayBalance(MathUtils.multiply100(str[6]));

                       insertDetails.add(detail);
                    }

                    invokeThirdLogWithBLOBs.setResponse(resTxt);
                    return insertDetails;
                }
            }else{
                String resTxt =response == null ?"????????????": response.body().string();
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
     * ??????
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

        return responseResult(requestEntity,invokeThirdLogWithBLOBs);
    }

    /**
     * ??????????????????
     * @param mobile
     * @param pwd
     * @param copartnerId
     * @param copartnerPwd
     * @return
     */
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

        return responseResult(requestEntity,invokeThirdLogWithBLOBs);
    }

    /**
     * ???????????????40??????
     * @param mobile
     * @param copartnerId
     * @param copartnerPwd
     * @return
     */
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
        return responseResult(requestEntity,invokeThirdLogWithBLOBs);
    }

    private boolean responseResult(RequestEntity requestEntity,InvokeThirdLogWithBLOBs invokeThirdLogWithBLOBs){
        try{
            ResponseEntity responseEntity = null;

            String ex = XmlUtils.toStr(requestEntity,false,true);
            invokeThirdLogWithBLOBs.setRequest(ex);
            logger.debug("??????request-->{}", ex);

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
                String resTxt = response == null ?"????????????": response.body().string();
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
