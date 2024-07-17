package com.elbio.centraldeajuda.service;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RabbitService {

    private RabbitTemplate rabbitTemplate;


    public void enviaMensagem(String nomeFila, Object mensagem){
       rabbitTemplate.convertAndSend(nomeFila, mensagem);
    }
}
