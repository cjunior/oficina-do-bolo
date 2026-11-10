package com.loja.bolos.controller;

import com.loja.bolos.dto.PedidoRequest;
import com.loja.bolos.dto.PedidoResponse;
import com.loja.bolos.exception.ResourceNotFoundException;
import com.loja.bolos.service.PedidoService;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PedidoController.class)
@AutoConfigureMockMvc(addFilters = false)
class PedidoControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockitoBean private PedidoService pedidoService;

    @Test
    void create_returns201() throws Exception {
        PedidoRequest request = new PedidoRequest(java.time.LocalDate.now(), "sample", java.math.BigDecimal.ONE, 1L, 1L);
        PedidoResponse response = new PedidoResponse(1L, java.time.LocalDate.now(), "sample", java.math.BigDecimal.ONE, 1L, 1L);

        when(pedidoService.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void findById_whenMissing_returns404() throws Exception {
        when(pedidoService.findById(eq(1L)))
            .thenThrow(new ResourceNotFoundException("not found"));

        mockMvc.perform(get("/api/pedidos/" + 1L))
            .andExpect(status().isNotFound());
    }

    @Test
    void findAll_returns200() throws Exception {
        PedidoResponse response = new PedidoResponse(1L, java.time.LocalDate.now(), "sample", java.math.BigDecimal.ONE, 1L, 1L);
        when(pedidoService.findAll()).thenReturn(java.util.List.of(response));

        mockMvc.perform(get("/api/pedidos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    void findById_whenPresent_returns200() throws Exception {
        PedidoResponse response = new PedidoResponse(1L, java.time.LocalDate.now(), "sample", java.math.BigDecimal.ONE, 1L, 1L);
        when(pedidoService.findById(eq(1L))).thenReturn(response);

        mockMvc.perform(get("/api/pedidos/" + 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void update_returns200() throws Exception {
        PedidoRequest request = new PedidoRequest(java.time.LocalDate.now(), "sample", java.math.BigDecimal.ONE, 1L, 1L);
        PedidoResponse response = new PedidoResponse(1L, java.time.LocalDate.now(), "sample", java.math.BigDecimal.ONE, 1L, 1L);
        when(pedidoService.update(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/pedidos/" + 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void delete_returns204() throws Exception {
        mockMvc.perform(delete("/api/pedidos/" + 1L))
            .andExpect(status().isNoContent());
    }
}
