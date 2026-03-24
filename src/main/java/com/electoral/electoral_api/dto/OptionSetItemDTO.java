package com.electoral.electoral_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionSetItemDTO {
    private Integer id;
    private String text;
    private Integer value;
    private Integer displayOrder;
}
