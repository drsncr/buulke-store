package com.buulke.buulkestore.service;

import com.buulke.buulkestore.model.Question;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Queue;

@Service
public class QuestionService {

    @Autowired
    private Environment env;

    private Queue<Question> questions = new ArrayDeque<>();
    private final Integer MAX_SIZE_OF_QUEUE;
    private final Integer MIN_SIZE_OF_QUEUE;

    public QuestionService(){
        MAX_SIZE_OF_QUEUE = Integer.parseInt(env.getProperty("queue.maxSize"));
        MIN_SIZE_OF_QUEUE = Integer.parseInt(env.getProperty("queue.minSize"));
    }

    @RabbitListener(queues = "questionQueue1")
    public void rabbitMqListener(String questionMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Question question = objectMapper.readValue(questionMessage, Question.class);
            questions.add(question);
        }catch (JsonProcessingException ex){
            System.out.println("questionMessage can not be parsed => " + ex.getMessage());
        }
    }

    public Question getQuestionFromQueue(){
        Question question = questions.poll();
        return question;
    }
}
