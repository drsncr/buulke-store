package com.buulke.buulkestore.service;

import com.buulke.buulkestore.model.Question;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqService {

    @Autowired
    QuestionService questionService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "questionQueue1")
    public void rabbitMqListener(String questionMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Question question = objectMapper.readValue(questionMessage, Question.class);
            questionService.addQuestionToQueue(question);
        }catch (JsonProcessingException ex){
            System.out.println("questionMessage can not be parsed => " + ex.getMessage());
        }
    }

    public void sendRequestForQuestionGeneration(int quantity){
        rabbitTemplate.convertAndSend(String.valueOf(quantity));
    }
}
