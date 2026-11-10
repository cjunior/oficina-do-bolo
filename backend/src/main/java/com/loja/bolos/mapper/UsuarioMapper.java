package com.loja.bolos.mapper;

import com.loja.bolos.dto.UsuarioRequest;
import com.loja.bolos.dto.UsuarioResponse;
import com.loja.bolos.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequest request) {
        Usuario entity = new Usuario();
        entity.setNome(request.nome());
        entity.setEmail(request.email());
        entity.setSenhaHash(request.senhaHash());
        entity.setRole(request.role());
        return entity;
    }

    public UsuarioResponse toResponse(Usuario entity) {
        return new UsuarioResponse(
            entity.getId(),
            entity.getNome(),
            entity.getEmail(),
            entity.getSenhaHash(),
            entity.getRole()
        );
    }

    public void updateEntity(Usuario entity, UsuarioRequest request) {
        entity.setNome(request.nome());
        entity.setEmail(request.email());
        entity.setSenhaHash(request.senhaHash());
        entity.setRole(request.role());
    }
}
