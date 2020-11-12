package com.petroun.devourerhizine.task;

import com.petroun.devourerhizine.RabbitMQRunner;
import com.petroun.devourerhizine.classes.tools.DateUtils;
import com.petroun.devourerhizine.provider.gt.GTConfig;
import com.petroun.devourerhizine.enums.EnumCardStatus;
import com.petroun.devourerhizine.enums.EnumTranStatus;
import com.petroun.devourerhizine.model.entity.OilCardUse;
import com.petroun.devourerhizine.model.entity.OilMobileCardInfo;
import com.petroun.devourerhizine.provider.gt.GTGateWay;
import com.petroun.devourerhizine.service.oil.CardService;
import com.petroun.devourerhizine.service.oil.MobileCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class OilTask {

    private static Logger logger = LoggerFactory.getLogger(OilTask.class);

    @Autowired
    CardService cardService;

    @Autowired
    GTGateWay gtGateWay;

    @Autowired
    GTConfig gtConfig;

    @Autowired
    MobileCardService mobileCardService;

    /**
     * 检查是否需要新增卡
     * 当可使用的卡<(总数卡/2)，增加总数的一半
     */
    @Scheduled(initialDelay = 60000, fixedDelay = 1000 * 60 * 5)
    public void addCard(){
        ArrayList<Byte> list = new ArrayList<>();
        list.add(EnumCardStatus.Enable.getCode());
        list.add(EnumCardStatus.Useing.getCode());
        // 总数
        long count = mobileCardService.queryCardsInfo(list);

        ArrayList<Byte> use = new ArrayList<>();
        use.add(EnumCardStatus.Enable.getCode());
        //目前可用
        long usecount = mobileCardService.queryCardsInfoByBalanceByZero(use);

        //总卡数一半
        BigDecimal b1 = new BigDecimal(count);
        long b2 = b1.divide(new BigDecimal(2)).longValue();
        if(b2 == 0){
            b2 = 1;
        }

        if(b2 >= usecount){
            for(long i = 0 ;  i < b2 ;i++ ){
                gtGateWay.phoneRegister(gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword());
            }
        }
        logger.info("增加可用卡--->总数={},目前可用={},增加卡数={}",count,usecount,b2);
    }

    /**
     * 检查绑定中的卡是否需要解绑
     */
    //@Scheduled(initialDelay = 60000, fixedDelay = 1000 * 60 * 10)
    public void checkBindCards(){
        ArrayList<Byte> status = new ArrayList<>();
        status.add(EnumCardStatus.Useing.getCode());
        List<OilMobileCardInfo> cards = mobileCardService.getMobileCardsByStatus(status);

        for(OilMobileCardInfo card : cards){
            OilCardUse use = cardService.queryById(card.getBindId());
            //todo 解绑没有记录的卡
            cardService.unbundlingNotInTrading(use.getId());
        }
    }

    /**
     * 交易记录
     */
    @Scheduled(initialDelay = 60000, fixedDelay = 1000 * 30)
    public void checkCardUse(){
        List<OilCardUse> list = cardService.queryCardUseByStatus(EnumTranStatus.Trading.getCode());
        if(list != null){
            for(OilCardUse cardUse : list){
                //没有有效时间表示二维码获取失败，直接解绑
                if(cardUse.getValidityTime() == null){
                    OilCardUse update = new OilCardUse();
                    update.setId(cardUse.getId());
                    update.setStatus(EnumTranStatus.fail.getCode());
                    cardService.updateOilCardUse(update);
                    cardService.unbundlingNotInTrading(cardUse.getId());
                }
            }
        }
    }
}
