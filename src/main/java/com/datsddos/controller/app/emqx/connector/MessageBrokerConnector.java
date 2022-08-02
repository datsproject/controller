package com.datsddos.controller.app.emqx.connector;

import com.datsddos.controller.app.emqx.callback.OnMessageCallback;
import com.datsddos.controller.app.emqx.operator.OnAttackMessageOperator;
import com.datsddos.controller.app.model.participant.OperableParticipant;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MessageBrokerConnector {
    private static final Logger logger = LoggerFactory.getLogger(MessageBrokerConnector.class);

    @Value("${emqx.broker}")
    private String brokerAddress;

    @Value("${emqx.client_id}")
    private String clientId;

    @Value("${emqx.attack_message_topic}")
    private String attackMessageTopic;

    @Autowired
    OnAttackMessageOperator onAttackMessageOperator;

    private MqttConnectOptions mqttConnectOptions;

    private MqttClient mqttParticipantsClient;

    private MqttClient mqttAttacksClient;

    private static final int quality_of_service = 0;

    public MqttConnectOptions getConnectionOptions() {
        if (mqttConnectOptions == null) {
            mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setCleanSession(true);
            mqttConnectOptions.setKeepAliveInterval(300);
        }
        return mqttConnectOptions;
    }

    @SneakyThrows
    public MqttClient getParticipantsMqttClient() {
        if (mqttParticipantsClient == null || !mqttParticipantsClient.isConnected()) {
            logger.info("Connecting to broker: {}", brokerAddress);
            MemoryPersistence persistence = new MemoryPersistence();
            mqttParticipantsClient = new MqttClient(brokerAddress, clientId, persistence);
            mqttParticipantsClient.setCallback(new OnMessageCallback(onAttackMessageOperator));
            mqttParticipantsClient.connect(getConnectionOptions());
            logger.info("Connected to participant broker");
        }
        return mqttParticipantsClient;
    }

    @SneakyThrows
    public MqttClient getAttacksMqttClient() {
        if (mqttAttacksClient == null || !mqttAttacksClient.isConnected()) {
            logger.info("Connecting to broker: {}", brokerAddress);
            MemoryPersistence persistence = new MemoryPersistence();
            mqttAttacksClient = new MqttClient(brokerAddress, clientId, persistence);
            mqttAttacksClient.setCallback(new OnMessageCallback(onAttackMessageOperator));
            mqttAttacksClient.connect(getConnectionOptions());
            logger.info("Connected to attack requests broker");
        }
        return mqttAttacksClient;
    }


    @SneakyThrows
    public MqttClient reConnectAttacksMqttClient() {
        if (mqttAttacksClient != null && !mqttAttacksClient.isConnected()) {
            mqttAttacksClient.reconnect();
            logger.info("Connection lost so will be reconnected to attack requests broker");
        }
        return mqttAttacksClient;
    }

    @SneakyThrows
    public void subscribeToAttackRequestsTopic() {
        // Subscribe
        MqttClient mqttClientForSubscribeAttackRequests = getAttacksMqttClient();
        mqttClientForSubscribeAttackRequests.subscribe(attackMessageTopic);
    }


    @SneakyThrows
    public void sendSingleMessageToTopicImmediately(String content, String userWalletTopic) {
        // Subscribe
        MqttClient mqttClientForPublishMessageToParticipants = getParticipantsMqttClient();

        // Required parameters for message publishing
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(quality_of_service);
        mqttClientForPublishMessageToParticipants.publish(userWalletTopic, message);
        logger.info("Message published to topic " + userWalletTopic);

        mqttClientForPublishMessageToParticipants.disconnect();
        logger.info("Disconnected");
        mqttClientForPublishMessageToParticipants.close();
    }

    @SneakyThrows
    public void sendMessageToAllAddressesImmediately(Map<String, OperableParticipant> finalUserMap, String message) {
        for (Map.Entry<String, OperableParticipant> entry : finalUserMap.entrySet()) {
            sendSingleMessageToTopicImmediately(String.valueOf(message), entry.getKey());
        }
    }
}

