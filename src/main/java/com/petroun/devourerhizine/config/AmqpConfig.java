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

package com.petroun.devourerhizine.config;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


@Configuration
public class AmqpConfig {

    private static Logger logger = LoggerFactory.getLogger(AmqpConfig.class);
    private static ConnectionFactory connectionFactory;

    static {
        connectionFactory = new ConnectionFactory();
    }

    @Autowired
    private RabbitMQProperties rabbitMQProperties;


    @PostConstruct
    public void construct() {
        connectionFactory.setHost(rabbitMQProperties.getHost());
        connectionFactory.setPort(rabbitMQProperties.getPort());
        connectionFactory.setUsername(rabbitMQProperties.getUser());
        connectionFactory.setPassword(rabbitMQProperties.getPassword());
        connectionFactory.setConnectionTimeout(5000);
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(5);
        connectionFactory.setRequestedHeartbeat(60);
        connectionFactory.setVirtualHost("devourerhizine");
        logger.info("{}", rabbitMQProperties);
    }

    @Bean
    public ConnectionFactory rabbitMQConnectionFactory() {
        return connectionFactory;
    }

    @Bean
    public Connection rabbitMQConnection(ConnectionFactory connectionFactory) throws Exception {
        return connectionFactory.newConnection();
    }

}
