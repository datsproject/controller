package com.datsddos.controller.app.service.integration.emqx;

import com.datsddos.controller.app.exception.custom.ControllerCustomException;
import com.datsddos.controller.app.model.emqx.ConnectedClients;
import com.datsddos.controller.app.service.json.JsonService;
import com.datsddos.controller.app.service.rest.IRestService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MessageBrokerClients {

    @Value("${emqx.connected_clients_url}")
    private String connectedClientsUrl;

    @Value("${emqx.api_basic_token}")
    private String apiBasicToken;

    @Autowired
    protected IRestService restService;

    @Autowired
    protected JsonService jsonService;

    @SneakyThrows
    public Map<String, String> getConnectedClientsFromMessageBroker() {
        Map<String, String> headersMap = new HashMap<>();
        headersMap.put("Authorization", "Basic " + apiBasicToken);
        ResponseEntity<String> response = restService.getUrlWithHeaders(connectedClientsUrl, headersMap);
        if (response.getStatusCode() == HttpStatus.OK && response.hasBody()) {
            return getClientIdAndIpAddressFromBroker(response.getBody());
        }

        throw new ControllerCustomException(HttpStatus.NOT_FOUND, "Connected clients information could not be taken from message broker");
    }

    @SneakyThrows
    public Map<String, String> getClientIdAndIpAddressFromBroker(String emqxConnectedClientsBody) {
        if (emqxConnectedClientsBody == null || emqxConnectedClientsBody.isBlank()) {
            throw new ControllerCustomException(HttpStatus.NOT_FOUND, "Connected clients information could not be taken from message broker");
        }

        ConnectedClients connectedClients = jsonService.toObject(emqxConnectedClientsBody, ConnectedClients.class);

        if (connectedClients.getData() == null || connectedClients.getData().isEmpty()) {
            throw new ControllerCustomException(HttpStatus.NOT_FOUND, "There is no connected clients found in data");
        }
        Map<String, String> clientAndIpMap = new HashMap<>();
        connectedClients.getData().stream().forEach(client -> {
            if (client.isConnected()) {
                clientAndIpMap.put(client.getClientid(), client.getIp_address());
            }
        });

        return clientAndIpMap;
    }
}
