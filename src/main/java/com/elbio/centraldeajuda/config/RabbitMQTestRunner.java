package com.elbio.centraldeajuda.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQTestRunner implements CommandLineRunner {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void run(String... args) throws Exception {
        try {
            rabbitTemplate.convertAndSend("testQueue", "Test Message");
            System.out.println("RabbitMQ connection test successful");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("RabbitMQ connection test failed");
        }
    }
}
