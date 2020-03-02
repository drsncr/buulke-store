package com.buulke.buulkestore.service;

import com.buulke.buulkestore.model.Question;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqListener {

    @RabbitListener(queues = "questionQueue")
    public void handleMessage(@Payload Question question) {
        System.err.println(question);
    }
}
