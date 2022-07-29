package com.datsddos.controller.app.cache.object;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("TargetAttack-start")
@Builder
@EqualsAndHashCode
public class TargetAttackStart implements Serializable {

    @Id
    private String attackId;
    private String details;
}
