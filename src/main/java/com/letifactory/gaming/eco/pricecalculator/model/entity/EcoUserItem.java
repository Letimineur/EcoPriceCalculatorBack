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

public class EcoUserItem {

    @ManyToOne(optional = false)
    @JoinColumn(name = "name", nullable = false)
    @Id
    private EcoItem name;

    @NotNull
    @Column(columnDefinition = "DECIMAL(18,2)",nullable = false)
    private double price;
}
