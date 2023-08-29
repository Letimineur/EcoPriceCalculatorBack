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
public class Item {
    @Id
    @Column(columnDefinition = "nvarchar(50)")
    private String name;
    @NotNull
    @Column(columnDefinition = "DECIMAL(18,2)")
    private double price;
    @ManyToOne
    @JoinColumn(name = "type")
    private ItemType type;
    @ManyToOne
    @JoinColumn(name = "source")
    private Skill source;

}
