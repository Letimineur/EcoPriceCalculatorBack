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

public class EcoRecipe {

    @Id
    @Column(columnDefinition = "nvarchar(50)", nullable = false)
    private String name;
    @Column(columnDefinition = "DECIMAL(18,0)", nullable = false)
    private Integer labor;
    @ManyToOne
    @JoinColumn(name = "workbench", nullable = false)
    private EcoWorkbench ecoWorkbench;
    @ManyToOne
    @JoinColumn(name = "skill", nullable = false)
    private EcoSkill ecoSkill;
}
