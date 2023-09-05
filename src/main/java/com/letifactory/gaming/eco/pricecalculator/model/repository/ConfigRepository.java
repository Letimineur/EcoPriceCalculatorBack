package com.letifactory.gaming.eco.pricecalculator.model.repository;

import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends JpaRepository<EcoConfig, String> {

}
