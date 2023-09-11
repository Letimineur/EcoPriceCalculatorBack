package com.letifactory.gaming.eco.pricecalculator.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.letifactory.gaming.eco.pricecalculator.utils.json.EcoItemDeserializer;
import com.letifactory.gaming.eco.pricecalculator.utils.json.EcoSkillDeserializer;
import jakarta.persistence.*;
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
//@JsonDeserialize(using = EcoItemDeserializer.class)
public class EcoItem {
    @Id
    @Column(columnDefinition = "nvarchar(50)", nullable = false)
    private String name;
    @Column(columnDefinition = "DECIMAL(18,2)", nullable = false)
    private double price;
    @ManyToOne
    @JoinColumn(name = "type", nullable = false)
    private EcoItemType type;
    @Column(columnDefinition = "nvarchar(50)")
    private String imageFile;
    @Column(columnDefinition = "DECIMAL(3,0)")
    @JsonProperty(value = "xPos")
    private int xPos;
    @Column(columnDefinition = "DECIMAL(3,0)")
    @JsonProperty(value = "yPos")
    private int yPos;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ItemName: ").append(getName())
                .append(" type: ").append(getType().getType()).append(" ").append(getType().getTypeOrder())
                .append(" price: ").append(getPrice());
        return builder.toString();
    }

}
