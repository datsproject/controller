package com.datsddos.controller.app.service.integration.cache;

import com.datsddos.controller.app.cache.object.ContractUser;
import com.datsddos.controller.app.cache.repository.ContractUserDao;
import com.datsddos.controller.app.exception.custom.ControllerCustomException;
import com.datsddos.controller.app.service.json.JsonService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RedisClients {
    @Autowired
    private ContractUserDao contractUserDao;

    @Autowired
    private JsonService jsonService;

    @SneakyThrows
    public Map<String, String> getContractUsersFromRedis() {
        List<ContractUser> contractUserListAsLinkedMap = contractUserDao.findAll();

        if (contractUserListAsLinkedMap == null || contractUserListAsLinkedMap.isEmpty()) {
            throw new ControllerCustomException(HttpStatus.EXPECTATION_FAILED, "Users could not be found for attack in cache database");
        }
        Map<String, String> redisClientsMap = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();
        List<ContractUser> contractUserList = mapper.convertValue(contractUserListAsLinkedMap, new TypeReference<List<ContractUser>>() {
        });

        contractUserList.forEach(redisClient -> {
            if (!redisClient.getAddress().isBlank()) {
                redisClientsMap.put(redisClient.getAddress(), redisClient.getDetails());
            }
        });
        return redisClientsMap;
    }
}
