package com.datsddos.controller.app.emqx.callback;

import com.datsddos.controller.app.emqx.connector.MessageBrokerConnector;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class OnMessageCallback implements MqttCallback {

    private final Logger logger = LoggerFactory.getLogger(OnMessageCallback.class);

    @Autowired
    private MessageBrokerConnector messageBrokerConnector;

    public void connectionLost(Throwable cause) {
        // After the connection is lost, it usually reconnects here
        logger.error("disconnect，you can reconnect");
        messageBrokerConnector.subscribeToAttackRequestsTopic();
    }

    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // The messages obtained after subscribe will be executed here
        logger.info("Received message topic:" + topic);
        logger.info("Received message Qos:" + message.getQos());
        logger.info("Received message content:" + new String(message.getPayload()));
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        logger.info("Message Delivery completed -> " + token.isComplete());
    }
}
