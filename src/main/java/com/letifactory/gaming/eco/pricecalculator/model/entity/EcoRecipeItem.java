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
public class EcoRecipeItem {
    @Id
    @Column(columnDefinition = "nvarchar(101)",nullable = false)
    private String nameId;
    @ManyToOne(optional = false)
    @JoinColumn(name = "recipe", nullable = false)
    private EcoRecipe ecoRecipe;
    @ManyToOne(optional = false)
    @JoinColumn(name = "item", nullable = false)
    private EcoItem ecoItem;
    @Column(columnDefinition = "decimal(5,2)",nullable = false)
    private double quantity;
    @NotNull
    private boolean reducible;
    @NotNull
    private boolean isOutput;

    @Override
    public String toString() {
        return "EcoRecipeItem{" +
                "nameId='" + nameId + '\'' +
                ", ecoRecipe=" + ecoRecipe +
                ", ecoItem=" + ecoItem +
                ", quantity=" + quantity +
                ", reducible=" + reducible +
                ", isOutput=" + isOutput +
                '}';
    }
}
