package com.letifactory.gaming.eco.pricecalculator.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class EcoItemType {

    @Id
    @Column(columnDefinition = "nvarchar(50)", nullable = false)
    private String type;
    @Column(columnDefinition = "DECIMAL(5,2)", nullable = false)
    private double taxe;
    @Column(columnDefinition = "DECIMAL(5,2)", nullable = false)
    private double defaultProfits;
    @Column(columnDefinition = "DECIMAL(3,0)", nullable = false, unique = true)
    private int typeOrder;

    public EcoItemType(final String type) {
        this.type = type;
        this.taxe = 0.0;
        this.typeOrder = -1;
    }

    public double getTaxeMultiplicator() {
        return 1 + (getTaxe() / 100.0);
    }

    public double getProfitsMultiplicator() {
        return 1 + (getDefaultProfits() / 100.0);
    }

    @Override
    public String toString() {
        return this.getType() + " order: " + getTypeOrder();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof final EcoItemType that)) return false;
        return Double.compare(that.getTaxe(), getTaxe()) == 0 &&
                Double.compare(that.getDefaultProfits(), getDefaultProfits()) == 0 &&
                getTypeOrder() == that.getTypeOrder() && getType().equals(that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType());
    }
}
