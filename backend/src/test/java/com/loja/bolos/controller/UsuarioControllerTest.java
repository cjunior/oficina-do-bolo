package com.loja.bolos.controller;

import com.loja.bolos.dto.UsuarioRequest;
import com.loja.bolos.dto.UsuarioResponse;
import com.loja.bolos.exception.ResourceNotFoundException;
import com.loja.bolos.service.UsuarioService;
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

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
class UsuarioControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockitoBean private UsuarioService usuarioService;

    @Test
    void create_returns201() throws Exception {
        UsuarioRequest request = new UsuarioRequest("sample", "user@example.com", "sample", "sample");
        UsuarioResponse response = new UsuarioResponse(1L, "sample", "user@example.com", "sample", "sample");

        when(usuarioService.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void findById_whenMissing_returns404() throws Exception {
        when(usuarioService.findById(eq(1L)))
            .thenThrow(new ResourceNotFoundException("not found"));

        mockMvc.perform(get("/api/usuarios/" + 1L))
            .andExpect(status().isNotFound());
    }

    @Test
    void findAll_returns200() throws Exception {
        UsuarioResponse response = new UsuarioResponse(1L, "sample", "user@example.com", "sample", "sample");
        when(usuarioService.findAll()).thenReturn(java.util.List.of(response));

        mockMvc.perform(get("/api/usuarios"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    void findById_whenPresent_returns200() throws Exception {
        UsuarioResponse response = new UsuarioResponse(1L, "sample", "user@example.com", "sample", "sample");
        when(usuarioService.findById(eq(1L))).thenReturn(response);

        mockMvc.perform(get("/api/usuarios/" + 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void update_returns200() throws Exception {
        UsuarioRequest request = new UsuarioRequest("sample", "user@example.com", "sample", "sample");
        UsuarioResponse response = new UsuarioResponse(1L, "sample", "user@example.com", "sample", "sample");
        when(usuarioService.update(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/usuarios/" + 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void delete_returns204() throws Exception {
        mockMvc.perform(delete("/api/usuarios/" + 1L))
            .andExpect(status().isNoContent());
    }
}
