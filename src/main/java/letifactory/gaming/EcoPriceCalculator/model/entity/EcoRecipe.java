package letifactory.gaming.EcoPriceCalculator.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class EcoRecipe {
    @Id
    @Column(columnDefinition = "nvarchar(50)")
    private String name;
    @Column(columnDefinition = "DECIMAL(18,0)")
    private Integer labor;
    @ManyToOne
    @JoinColumn(name = "workbench")
    private EcoWorkbench ecoWorkbench;
    @ManyToOne
    @JoinColumn(name = "skill")
    private EcoSkill ecoSkill;
}
