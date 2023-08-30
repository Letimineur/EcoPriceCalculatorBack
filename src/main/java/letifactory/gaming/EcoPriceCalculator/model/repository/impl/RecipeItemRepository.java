package letifactory.gaming.EcoPriceCalculator.model.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import letifactory.gaming.EcoPriceCalculator.model.entity.EcoRecipeItem;
import org.springframework.stereotype.Repository;

@Repository
public class RecipeItemRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public EcoRecipeItem save(EcoRecipeItem ecoRecipeItem){

        entityManager.persist(ecoRecipeItem);
        return ecoRecipeItem;
    }
}
