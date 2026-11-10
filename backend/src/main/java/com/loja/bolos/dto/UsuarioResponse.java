package com.loja.bolos.dto;

public record UsuarioResponse(
    Long id,
    String nome,
    String email,
    String senhaHash,
    String role
) {}
