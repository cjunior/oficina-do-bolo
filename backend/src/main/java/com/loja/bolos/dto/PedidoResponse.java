package com.loja.bolos.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PedidoResponse(
    Long id,
    LocalDate dataPedido,
    String status,
    BigDecimal valorTotal,
    Long clienteId,
    Long produtosId
) {}
