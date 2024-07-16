package com.elbio.centraldeajuda.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class MensagemDTO {

    private UUID userId;
    private Long idCall;
    private String descricao;
    private Boolean newCall;
    private Boolean ratingCall;
    private Boolean closeCall;
}
