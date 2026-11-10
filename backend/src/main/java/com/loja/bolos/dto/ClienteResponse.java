package com.loja.bolos.dto;

public record ClienteResponse(
    Long id,
    String nome,
    String email,
    String telefone
) {}
