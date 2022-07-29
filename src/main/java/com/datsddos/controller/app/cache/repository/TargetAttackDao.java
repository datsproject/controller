package com.datsddos.controller.app.cache.repository;

import com.datsddos.controller.app.cache.object.TargetAttackStart;
import com.datsddos.controller.app.cache.object.TargetAttackStop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TargetAttackDao {

    public static final String HASH_KEY = "TargetAttack";
    @Autowired
    private RedisTemplate template;

    private HashOperations getHashOperation() {
        return template.opsForHash();
    }

    public TargetAttackStart saveTargetAttackStart(TargetAttackStart targetAttackStart, String operationType) {
        getHashOperation().put(HASH_KEY + "-" + operationType, targetAttackStart.getAttackId(), targetAttackStart);
        return targetAttackStart;
    }

    public TargetAttackStop saveTargetAttackStop(TargetAttackStop targetAttackStop, String operationType) {
        getHashOperation().put(HASH_KEY + "-" + operationType, targetAttackStop.getAttackId(), targetAttackStop);
        return targetAttackStop;
    }

    public List<TargetAttackStart> findAll(String operationType) {
        return getHashOperation().values(HASH_KEY + "-" + operationType);
    }

    public TargetAttackStart findTargetAttackById(String attackId, String operationType) {
        return (TargetAttackStart) getHashOperation().get(HASH_KEY + "-" + operationType, attackId);
    }

    public String deleteTargetAttackById(String attackId, String operationType) {
        getHashOperation().delete(HASH_KEY + "-" + operationType, attackId);
        return "Target Attack " + attackId + " has been deleted from cache";
    }

    public void deleteAll(String operationType) {
        template.delete(HASH_KEY + "-" + operationType);
    }
}
