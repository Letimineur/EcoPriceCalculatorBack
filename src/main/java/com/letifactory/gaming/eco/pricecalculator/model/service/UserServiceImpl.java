package com.letifactory.gaming.eco.pricecalculator.model.service;

import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoUser;
import com.letifactory.gaming.eco.pricecalculator.model.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public EcoUser save(final EcoUser ecoUser) {
        return userRepository.save(ecoUser);
    }
}
