package com.letifactory.gaming.eco.pricecalculator.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.letifactory.gaming.eco.pricecalculator.utils.json.EcoSkillDeserializer;
import com.letifactory.gaming.eco.pricecalculator.utils.json.EcoWorkbenchDeserializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@JsonDeserialize(using = EcoWorkbenchDeserializer.class)
public class EcoWorkbench {
    @Id
    @Column(columnDefinition = "nvarchar(50)",nullable = false)
    private String name;

}
