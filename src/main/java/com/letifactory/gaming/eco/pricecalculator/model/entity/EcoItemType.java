package com.letifactory.gaming.eco.pricecalculator.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class EcoItemType {

    @Id
    @Column(columnDefinition = "nvarchar(50)",nullable = false)
    private String type;
    @Column(columnDefinition = "DECIMAL(5,2)",nullable = false)
    private double taxe;
}
