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
public class EcoItemType {

    @Id
    @Column(columnDefinition = "nvarchar(50)")
    private String type;
    @NotNull
    @Column(columnDefinition = "DECIMAL(5,2)")
    private double taxe;
}
