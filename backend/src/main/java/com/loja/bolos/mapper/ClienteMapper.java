package com.loja.bolos.mapper;

import com.loja.bolos.dto.ClienteRequest;
import com.loja.bolos.dto.ClienteResponse;
import com.loja.bolos.entity.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteRequest request) {
        Cliente entity = new Cliente();
        entity.setNome(request.nome());
        entity.setEmail(request.email());
        if (request.telefone() != null) {
            entity.setTelefone(request.telefone());
        }
        return entity;
    }

    public ClienteResponse toResponse(Cliente entity) {
        return new ClienteResponse(
            entity.getId(),
            entity.getNome(),
            entity.getEmail(),
            entity.getTelefone()
        );
    }

    public void updateEntity(Cliente entity, ClienteRequest request) {
        entity.setNome(request.nome());
        entity.setEmail(request.email());
        if (request.telefone() != null) {
            entity.setTelefone(request.telefone());
        }
    }
}
