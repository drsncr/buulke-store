package com.buulke.buulkestore.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RabbitMqConfiguration {

    @Autowired
    private Environment env;

    @Bean
    DirectExchange exchange() {
        String directExchangeName = env.getProperty("rabbitmq.directExchangeName");
        return new DirectExchange(directExchangeName);
    }

    @Bean
    Queue queue() {
        String queueName = env.getProperty("rabbitmq.queueName");
        return new Queue(queueName, false);
    }

    @Bean
    Binding binding(DirectExchange exchange, Queue queue) {
        String routingKey = env.getProperty("rabbitmq.routingKey");
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }
}