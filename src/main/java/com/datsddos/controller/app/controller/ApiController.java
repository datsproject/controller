package com.datsddos.controller.app.controller;

import com.datsddos.controller.app.emqx.connector.MessageBrokerConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private MessageBrokerConnector messageBrokerConnector;

    @PostConstruct
    public void initSubscriptionForAttacks() {
        messageBrokerConnector.subscribeToAttackRequestsTopic();
    }

    @GetMapping("/test")
    public String testEp() {
        return "Test Successful !";
    }
}
