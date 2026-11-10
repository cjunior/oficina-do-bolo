package com.loja.bolos.dto;

import java.math.BigDecimal;

public record ProdutoResponse(
    Long id,
    String nome,
    String descricao,
    BigDecimal preco,
    byte[] imagem,
    Boolean ativo
) {}
