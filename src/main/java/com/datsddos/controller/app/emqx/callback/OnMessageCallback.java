package com.datsddos.controller.app.emqx.callback;

import com.datsddos.controller.app.emqx.operator.OnAttackMessageOperator;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
public class OnMessageCallback implements MqttCallback {

    private static final Logger logger = LoggerFactory.getLogger(OnMessageCallback.class);

    public OnAttackMessageOperator onAttackMessageOperator;

    @Autowired
    public OnMessageCallback(OnAttackMessageOperator onAttackMessageOperator) {
        this.onAttackMessageOperator = onAttackMessageOperator;
    }

    public void connectionLost(Throwable cause) {
        // After the connection is lost, it usually reconnects here
        logger.error("disconnect，you can reconnect");
    }

    public void messageArrived(String topic, MqttMessage message) {
        // The messages obtained after subscribe will be executed here
        logger.info("Received message topic: {}", topic);
        logger.info("Received message Qos: {}", message.getQos());
        logger.info("Received message content: {}", new String(message.getPayload()));
        onAttackMessageOperator.makeAttackMessageArrivedOperations(topic);

    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        logger.info("Message Delivery completed -> {}", token.isComplete());
    }
}
