package com.dev.agendamento_spring.dto;

import java.time.LocalDateTime;

import com.dev.agendamento_spring.model.StatusAgendamento;

public record AgendamentoResponse(

    Long id,
    String titulo,
    String descricao,
    LocalDateTime dataInicio,
    LocalDateTime dataFim,
    StatusAgendamento status,
    String usuario,
    LocalDateTime criadoEm,
    LocalDateTime atualizadoEm
) {

}
