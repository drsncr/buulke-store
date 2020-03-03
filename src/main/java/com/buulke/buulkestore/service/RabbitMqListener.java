package com.buulke.buulkestore.service;

import com.buulke.buulkestore.model.Question;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqListener {

    @RabbitListener(queues = "questionQueue1")
    public void handleMessage(@Payload String questionMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        Queu
        try {
            Question question = objectMapper.readValue(questionMessage, Question.class);
            System.out.println(question);
        }catch (JsonProcessingException ex){
            System.out.println("questionMessage can not be parsed => " + ex.getMessage());
        }
    }
}
