package com.electoral.electoral_api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OptionSetDTO {
    private Short id;
    private String name;
    private List<OptionSetItemDTO> items;
}