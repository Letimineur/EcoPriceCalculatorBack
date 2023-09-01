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
@NamedQueries({
        @NamedQuery(name = EcoRecipe.COMPLETE_RECIPE_BY_NAME, query = EcoRecipe.COMPLETE_RECIPE_BY_NAME_JPQL)
})
public class EcoRecipe {

    public static final String COMPLETE_RECIPE_BY_NAME = "EcoRecipe.getCompleteRecipeByName";
    public static final String COMPLETE_RECIPE_BY_NAME_JPQL =
            "Select new com.letifactory.gaming.eco.pricecalculator.model.dto.CompleteRecipe(r, ri) from EcoRecipe r, " +
                    "EcoRecipeItem ri" +
                    " where r.name = :recipe AND r.name = ri.ecoRecipe.name GROUP BY r";
    @Id
    @Column(columnDefinition = "nvarchar(50)",nullable = false)
    private String name;
    @Column(columnDefinition = "DECIMAL(18,0)",nullable = false)
    private Integer labor;
    @ManyToOne
    @JoinColumn(name = "workbench",nullable = false)
    private EcoWorkbench ecoWorkbench;
    @ManyToOne
    @JoinColumn(name = "skill",nullable = false)
    private EcoSkill ecoSkill;
}
