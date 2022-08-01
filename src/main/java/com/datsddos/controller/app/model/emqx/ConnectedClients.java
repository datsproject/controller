package com.datsddos.controller.app.model.emqx;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@lombok.Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
public class ConnectedClients {
    private Meta meta;
    private ArrayList<Data> data;
    private int code;
}
