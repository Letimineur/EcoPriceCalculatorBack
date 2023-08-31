package com.letifactory.gaming.eco.pricecalculator.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class EcoWorkbench {
    @Id
    @Column(columnDefinition = "nvarchar(50)")
    private String name;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "skill")
    private EcoSkill ecoSkill;

}
