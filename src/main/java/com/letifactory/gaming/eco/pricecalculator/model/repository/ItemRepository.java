package com.letifactory.gaming.eco.pricecalculator.model.repository;

import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<EcoItem,String> {

    @Query(value = "Select it from EcoItem it where it.price = 0 AND it.type.type = :type")
    List<EcoItem> getAllNotPricedItemForType(@Param(value = "type")String type);
    @Query(value = "Select it from EcoItem it where it.type.type = :type")
    List<EcoItem> getAllItemForType(@Param(value = "type")String type);

}
