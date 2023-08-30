package letifactory.gaming.EcoPriceCalculator.model.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import letifactory.gaming.EcoPriceCalculator.model.entity.EcoUserItem;
import org.springframework.stereotype.Repository;

@Repository
public class UserItemRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public EcoUserItem save(EcoUserItem ecoUserItem){

        entityManager.persist(ecoUserItem);
        return ecoUserItem;
    }
}
