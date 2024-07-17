package com.elbio.centraldeajuda.controller;

import com.elbio.centraldeajuda.controller.dto.CallDto;
import com.elbio.centraldeajuda.controller.dto.MensagemDTO;
import com.elbio.centraldeajuda.rabbitmq.ConstanteR;
import com.elbio.centraldeajuda.service.RabbitService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@AllArgsConstructor
public class TestController {

    private RabbitService rabbitService;
    @PutMapping
    public ResponseEntity<CallDto> updateCall(@RequestBody MensagemDTO mensagemDTO) {
        System.out.println(mensagemDTO.getDescricao());
        this.rabbitService.enviaMensagem(ConstanteR.QUEUE_NAME, mensagemDTO);
        return new ResponseEntity(HttpStatus.OK);
    }
}
