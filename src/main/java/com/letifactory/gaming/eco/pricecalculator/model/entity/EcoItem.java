package com.letifactory.gaming.eco.pricecalculator.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.letifactory.gaming.eco.pricecalculator.utils.json.EcoItemDeserializer;
import com.letifactory.gaming.eco.pricecalculator.utils.json.EcoSkillDeserializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@JsonDeserialize(using = EcoItemDeserializer.class)
public class EcoItem {
    @Id
    @Column(columnDefinition = "nvarchar(50)",nullable = false)
    private String name;
    @Column(columnDefinition = "DECIMAL(18,2)",nullable = false)
    private double price;
    @ManyToOne
    @JoinColumn(name = "type",nullable = false)
    private EcoItemType type;

}
