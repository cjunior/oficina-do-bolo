package com.loja.bolos.controller;

import com.loja.bolos.dto.ProdutoRequest;
import com.loja.bolos.dto.ProdutoResponse;
import com.loja.bolos.exception.ResourceNotFoundException;
import com.loja.bolos.service.ProdutoService;
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

@WebMvcTest(ProdutoController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProdutoControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockitoBean private ProdutoService produtoService;

    @Test
    void create_returns201() throws Exception {
        ProdutoRequest request = new ProdutoRequest("sample", "sample", java.math.BigDecimal.ONE, new byte[0], true);
        ProdutoResponse response = new ProdutoResponse(1L, "sample", "sample", java.math.BigDecimal.ONE, new byte[0], true);

        when(produtoService.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void findById_whenMissing_returns404() throws Exception {
        when(produtoService.findById(eq(1L)))
            .thenThrow(new ResourceNotFoundException("not found"));

        mockMvc.perform(get("/api/produtos/" + 1L))
            .andExpect(status().isNotFound());
    }

    @Test
    void findAll_returns200() throws Exception {
        ProdutoResponse response = new ProdutoResponse(1L, "sample", "sample", java.math.BigDecimal.ONE, new byte[0], true);
        when(produtoService.findAll()).thenReturn(java.util.List.of(response));

        mockMvc.perform(get("/api/produtos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    void findById_whenPresent_returns200() throws Exception {
        ProdutoResponse response = new ProdutoResponse(1L, "sample", "sample", java.math.BigDecimal.ONE, new byte[0], true);
        when(produtoService.findById(eq(1L))).thenReturn(response);

        mockMvc.perform(get("/api/produtos/" + 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void update_returns200() throws Exception {
        ProdutoRequest request = new ProdutoRequest("sample", "sample", java.math.BigDecimal.ONE, new byte[0], true);
        ProdutoResponse response = new ProdutoResponse(1L, "sample", "sample", java.math.BigDecimal.ONE, new byte[0], true);
        when(produtoService.update(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/produtos/" + 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void delete_returns204() throws Exception {
        mockMvc.perform(delete("/api/produtos/" + 1L))
            .andExpect(status().isNoContent());
    }
}
