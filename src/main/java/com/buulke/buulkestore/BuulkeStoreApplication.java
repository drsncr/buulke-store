package com.buulke.buulkestore;

import com.buulke.buulkestore.service.QuestionService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableRabbit
public class BuulkeStoreApplication {

	@Autowired
	QuestionService questionService;

	public static void main(String[] args) {
		SpringApplication.run(BuulkeStoreApplication.class, args);
	}

	@PostConstruct
	public void sendRequestForQuestionGenerationIfNeeded(){
		questionService.sendRequestForQuestionGeneration();
	}

}
