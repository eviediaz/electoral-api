package com.electoral.electoral_api.service;

import com.electoral.electoral_api.dto.OptionSetDTO;
import com.electoral.electoral_api.dto.OptionSetItemDTO;
import com.electoral.electoral_api.entity.OptionSet;
import com.electoral.electoral_api.repository.OptionSetRepository;
import com.electoral.electoral_api.repository.OptionSetItemRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OptionSetService {

    private final OptionSetRepository optionSetRepository;
    private final OptionSetItemRepository optionSetItemRepository;

    public OptionSetService(OptionSetRepository optionSetRepository,
                            OptionSetItemRepository optionSetItemRepository) {
        this.optionSetRepository = optionSetRepository;
        this.optionSetItemRepository = optionSetItemRepository;
    }

    public List<OptionSetDTO> getAllOptionSets() {
        return optionSetRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private OptionSetDTO toDTO(OptionSet optionSet) {
        OptionSetDTO dto = new OptionSetDTO();
        dto.setId(optionSet.getId());
        dto.setName(optionSet.getName());
        dto.setItems(
                optionSetItemRepository
                        .findByOptionSet_IdOrderByDisplayOrderAsc(optionSet.getId())
                        .stream()
                        .map(item -> {
                            OptionSetItemDTO itemDTO = new OptionSetItemDTO();
                            itemDTO.setId((int) item.getId());
                            itemDTO.setText(item.getText());
                            itemDTO.setValue((int) item.getValue());
                            itemDTO.setDisplayOrder((int) item.getDisplayOrder());
                            return itemDTO;
                        })
                        .collect(Collectors.toList())
        );
        return dto;
    }
}