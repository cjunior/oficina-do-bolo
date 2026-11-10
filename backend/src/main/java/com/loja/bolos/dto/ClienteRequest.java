package com.loja.bolos.dto;

import jakarta.validation.constraints.*;

public record ClienteRequest(
    @NotBlank @Size(min = 2, max = 80)
    String nome,
    @NotBlank @Email @Size(max = 120)
    String email,
    @Size(max = 18)
    String telefone
) {}
