package com.elbio.centraldeajuda.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class MensagemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String descricao;
}
