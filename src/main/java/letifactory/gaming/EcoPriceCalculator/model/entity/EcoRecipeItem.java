package letifactory.gaming.EcoPriceCalculator.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GeneratedColumn;

@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class EcoRecipeItem {
    @Id
    @Column(columnDefinition = "nvarchar(101)")
    private String nameId;
    @ManyToOne(optional = false)
    @JoinColumn(name = "recipe", nullable = false)
    private EcoRecipe ecoRecipe;
    @ManyToOne(optional = false)
    @JoinColumn(name = "item", nullable = false)
    private EcoItem ecoItem;
    @NotNull
    @Column(columnDefinition = "decimal(5,0)")
    private Integer quantity;

    @NotNull
    private boolean isOutput;
}
