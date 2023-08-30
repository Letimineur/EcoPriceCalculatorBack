package letifactory.gaming.EcoPriceCalculator.model.repository;

import letifactory.gaming.EcoPriceCalculator.model.entity.EcoRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<EcoRecipe, String> {
}
