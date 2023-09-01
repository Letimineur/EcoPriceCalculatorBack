package com.letifactory.gaming.eco.pricecalculator.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class EcoConfig {
    @Id
    @Column(columnDefinition = "nvarchar(50)",nullable = false)
    private String name;
    @Column(columnDefinition = "decimal(18,2)",nullable = false)
    private double value;
}
