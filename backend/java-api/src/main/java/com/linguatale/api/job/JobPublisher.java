package com.linguatale.api.job;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JobPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final String queue;
    public JobPublisher(RabbitTemplate rabbitTemplate, @Value("${linguatale.queue.generation}") String queue) { this.rabbitTemplate = rabbitTemplate; this.queue = queue; }
    public void publish(String jobId) { rabbitTemplate.convertAndSend(queue, jobId); }
}