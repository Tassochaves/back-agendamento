package com.dev.agendamento_spring.mapper;

import java.time.LocalDateTime;

import com.dev.agendamento_spring.dto.AgendamentoCreateRequest;
import com.dev.agendamento_spring.dto.AgendamentoResponse;
import com.dev.agendamento_spring.dto.AgendamentoUpdateRequest;
import com.dev.agendamento_spring.model.Agendamento;
import com.dev.agendamento_spring.model.StatusAgendamento;

public class AgendamentoMapper {
    
    public static Agendamento paraEntidade(AgendamentoCreateRequest requestCreate){
        return Agendamento.builder()
            .titulo(requestCreate.titulo())
            .descricao(requestCreate.descricao())
            .dataInicio(requestCreate.dataInicio())
            .dataFim(requestCreate.dataFim())
            .usuario(requestCreate.usuario())
            .status(StatusAgendamento.AGENDADO)
            .criadoEm(LocalDateTime.now())
            .atualizadoEm(LocalDateTime.now())
            .build();
    }

    public static AgendamentoResponse paraResponse(Agendamento agendamento){
        return new AgendamentoResponse(
            agendamento.getId(), 
            agendamento.getTitulo(), 
            agendamento.getDescricao(),
            agendamento.getDataInicio(),
            agendamento.getDataFim(), 
            agendamento.getStatus(), 
            agendamento.getUsuario(), 
            agendamento.getCriadoEm(), 
            agendamento.getAtualizadoEm());
    }

    public static void merge(Agendamento agendamento, AgendamentoUpdateRequest requestUpdate){

        if(requestUpdate.titulo() != null){
            agendamento.setDescricao(requestUpdate.descricao());
        }
        if(requestUpdate.descricao() != null){
            agendamento.setTitulo(requestUpdate.titulo());
        }
        if(requestUpdate.dataInicio() != null){
            agendamento.setDataFim(requestUpdate.dataInicio());
        }
        if(requestUpdate.dataFim() != null){
            agendamento.setDataFim(requestUpdate.dataFim());
        }
    }
}
