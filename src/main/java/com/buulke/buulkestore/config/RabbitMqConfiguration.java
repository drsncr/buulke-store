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
    TopicExchange exchange() {
        String topicExchangeName = env.getProperty("rabbitmq.topicExchangeName");
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Queue orderQueue() {
        String queueName = env.getProperty("rabbitmq.orderQueueName");
        return new Queue(queueName, false);
    }

    @Bean
    Binding binding(TopicExchange exchange) {
        String routingKey = env.getProperty("rabbitmq.orderRoutingKey");
        return BindingBuilder.bind(orderQueue()).to(exchange).with(routingKey);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }
}
