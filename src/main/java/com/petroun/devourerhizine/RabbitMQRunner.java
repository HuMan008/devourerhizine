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

package com.petroun.devourerhizine;

import cn.gotoil.bill.tools.ObjectHelper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.petroun.devourerhizine.classes.rabbitmq.MQDefiner;
import com.petroun.devourerhizine.classes.rabbitmq.SuppressRabbitConsumer;
import com.petroun.devourerhizine.model.OptionKeys;
import com.petroun.devourerhizine.provider.petroun.Rhizine;
import com.petroun.devourerhizine.service.OptionService;
import com.petroun.devourerhizine.service.cnpc.CnpcRechargeService;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitMQRunner implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(RabbitMQRunner.class);

    @Autowired
    private Connection connection;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private CnpcRechargeService cnpcRechargeService;

    @Autowired
    private OptionService optionService;


    @Override
    public void run(String... args) throws Exception {
        optionService.reload();

        Rhizine.configure(optionService.get(OptionKeys.RHIZINE_HOST),
                optionService.get(OptionKeys.RHIZINE_XU),
                optionService.get(OptionKeys.RHIZINE_SECRET));

        logger.info("============start MQ runner============");
        MQDefiner.defineDelayExchangeAndQueues(connection);
        cnpcThrough();
        cnpcInquire();
        cnpcRegain();

        //
        gotoilQR();
    }

    /**
     * @throws Exception
     */
    private void cnpcThrough() throws Exception {

//        for (int i = 0; i < 2; i++) {
        logger.info("============start cnpcThrough worker============");
        Channel channel = MQDefiner.ThroughChannel(applyConnection(), 32);
        CnpcThroughConsumer througher = new CnpcThroughConsumer(channel);
        channel.basicConsume(MQDefiner.Q_THROUGH, false, througher);
//        }
    }

    /**
     * @throws Exception
     */
    private void cnpcInquire() throws Exception {
        logger.info("============start cnpcInquire worker============");
//        for (int i = 0; i < 5; i++) {
        Channel channel = MQDefiner.InquireChannel(applyConnection(), 32);
        CnpcInquireConsumer inquirer = new CnpcInquireConsumer(channel);
        channel.basicConsume(MQDefiner.Q_INQUIRE, false, inquirer);
//        }
    }


    /**
     * @throws Exception
     */
    private void cnpcRegain() throws Exception {
        logger.info("============start cnpcRegain worker============");
        Channel channel = MQDefiner.RegainChannel(applyConnection(), 8);
        CnpcRegainConsumer regainer = new CnpcRegainConsumer(channel);
        channel.basicConsume(MQDefiner.Q_REGAIN, false, regainer);
    }


    private void gotoilQR() throws Exception {
        logger.info("============start gotoil qr  worker============");
        Channel channel = MQDefiner.gotoilQRCode(applyConnection(), 8);
        GotoilQRConsumer gotoilQr = new GotoilQRConsumer(channel);
        channel.basicConsume(MQDefiner.Q_GOTOIL_QR, false, gotoilQr);
    }


    /**
     * @return
     */
    private Connection applyConnection() {
        if (connection == null || !connection.isOpen()) {
            try {
                connection = connectionFactory.newConnection();
            } catch (Exception ex) {
                logger.error("{}", ex);
            }
        }

        return connection;
    }


    /**
     *
     */
    private class CnpcThroughConsumer extends SuppressRabbitConsumer {

        public CnpcThroughConsumer(Channel channel) {
            super(channel);
        }

        public void startWork() {
            try {
                cnpcThrough();
            } catch (Exception ex) {
                logger.error("{}", ex);
            }
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
            try {
                Long id = ObjectHelper.getObjectMapper().readValue(body, Long.class);
                logger.info("Handle CNPC Through Task:{}", id);
                cnpcRechargeService.through(id);
                getChannel().basicAck(envelope.getDeliveryTag(), false);
            } catch (InvalidFormatException ex) {
                logger.info("{}", ex);
                getChannel().basicAck(envelope.getDeliveryTag(), false);
            } catch (Exception ex) {
                logger.error("{}", ex);
            }
        }
    }

    /**
     *
     */
    private class CnpcInquireConsumer extends SuppressRabbitConsumer {
        public CnpcInquireConsumer(Channel channel) {
            super(channel);
        }

        public void startWork() {
            try {
                cnpcInquire();
            } catch (Exception ex) {
                logger.error("{}", ex);
            }
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
            try {
//                CnpcOrder cnpcOrder = ObjectHelper.getObjectMapper().readValue(body, CnpcOrder.class);
//                logger.info("Handle CNPC Inquire Task:{}", cnpcOrder);

                Long id = ObjectHelper.getObjectMapper().readValue(body, Long.class);
                cnpcRechargeService.inquire(id, false);
                getChannel().basicAck(envelope.getDeliveryTag(), false);

            } catch (InvalidFormatException ex) {
                logger.info("{}", ex);
                getChannel().basicAck(envelope.getDeliveryTag(), false);
            } catch (Exception ex) {
                logger.error("{}", ex);
            }
        }
    }


    /**
     *
     */
    private class CnpcRegainConsumer extends SuppressRabbitConsumer {
        public CnpcRegainConsumer(Channel channel) {
            super(channel);
        }

        public void startWork() {
            try {
                cnpcRegain();
            } catch (Exception ex) {
                logger.error("{}", ex);
            }
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
            try {
                Long id = ObjectHelper.getObjectMapper().readValue(body, Long.class);
                logger.info("Handle CNPC Regain Task:{}", id);
                cnpcRechargeService.regain(id);
                getChannel().basicAck(envelope.getDeliveryTag(), false);
            } catch (InvalidFormatException ex) {
                logger.info("{}", ex);
                getChannel().basicAck(envelope.getDeliveryTag(), false);
            } catch (Exception ex) {
                logger.error("{}", ex);
            }
        }
    }


    private class GotoilQRConsumer extends SuppressRabbitConsumer {
        public GotoilQRConsumer(Channel channel) {
            super(channel);
        }

        @Override
        public void startWork() {
            try {
                gotoilQR();
            } catch (Exception ex) {
                logger.error("{}", ex);
            }
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                   byte[] body) throws IOException {
            try {
                logger.info("----->{}", new String(body));
                // TODO

                //                Long id = ObjectHelper.getObjectMapper().readValue(body, .class);
                //                logger.info("Handle CNPC Regain Task:{}", id);
                //                cnpcRechargeService.regain(id);
                //                getChannel().basicAck(envelope.getDeliveryTag(), false);
                getChannel().basicReject(envelope.getDeliveryTag(), false);
            } catch (InvalidFormatException ex) {
                logger.info("{}", ex);
                getChannel().basicAck(envelope.getDeliveryTag(), false);
            } catch (Exception ex) {
                logger.error("{}", ex);
            }
        }
    }

}
