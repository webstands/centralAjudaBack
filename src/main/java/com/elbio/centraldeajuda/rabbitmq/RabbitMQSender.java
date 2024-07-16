package com.elbio.centraldeajuda.rabbitmq;

import com.elbio.centraldeajuda.controller.dto.MensagemDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(MensagemDTO message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, message);
    }
}
