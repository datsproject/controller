package com.datsddos.controller.app.model.participant;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@lombok.Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
@Builder
public class OperableParticipant {
    private String details;
    private String ipAddress;
}
