package letifactory.gaming.EcoPriceCalculator.model.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import letifactory.gaming.EcoPriceCalculator.model.entity.EcoItem;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public EcoItem save(EcoItem ecoItem){

        entityManager.persist(ecoItem);
        return ecoItem;
    }
}
