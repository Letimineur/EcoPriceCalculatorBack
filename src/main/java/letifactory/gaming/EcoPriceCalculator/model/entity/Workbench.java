package letifactory.gaming.EcoPriceCalculator.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Workbench {
    @Id
    @Column(columnDefinition = "nvarchar(50)")
    private String name;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "skill")
    private Skill skill;

}
