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


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */

//@SuppressWarnings("unused")
public class MQDefiner {

    private static Logger logger = LoggerFactory.getLogger(MQDefiner.class);

    public static final String EX_CNPC = "cnpc";
    public static final String EX_GOTOIL = "gotoil";

    public static final String RK_THROUGH_BIND = "#.through.#";
    public static final String RK_THROUGH = "through";
    public static final String Q_THROUGH = "CNPC_THROUGH";

    public static final String RK_INQUIRE_BIND = "#.inquire.#";
    public static final String RK_INQUIRE = "inquire";
    public static final String Q_INQUIRE = "CNPC_INQUIRE";

    public static final String RK_REGAIN_BIND = "#.regain.#";
    public static final String RK_REGAIN = "regain";
    public static final String Q_REGAIN = "CNPC_REGAIN";


    public static final String RK_GOTOIL_BIND = "#.gotoil.#";
    public static final String RK_GOTOIL_REFUEL = "gotoil.refuel";
    public static final String RK_GOTOIL_QUERY = "gotoil.transquey";
    public static final String Q_REFUEL = "GOTOIL_REFUEL";
    public static final String Q_TRANSQUERY = "GOTOIL_TRANSQUERY";




    public static final String RK_NOTIFICATION_MERCHANT_BIND = "#.notimechant.#";
    public static final String RK_NOTIFICATION_MERCHANT = "notimechant";
    public static final String Q_NOTIFICATION_MERCHANT = "REFEL_NOTIFI_MER";

    public static final String RK_NOTIFICATION_MERCHANT_FLITER_BIND = "#.fliter.#";
    public static final String RK_NOTIFICATION_MERCHANT_FLITER = "fliter";
    public static final String Q_NOTIFICATION_MERCHANT_FLITER = "REFEL_NOTIFI_MER_FLITER";


    public static final String EX_DEAD_LETTERS = "dead.letters";
    public static final String DELAY_5SEC = "DELAY_5SEC";
    public static final String DELAY_30SEC = "DELAY_30SEC";
    public static final String DELAY_1MIN = "DELAY_1MIN";
    public static final String DELAY_2MIN = "DELAY_2MIN";
    public static final String DELAY_5MIN = "DELAY_5MIN";
    public static final String DELAY_10MIN = "DELAY_10MIN";
    public static final String DELAY_30MIN = "DELAY_30MIN";


    /**
     * @param connection
     * @param prefetchSize
     * @return
     * @throws IOException
     */
    public static Channel ThroughChannel(Connection connection, int prefetchSize) {
        return natureBuildChannel(connection, prefetchSize, EX_CNPC, Q_THROUGH, RK_THROUGH_BIND);
    }


    /**
     * @param connection
     * @param prefetchSize
     * @return
     * @throws IOException
     */
    public static Channel InquireChannel(Connection connection, int prefetchSize) {
        return natureBuildChannel(connection, prefetchSize, EX_CNPC, Q_INQUIRE, RK_INQUIRE_BIND);
    }

    //    public static Channel gotoilQRRefuel(Connection connection, int prefetchSize) {
    //        return natureBuildChannel(connection, prefetchSize, EX_GOTOIL, Q_REFUEL, RK_GOTOIL_BIND);
    //
    //    }


    /**
     * @param connection
     * @param prefetchSize
     * @return
     * @throws IOException
     */
    public static Channel RegainChannel(Connection connection, int prefetchSize) {
        return natureBuildChannel(connection, prefetchSize, EX_CNPC, Q_REGAIN, RK_REGAIN_BIND);
    }

    /**
     * @param connection
     * @param prefetchSize
     * @return
     * @throws IOException
     */
    public static Channel gotoilRefuelChannel(Connection connection, int prefetchSize) {
        //        Channel channel = natureBuildChannel(connection, prefetchSize, EX_REFUEL_CODE,
        // Q_NOTIFICATION_MERCHANT, RK_NOTIFICATION_MERCHANT_BIND);
        //        try {
        //            channel.queueDeclare(Q_NOTIFICATION_MERCHANT_FLITER, true,false,false,null);
        //            channel.queueBind(Q_NOTIFICATION_MERCHANT_FLITER, EX_REFUEL_CODE,
        // RK_NOTIFICATION_MERCHANT_FLITER_BIND);
        //        }catch (Exception ex) {
        //            logger.error("{}", ex);
        //        }
        //        return channel;
        return natureBuildChannel(connection, prefetchSize, EX_GOTOIL, Q_REFUEL, RK_GOTOIL_REFUEL);
    }

    public static Channel gotoilTransQueryChannel(Connection connection, int prefetchSize) {
        return natureBuildChannel(connection, prefetchSize, EX_GOTOIL, Q_TRANSQUERY, RK_GOTOIL_QUERY);
    }

    /**
     * @param connection
     * @param prefetchSize
     * @param exchange
     * @param queue
     * @param route
     * @return
     */
    private static Channel natureBuildChannel(Connection connection, int prefetchSize, String exchange, String queue, String route) {
        try {
            Channel channel = defineChannel(connection, prefetchSize);
            channel.exchangeDeclare(exchange, "topic", true, false, false, null);
            channel.queueDeclare(queue, true, false, false, null);
            channel.queueBind(queue, exchange, route);
            channel.queueBind(queue, EX_DEAD_LETTERS, route);

            logger.info("NatureBuildChannel exchange:{} queue:{} route:{}", exchange, queue, route);
            return channel;
        } catch (Exception ex) {
            logger.error("{}", ex);
        }
        return null;
    }


    /**
     * @param connection
     */
    public static void defineDelayExchangeAndQueues(Connection connection) {
        logger.info("Define Delay Exchange And Queues");
        try {
            Channel channel = defineChannel(connection, 1);
            channel.exchangeDeclare(EX_DEAD_LETTERS, "topic", true, false, false, null);

            defineDelayQueue(channel, DELAY_5SEC, 5);
            defineDelayQueue(channel, DELAY_30SEC, 30);
            defineDelayQueue(channel, DELAY_1MIN, 60);
            defineDelayQueue(channel, DELAY_2MIN, 120);
            defineDelayQueue(channel, DELAY_5MIN, 300);
            defineDelayQueue(channel, DELAY_10MIN, 600);
            defineDelayQueue(channel, DELAY_30MIN, 1800);

        } catch (Exception ex) {
            logger.error("{}", ex);
        }
    }

    /**
     * @param name
     * @return
     */
    public static String DelayExchangeName(String name) {
        return "delay." + name.toLowerCase();
    }

    /**
     * @param name
     * @return
     */
    public static String DelayQueueName(String name) {
        return "DELAY_" + name.toUpperCase();
    }

    /**
     * @param channel
     * @param name
     * @param sec
     * @throws Exception
     */
    private static void defineDelayQueue(Channel channel, String name, int sec) throws Exception {
        String queueName = DelayQueueName(name);
        String exchangeName = DelayExchangeName(name);
        logger.info("Define delay exchange:{}  queue    :{}", exchangeName, queueName);

        channel.exchangeDeclare(exchangeName, "fanout", true, false, false, null);

        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 1000 * sec);
        args.put("x-dead-letter-exchange", EX_DEAD_LETTERS);
        channel.queueDeclare(queueName, true, false, false, args);
        channel.queueBind(queueName, exchangeName, "");
    }

    /**
     *
     * @param connection
     * @param prefetchSize
     * @return
     * @throws IOException
     */
    private static Channel defineChannel(Connection connection, int prefetchSize) throws IOException {
        Channel channel = connection.createChannel();
        channel.basicQos(prefetchSize);
        return channel;
    }


}
