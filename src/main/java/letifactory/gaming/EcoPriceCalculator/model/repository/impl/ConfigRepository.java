package letifactory.gaming.EcoPriceCalculator.model.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import letifactory.gaming.EcoPriceCalculator.model.entity.EcoConfig;
import org.springframework.stereotype.Repository;

@Repository
public class ConfigRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public EcoConfig save(EcoConfig cfg){

        entityManager.persist(cfg);
        return cfg;
    }
}
