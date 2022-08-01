package com.datsddos.controller.app.model.emqx;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.Data;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
public class Meta {
    private int page;
    private boolean hasnext;
    private int limit;
    private int count;
}
