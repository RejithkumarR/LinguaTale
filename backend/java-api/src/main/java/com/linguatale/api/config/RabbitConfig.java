package com.linguatale.api.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean Queue generationQueue(@Value("${linguatale.queue.generation}") String name) { return QueueBuilder.durable(name).build(); }
    @Bean RabbitTemplate rabbitTemplate(ConnectionFactory factory) { return new RabbitTemplate(factory); }
}