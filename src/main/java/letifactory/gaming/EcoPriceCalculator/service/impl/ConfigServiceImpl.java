package letifactory.gaming.EcoPriceCalculator.service.impl;

import jakarta.transaction.Transactional;
import letifactory.gaming.EcoPriceCalculator.model.entity.EcoConfig;
import letifactory.gaming.EcoPriceCalculator.model.repository.impl.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl {

    @Autowired
    ConfigRepository configRepository;

    @Transactional
    public EcoConfig save(EcoConfig ecoConfig){
       return configRepository.save(ecoConfig);
    }
}
