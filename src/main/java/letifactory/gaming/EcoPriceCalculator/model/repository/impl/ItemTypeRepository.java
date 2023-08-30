package letifactory.gaming.EcoPriceCalculator.model.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import letifactory.gaming.EcoPriceCalculator.model.entity.EcoItemType;
import org.springframework.stereotype.Repository;

@Repository
public class ItemTypeRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public EcoItemType save(EcoItemType ecoItemType){

        entityManager.persist(ecoItemType);
        return ecoItemType;
    }
}
