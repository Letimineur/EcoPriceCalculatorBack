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
public class UserItemType {

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "type", nullable = false)
    private ItemType type;
    @NotNull
    @Column(columnDefinition = "DECIMAL(5,2)")
    private double profit;
}
