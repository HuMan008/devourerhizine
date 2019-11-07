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
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.ShutdownSignalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SuppressRabbitConsumer extends DefaultConsumer {

    private static Logger logger = LoggerFactory.getLogger(SuppressRabbitConsumer.class);

    public abstract void startWork();

    public SuppressRabbitConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {

        super.handleShutdownSignal(consumerTag, sig);

        logger.error("HandleShutdownSignal {} == {}", consumerTag, sig);
        logger.error("Connection Close Reason ==========={}", getChannel().getConnection().getCloseReason());

        try {
            getChannel().getConnection().close();
            getChannel().close();
        } catch (Exception ex) {
            logger.error("{}", ex);
        }

        try {
            startWork();
        } catch (Exception ex) {
            logger.error("Restart...Error:{}", ex);
        }
    }

}
