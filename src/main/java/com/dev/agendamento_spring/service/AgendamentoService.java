package com.dev.agendamento_spring.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.dev.agendamento_spring.dto.AgendamentoCreateRequest;
import com.dev.agendamento_spring.dto.AgendamentoResponse;
import com.dev.agendamento_spring.dto.AgendamentoUpdateRequest;
import com.dev.agendamento_spring.mapper.AgendamentoMapper;
import com.dev.agendamento_spring.model.Agendamento;
import com.dev.agendamento_spring.model.StatusAgendamento;
import com.dev.agendamento_spring.repository.AgendamentoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class AgendamentoService {

    private final AgendamentoRepository repository;

    public AgendamentoService(AgendamentoRepository repository){
        this.repository = repository;
    }

    @Transactional
    public AgendamentoResponse criar(@Valid AgendamentoCreateRequest createRequest){

        validarIntervalo(createRequest.dataInicio(), createRequest.dataFim());
        checarConflito(createRequest.usuario(), createRequest.dataInicio(), createRequest.dataFim(), null);
        
        Agendamento agendamento = AgendamentoMapper.paraEntidade(createRequest);
        agendamento = repository.save(agendamento);

        return AgendamentoMapper.paraResponse(agendamento);
    }

    @Transactional
    public AgendamentoResponse atualizar(Long id, @Valid AgendamentoUpdateRequest updateRequest){

        Agendamento agendamento = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Agendamento nao encontrado"));

        AgendamentoMapper.merge(agendamento, updateRequest);
        validarIntervalo(updateRequest.dataInicio(), updateRequest.dataFim());
        checarConflito(agendamento.getUsuario(), updateRequest.dataInicio(), updateRequest.dataFim(), agendamento.getId());

        agendamento = repository.save(agendamento);

        return AgendamentoMapper.paraResponse(agendamento);
    }

    @Transactional
    public AgendamentoResponse cancelar(Long id){
        Agendamento agendamento = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Agendamento nao encontrado"));

        agendamento.setStatus(StatusAgendamento.CANCELADO);
        agendamento = repository.save(agendamento);

        return AgendamentoMapper.paraResponse(agendamento);
    }

    @Transactional
    public AgendamentoResponse concluir(Long id){
        Agendamento agendamento = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Agendamento nao encontrado"));

        agendamento.setStatus(StatusAgendamento.CONCLUIDO);
        agendamento = repository.save(agendamento);

        return AgendamentoMapper.paraResponse(agendamento);
    }

    public AgendamentoResponse buscarPorId(Long id){
        Agendamento agendamento = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Agendamento nao encontrado"));

        return AgendamentoMapper.paraResponse(agendamento);
    }

    private void validarIntervalo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        
        if(dataInicio == null || dataFim == null || !dataInicio.isBefore(dataFim)){
            throw new IllegalArgumentException("Intervalo invalido: dataInicio deve ser anterior a dataFim...");
        }
    }

    private void checarConflito(String usuario, LocalDateTime dataInicio,
            LocalDateTime dataFim, Long id) {
        
        if(repository.existeConflito(usuario, dataInicio, dataFim, id)){
            throw new IllegalArgumentException("Conflito: ja existe agendamento para o periodo escolhido...");
        }
    }
}
