package com.loja.bolos.dto;

import jakarta.validation.constraints.*;

public record UsuarioRequest(
    @NotBlank @Size(min = 2, max = 60)
    String nome,
    @NotBlank @Email @Size(max = 120)
    String email,
    @NotBlank @Size(max = 220)
    String senhaHash,
    @NotBlank @Size(max = 50)
    String role
) {}
