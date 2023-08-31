package com.letifactory.gaming.eco.pricecalculator;

import com.letifactory.gaming.eco.pricecalculator.service.impl.InitDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class EcoPriceCalculatorApplication {

//	@Autowired
//	private InitDatabaseService initDatabaseService;
	public static void main(String[] args) {
		SpringApplication.run(EcoPriceCalculatorApplication.class, args);
	}

//	@EventListener(ApplicationReadyEvent.class)
//	private void initDatabase(){
//		this.initDatabaseService.initDatabase();
//	}

}
