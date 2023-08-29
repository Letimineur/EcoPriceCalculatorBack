package letifactory.gaming.EcoPriceCalculator.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Config {
    @Id
    @Column(columnDefinition = "nvarchar(50)")
    private String name;
    @NotNull
    @Column(columnDefinition = "decimal(18,2)")
    private double value;
}
