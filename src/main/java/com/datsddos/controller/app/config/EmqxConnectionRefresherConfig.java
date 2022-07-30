package com.datsddos.controller.app.config;

import com.datsddos.controller.app.emqx.connector.MessageBrokerConnector;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class EmqxConnectionRefresherConfig {

    @Autowired
    private MessageBrokerConnector messageBrokerConnector;

    public static final Logger logger = LoggerFactory.getLogger(EmqxConnectionRefresherConfig.class);

    @Scheduled(initialDelay = 1000, fixedDelay = 5000)
    public MqttClient refreshContractUsersFixedRate() {
        return messageBrokerConnector.reConnectAttacksMqttClient();
    }
}
