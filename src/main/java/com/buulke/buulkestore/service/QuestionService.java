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

    @Autowired
    RabbitMqService rabbitMqService;

    private Queue<Question> questions = new ArrayDeque<>();
    private final Integer MAX_SIZE_OF_QUEUE;
    private final Integer MIN_SIZE_OF_QUEUE;

    public QuestionService(){
        MAX_SIZE_OF_QUEUE = Integer.parseInt(env.getProperty("queue.maxSize"));
        MIN_SIZE_OF_QUEUE = Integer.parseInt(env.getProperty("queue.minSize"));
    }

    public void addQuestionToQueue(Question question){
        questions.add(question);
        System.out.println("question added");
    }

    public Question getQuestionFromQueue(){
        Question question = questions.poll();
        this.sendRequestForQuestionGeneration();
        return question;
    }

    public void sendRequestForQuestionGeneration(){
        if(this.questions.size() <= MIN_SIZE_OF_QUEUE) {
            int diff = MAX_SIZE_OF_QUEUE - this.questions.size();
            rabbitMqService.sendRequestForQuestionGeneration(diff);
        }
    }
}
