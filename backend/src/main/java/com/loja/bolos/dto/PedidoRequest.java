package com.loja.bolos.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record PedidoRequest(
    @NotNull
    LocalDate dataPedido,
    @NotBlank @Size(max = 30)
    String status,
    @NotNull
    BigDecimal valorTotal,
    @NotNull
    Long clienteId,
    @NotNull
    Long produtosId
) {}
