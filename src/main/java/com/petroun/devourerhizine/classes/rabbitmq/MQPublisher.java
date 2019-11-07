/*
 * ******************************************************
 *  * Copyright (C) 2018 cluries  <cluries#me.com>
 *  *
 *  * This file is part of Devourer.
 *  *
 *  * Devourer can not be copied and/or distributed without the express
 *  * permission of cluries
 *  ******************************************************
 */

package com.petroun.devourerhizine.classes.rabbitmq;

import cn.gotoil.bill.tools.ObjectHelper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 *
 */
@SuppressWarnings("unused")
public class MQPublisher {

    private static Logger logger = LoggerFactory.getLogger(MQPublisher.class);

    private static ThreadLocal<Channel> threadLocalChannels = ThreadLocal.withInitial(() -> null);

    private static ConcurrentLinkedDeque<Channel> channelConcurrentLinkedDeque = new ConcurrentLinkedDeque<>();

    private static boolean useThreadLocalStorageChannel = false;

    public enum DelayInterval {
        IMMEDIATELY, S30, M1, M2, M5, M10, M30;

        public String exchangeName() {
            if (this.equals(DelayInterval.S30)) {
                return MQDefiner.DelayExchangeName(MQDefiner.DELAY_30SEC);
            }

            if (this.equals(DelayInterval.M1)) {
                return MQDefiner.DelayExchangeName(MQDefiner.DELAY_1MIN);
            }

            if (this.equals(DelayInterval.M2)) {
                return MQDefiner.DelayExchangeName(MQDefiner.DELAY_2MIN);
            }

            if (this.equals(DelayInterval.M5)) {
                return MQDefiner.DelayExchangeName(MQDefiner.DELAY_5MIN);
            }

            if (this.equals(DelayInterval.M10)) {
                return MQDefiner.DelayExchangeName(MQDefiner.DELAY_10MIN);
            }

            if (this.equals(DelayInterval.M30)) {
                return MQDefiner.DelayExchangeName(MQDefiner.DELAY_30MIN);
            }

            return "Delay";
        }
    }

    /**
     * @param connection
     * @param routingKey
     * @param obj
     */
    public static void cnpc(Connection connection, String routingKey, Object obj) {
        cnpc(connection, routingKey, obj, DelayInterval.IMMEDIATELY);
    }

    /**
     * @param connection
     * @param routingKey
     * @param obj
     * @param delay
     */
    public static void cnpc(Connection connection, String routingKey, Object obj, DelayInterval delay) {
        publish(connection, MQDefiner.EX_CNPC, routingKey, obj, delay);
    }

    /**
     * @param connection
     * @param routingKey
     * @param obj
     */
    public static void refuelCode(Connection connection, String routingKey, Object obj) {
        refuelCode(connection, routingKey, obj, DelayInterval.IMMEDIATELY);
    }

    /**
     * @param connection
     * @param routingKey
     * @param obj
     * @param delay
     */
    public static void refuelCode(Connection connection, String routingKey, Object obj, DelayInterval delay) {
        publish(connection, MQDefiner.EX_REFUEL_CODE, routingKey, obj, delay);
    }


    /**
     * @param connection
     * @param exchange
     * @param routingKey
     * @param obj
     * @param delay
     */
    public static void publish(Connection connection, String exchange, String routingKey, Object obj, DelayInterval delay) {
        Channel channel = getChannel(connection);

        try {
            byte[] body = ObjectHelper.getObjectMapper().writeValueAsBytes(obj);
            String exchangeName = exchange;

            if (delay != null && !delay.equals(DelayInterval.IMMEDIATELY)) {
                exchangeName = delay.exchangeName();
            }

            channel.basicPublish(exchangeName, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, body);
        } catch (Exception ex) {
            if (channel == null || !channel.isOpen()) {
                if (channel != null) {
                    try {
                        channel.close();
                    } catch (Exception exc) {
                        logger.error("{}", exc);
                    }
                }

                remallocPublishChannel(connection);
            }

            logger.error("{}", ex);
        } finally {
            if (!useThreadLocalStorageChannel) {
                closeChannel(channel);
            }
        }
    }

    /**
     * @param channel
     */
    public static void closeChannel(Channel channel) {
        if (channel == null) {
            return;
        }
        try {
            channel.close();
            if (useThreadLocalStorageChannel) {
                threadLocalChannels.set(null);
            }
        } catch (Exception ex) {
            logger.error("{}", ex);
        }
    }

    /**
     * @param connection
     * @return
     */
    private static Channel getChannel(Connection connection) {

        Channel channel = null;
        if (useThreadLocalStorageChannel) {
            channel = threadLocalChannels.get();
        }

        if (channel == null || !channel.isOpen()) {
            channel = remallocPublishChannel(connection);
        }

        return channel;
    }

    /**
     * @param connection
     * @return
     */
    private static Channel remallocPublishChannel(Connection connection) {
        logger.info("-----------------> Remalloc Publish Channel <-----------------");

        Channel channel = null;
        if (useThreadLocalStorageChannel) {
            channel = threadLocalChannels.get();
        }

        if (null != channel) {
            closeChannel(channel);
        }

        synchronized (connection) {
            try {
                channel = MQDefiner.ThroughChannel(connection, 1);
                if (useThreadLocalStorageChannel) {
                    threadLocalChannels.set(channel);
                }
            } catch (Exception ex) {
                logger.error("{}", ex);
            }
        }

        return channel;
    }


}
