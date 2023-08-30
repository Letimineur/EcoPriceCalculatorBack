package letifactory.gaming.EcoPriceCalculator.model.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import letifactory.gaming.EcoPriceCalculator.model.entity.EcoSkill;
import org.springframework.stereotype.Repository;

@Repository
public class SkillRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public EcoSkill save(EcoSkill ecoSkill){

        entityManager.persist(ecoSkill);
        return ecoSkill;
    }
}
