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
public class RecipeItem {
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "recipe", nullable = false)
    private Recipe recipe;
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "item", nullable = false)
    private Item item;
    @NotNull
    @Column(columnDefinition = "decimal(5,0)")
    private Integer quantity;
}
