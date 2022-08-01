package com.datsddos.controller.app.emqx.operator;

import com.datsddos.controller.app.service.integration.cache.RedisClients;
import com.datsddos.controller.app.service.integration.emqx.MessageBrokerClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OnAttackMessageOperator {

    @Value("${emqx.attack_message_topic}")
    private String attackMessageTopic;

    @Autowired
    private MessageBrokerClients messageBrokerClients;

    @Autowired
    private RedisClients redisClients;

    private static final Logger logger = LoggerFactory.getLogger(OnAttackMessageOperator.class);

    public void makeAttackMessageArrivedOperations(String topic) {
        if (topic.equalsIgnoreCase(attackMessageTopic)) {
            logger.info("Attack message operations will be implemented");
            messageBrokerClients.getConnectedClientsFromMessageBroker();
            redisClients.getContractUsersFromRedis();
        }
    }
}
