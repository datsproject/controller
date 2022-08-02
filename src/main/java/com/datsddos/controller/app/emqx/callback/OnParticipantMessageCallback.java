package com.datsddos.controller.app.emqx.callback;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OnParticipantMessageCallback implements MqttCallback {

    private static final Logger logger = LoggerFactory.getLogger(OnParticipantMessageCallback.class);

    public void connectionLost(Throwable cause) {
        // After the connection is lost, it usually reconnects here
        logger.error("disconnect from participant broker，you can reconnect");
    }

    public void messageArrived(String topic, MqttMessage message) {
        // The messages obtained after subscribe will be executed here
        logger.info("Received message topic: {}", topic);
        logger.info("Received message Qos: {}", message.getQos());
        logger.info("Received message content: {}", new String(message.getPayload()));
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        logger.info("Message Delivery completed to participant-> {}", token.isComplete());
    }
}
