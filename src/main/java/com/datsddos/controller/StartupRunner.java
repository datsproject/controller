package com.datsddos.controller;

import com.datsddos.controller.app.emqx.connector.MessageBrokerConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {
    @Autowired
    public MessageBrokerConnector messageBrokerConnector;

    @Override
    public void run(String... args) throws Exception {
        messageBrokerConnector.sendMessageToTopic();

    }
}
