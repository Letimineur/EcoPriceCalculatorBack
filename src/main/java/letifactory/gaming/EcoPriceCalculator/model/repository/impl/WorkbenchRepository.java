package letifactory.gaming.EcoPriceCalculator.model.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import letifactory.gaming.EcoPriceCalculator.model.entity.EcoWorkbench;
import org.springframework.stereotype.Repository;

@Repository
public class WorkbenchRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public EcoWorkbench save(EcoWorkbench ecoWorkbench){

        entityManager.persist(ecoWorkbench);
        return ecoWorkbench;
    }
}
