package com.letifactory.gaming.eco.pricecalculator.model.repository;

import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoItemType;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoUserItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserItemTypeRepository extends JpaRepository<EcoUserItemType, EcoItemType> {
}
