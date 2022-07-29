package com.datsddos.controller.app.cache.repository;

import com.datsddos.controller.app.cache.object.ContractUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContractUserDao {

    public static final String HASH_KEY = "ContractUsers";
    @Autowired
    private RedisTemplate template;

    private HashOperations getHashOperation() {
        return template.opsForHash();
    }

    public ContractUser save(ContractUser contractUser) {
        getHashOperation().put(HASH_KEY, contractUser.getAddress(), contractUser);
        return contractUser;
    }

    public List<ContractUser> findAll() {
        return getHashOperation().values(HASH_KEY);
    }

    public ContractUser findContractUserById(String address) {
        return (ContractUser) getHashOperation().get(HASH_KEY, address);
    }
}
