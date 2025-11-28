package com.dev.agendamento_spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.agendamento_spring.dto.AgendamentoCreateRequest;
import com.dev.agendamento_spring.dto.AgendamentoResponse;
import com.dev.agendamento_spring.dto.AgendamentoUpdateRequest;
import com.dev.agendamento_spring.service.AgendamentoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/agendamento")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;
    
    public AgendamentoController(AgendamentoService agendamentoService){
        this.agendamentoService = agendamentoService;
    }

    @PostMapping
    public AgendamentoResponse criar(@Valid @RequestBody AgendamentoCreateRequest createRequest){
        return agendamentoService.criar(createRequest);
    }

    @PutMapping("/{id}")
    public AgendamentoResponse atualizar(@PathVariable Long id, @Valid @RequestBody AgendamentoUpdateRequest updateRequest){
        return agendamentoService.atualizar(id, updateRequest);
    }

    @PutMapping("/{id}/cancelar")
    public AgendamentoResponse cancelar(@PathVariable Long id){
        return agendamentoService.cancelar(id);
    }

    @PutMapping("/{id}/concluir")
    public AgendamentoResponse concluir(@PathVariable Long id){
        return agendamentoService.concluir(id);
    }

    @GetMapping("/{id}")
    public AgendamentoResponse buscarPorId(@PathVariable Long id){
        return agendamentoService.buscarPorId(id);
    }
}
