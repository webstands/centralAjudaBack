package com.elbio.centraldeajuda.controller;

import com.elbio.centraldeajuda.rabbitmq.ConstanteR;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.amqp.core.Message;

@RestController
public class MessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/messages")
    public String getMessage() {
        System.out.println("Checking for messages in the queue...");
        Message message = rabbitTemplate.receive(ConstanteR.QUEUE_NAME);
        if (message != null) {
            String messageBody = new String(message.getBody());
            System.out.println("Message received: " + messageBody);
            return messageBody;
        } else {
            System.out.println("No messages in the queue");
            return "No messages in the queue";
        }
    }


}
