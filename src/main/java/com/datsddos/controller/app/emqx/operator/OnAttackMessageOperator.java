package com.datsddos.controller.app.emqx.operator;

import com.datsddos.controller.app.emqx.connector.MessageBrokerConnector;
import com.datsddos.controller.app.model.participant.OperableParticipant;
import com.datsddos.controller.app.service.integration.cache.RedisClients;
import com.datsddos.controller.app.service.integration.emqx.MessageBrokerClients;
import com.datsddos.controller.app.utils.MapUtils;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OnAttackMessageOperator {

    @Value("${emqx.attack_message_topic}")
    private String attackMessageTopic;

    @Autowired
    private MessageBrokerClients messageBrokerClients;

    @Autowired
    private RedisClients redisClients;

    @Autowired
    private MapUtils mapUtils;

    @Autowired
    private MessageBrokerConnector messageBrokerConnector;

    private static final Logger logger = LoggerFactory.getLogger(OnAttackMessageOperator.class);

    @SneakyThrows
    public void makeAttackMessageArrivedOperations(String topic, String message) {
        if (topic.equalsIgnoreCase(attackMessageTopic)) {
            logger.info("Attack message operations will be implemented");

            Map<String, String> onlineParticipantsOnMessageBrokerMap = messageBrokerClients.getConnectedClientsFromMessageBroker();
            Map<String, String> redisClientsMap = redisClients.getContractUsersFromRedis();

            JSONObject messageJsonObj = new JSONObject(message);
            String attackId = messageJsonObj.getString("attackId");
            String attackRequest = redisClients.getStartAttackFromRedis(attackId);


            Map<String, OperableParticipant> finalTotalOnlineParticipantsMap = mapUtils.concatenateTwoMapsOverIntersections(redisClientsMap, onlineParticipantsOnMessageBrokerMap);
            messageBrokerConnector.sendMessageToAllAddressesImmediately(finalTotalOnlineParticipantsMap, attackRequest);
        }
    }
}
