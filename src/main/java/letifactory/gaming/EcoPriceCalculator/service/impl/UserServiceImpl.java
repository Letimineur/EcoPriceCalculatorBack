package letifactory.gaming.EcoPriceCalculator.service.impl;

import jakarta.transaction.Transactional;
import letifactory.gaming.EcoPriceCalculator.model.entity.EcoUser;
import letifactory.gaming.EcoPriceCalculator.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public EcoUser save(EcoUser ecoUser){
        return userRepository.save(ecoUser);
    }
}
