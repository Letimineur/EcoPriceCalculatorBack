package com.letifactory.gaming.eco.pricecalculator.model.repository;

import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<EcoItem,String> {

}
