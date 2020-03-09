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

import javax.annotation.PostConstruct;
import java.util.ArrayDeque;
import java.util.Queue;

@Service
public class QuestionService {

    @Autowired
    private Environment env;

    @Autowired
    RabbitMqService rabbitMqService;

    private Queue<Question> questions = new ArrayDeque<>();
    private Integer minSizeOfQueue;
    private Integer maxSizeOfQueue;

    @PostConstruct
    public void setMinMaxLimit(){
        minSizeOfQueue = Integer.parseInt(env.getProperty("queue.minSize"));
        maxSizeOfQueue = Integer.parseInt(env.getProperty("queue.maxSize"));
    }

    public void addQuestionToQueue(Question question){
        questions.add(question);
        System.out.println("question added");
    }

    public Question getQuestionFromQueue(){
        Question question = questions.poll();
        this.checkAndSendRequestForQuestionGeneration();
        return question;
    }

    public void checkAndSendRequestForQuestionGeneration(){
        if(this.questions.size() <= minSizeOfQueue) {
            int diff = maxSizeOfQueue - this.questions.size();
            rabbitMqService.sendRequestForQuestionGeneration(diff);
        }
    }
}
