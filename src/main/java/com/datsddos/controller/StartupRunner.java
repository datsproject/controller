package com.datsddos.controller;

import com.datsddos.controller.app.emqx.connector.MessageBrokerConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(StartupRunner.class);

    @Autowired
    private MessageBrokerConnector messageBrokerConnector;

    @Override
    public void run(String... args) throws Exception {
        logger.info("Controller Application has been started");
        messageBrokerConnector.subscribeToAttackRequestsTopic();
    }
}
