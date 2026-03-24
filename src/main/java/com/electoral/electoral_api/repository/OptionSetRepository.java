package com.electoral.electoral_api.repository;

import com.electoral.electoral_api.entity.OptionSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionSetRepository extends JpaRepository<OptionSet, Short> {
}