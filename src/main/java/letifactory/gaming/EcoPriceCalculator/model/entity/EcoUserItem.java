package letifactory.gaming.EcoPriceCalculator.model.entity;

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

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "name", nullable = false)
    private EcoItem name;
    @NotNull
    @Column(columnDefinition = "DECIMAL(18,2)")
    private double price;
}
