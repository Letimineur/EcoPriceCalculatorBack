package letifactory.gaming.EcoPriceCalculator.model.repository;

import letifactory.gaming.EcoPriceCalculator.model.entity.EcoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<EcoUser,String> {
}
