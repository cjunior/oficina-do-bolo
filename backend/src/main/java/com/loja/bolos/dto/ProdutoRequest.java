package com.loja.bolos.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProdutoRequest(
    @NotBlank @Size(min = 2, max = 60)
    String nome,
    @NotBlank @Size(min = 5, max = 255)
    String descricao,
    @NotNull
    BigDecimal preco,
    @NotNull
    byte[] imagem,
    @NotNull
    Boolean ativo
) {}
