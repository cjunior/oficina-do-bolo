package com.loja.bolos.controller;

import com.loja.bolos.dto.ClienteRequest;
import com.loja.bolos.dto.ClienteResponse;
import com.loja.bolos.exception.ResourceNotFoundException;
import com.loja.bolos.service.ClienteService;
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

@WebMvcTest(ClienteController.class)
@AutoConfigureMockMvc(addFilters = false)
class ClienteControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockitoBean private ClienteService clienteService;

    @Test
    void create_returns201() throws Exception {
        ClienteRequest request = new ClienteRequest("sample", "user@example.com", "sample");
        ClienteResponse response = new ClienteResponse(1L, "sample", "user@example.com", "sample");

        when(clienteService.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void findById_whenMissing_returns404() throws Exception {
        when(clienteService.findById(eq(1L)))
            .thenThrow(new ResourceNotFoundException("not found"));

        mockMvc.perform(get("/api/clientes/" + 1L))
            .andExpect(status().isNotFound());
    }

    @Test
    void findAll_returns200() throws Exception {
        ClienteResponse response = new ClienteResponse(1L, "sample", "user@example.com", "sample");
        when(clienteService.findAll()).thenReturn(java.util.List.of(response));

        mockMvc.perform(get("/api/clientes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    void findById_whenPresent_returns200() throws Exception {
        ClienteResponse response = new ClienteResponse(1L, "sample", "user@example.com", "sample");
        when(clienteService.findById(eq(1L))).thenReturn(response);

        mockMvc.perform(get("/api/clientes/" + 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void update_returns200() throws Exception {
        ClienteRequest request = new ClienteRequest("sample", "user@example.com", "sample");
        ClienteResponse response = new ClienteResponse(1L, "sample", "user@example.com", "sample");
        when(clienteService.update(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/clientes/" + 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void delete_returns204() throws Exception {
        mockMvc.perform(delete("/api/clientes/" + 1L))
            .andExpect(status().isNoContent());
    }
}
