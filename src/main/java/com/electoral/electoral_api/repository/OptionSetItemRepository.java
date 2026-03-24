package com.electoral.electoral_api.repository;

import com.electoral.electoral_api.entity.OptionSetItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionSetItemRepository extends JpaRepository<OptionSetItem, Short> {

    List<OptionSetItem> findByOptionSet_IdOrderByDisplayOrderAsc(Short optionSetId);
}