package com.buulke.buulkestore.service;

import com.buulke.buulkestore.model.Question;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqService {

    @Autowired
    private Environment env;

    @Autowired
    QuestionService questionService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "${rabbitmq.questionQueueName}")
    public void rabbitMqListener(String questionMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Question question = objectMapper.readValue(questionMessage, Question.class);
            questionService.addQuestionToQueue(question);
            System.out.println("questionService.addQuestionToQueue() is called");
        }catch (JsonProcessingException ex){
            System.out.println("questionMessage can not be parsed => " + ex.getMessage());
        }
    }

    public void sendRequestForQuestionGeneration(int quantity){
        String routingKey = env.getProperty("rabbitmq.orderRoutingKey");
        rabbitTemplate.convertAndSend(routingKey, String.valueOf(quantity));
        System.out.println("rabbitTemplate.convertAndSend() is called");
    }
}
