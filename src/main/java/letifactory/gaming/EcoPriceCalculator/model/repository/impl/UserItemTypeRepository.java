package letifactory.gaming.EcoPriceCalculator.model.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import letifactory.gaming.EcoPriceCalculator.model.entity.EcoUserItemType;
import org.springframework.stereotype.Repository;

@Repository
public class UserItemTypeRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public EcoUserItemType save(EcoUserItemType ecoUserItemType){

        entityManager.persist(ecoUserItemType);
        return ecoUserItemType;
    }
}
