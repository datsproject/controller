package com.datsddos.controller.app.cache.object;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("ContractUser")
@Builder
public class ContractUser implements Serializable {

    @Id
    private String address;
    private String details;
}
