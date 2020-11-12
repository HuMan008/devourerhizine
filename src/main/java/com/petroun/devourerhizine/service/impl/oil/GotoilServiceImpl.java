package com.petroun.devourerhizine.service.impl.oil;

import com.petroun.devourerhizine.classes.rabbitmq.MQDefiner;
import com.petroun.devourerhizine.classes.rabbitmq.MQPublisher;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author think <syj247@qq.com>、
 * @date 2020/11/9、11:12
 */
@Service
@Slf4j
public class GotoilServiceImpl implements com.petroun.devourerhizine.service.oil.GotoilService {
    @Autowired
    private Connection connection;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Override
    public boolean appendGotoilTransSucessQueue(String id, int redo) {

        MQPublisher.DelayInterval delayInterval = MQPublisher.DelayInterval.IMMEDIATELY;
        if (redo == 0) {
            delayInterval = MQPublisher.DelayInterval.IMMEDIATELY;
        } else if (redo >= 1 && redo <= 5) {
            delayInterval = MQPublisher.DelayInterval.M1;
            ;
        } else if (redo > 5) {
            delayInterval = MQPublisher.DelayInterval.M5;
        }
        return publishToGotoilExchange(MQDefiner.RK_GOTOIL_REFUEL, id, delayInterval);
    }

    @Override
    public boolean appendGotoilQueryQueue(String id) {

        MQPublisher.DelayInterval delayInterval = MQPublisher.DelayInterval.S5;

        return publishToGotoilExchange(MQDefiner.RK_GOTOIL_QUERY, id, delayInterval);
    }

    /**
     * @param routeKey
     * @param value
     * @param delayInterval
     * @return
     */
    private boolean publishToGotoilExchange(String routeKey, Object value, MQPublisher.DelayInterval delayInterval) {
        boolean result = false;
        try {
            if (connection == null || !connection.isOpen()) {
                connection = connectionFactory.newConnection();
            }
            MQPublisher.gotoil(connection, routeKey, value, delayInterval);
            result = true;
        } catch (Exception ex) {
            log.error("{}", ex);
        }

        return result;
    }
}
