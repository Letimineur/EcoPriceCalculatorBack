package com.letifactory.gaming.eco.pricecalculator.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.letifactory.gaming.eco.pricecalculator.utils.json.EcoSkillDeserializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@JsonDeserialize(using = EcoSkillDeserializer.class)
public class EcoSkill {
    @Id
    @Column(columnDefinition = "nvarchar(50)",nullable = false)
    private String name;
}
