package letifactory.gaming.EcoPriceCalculator.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Skill {
    @Id
    @Column(columnDefinition = "nvarchar(50)")
    private String name;
}
