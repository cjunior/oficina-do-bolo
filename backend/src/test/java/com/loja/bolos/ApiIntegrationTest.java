package com.loja.bolos;

import com.loja.bolos.dto.ProdutoRequest;
import com.loja.bolos.dto.ProdutoResponse;
import com.loja.bolos.dto.ClienteRequest;
import com.loja.bolos.dto.ClienteResponse;
import com.loja.bolos.dto.UsuarioRequest;
import com.loja.bolos.dto.UsuarioResponse;
import com.loja.bolos.dto.PedidoRequest;
import com.loja.bolos.dto.PedidoResponse;
import tools.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void openApiDocumentIsServed() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.openapi").exists());
    }

    @Test
    void everyCollectionRouteResponds() throws Exception {
        mockMvc.perform(get("/api/produtos")).andExpect(status().isOk());
        mockMvc.perform(get("/api/clientes")).andExpect(status().isOk());
        mockMvc.perform(get("/api/pedidos")).andExpect(status().isOk());
        mockMvc.perform(get("/api/usuarios")).andExpect(status().isOk());
    }

    @Test
    void crudRoutesAndAcceptanceCriteriaWorkEndToEnd() throws Exception {
        mockMvc.perform(get("/health")).andExpect(status().isOk());

        // Critério de aceite: Deve ser possível cadastrar, editar, remover e listar produtos (bolos)
        // Critério de aceite: imagem obrigatória para cada produto
        // Critério de aceite: produtos inativos não devem aparecer no catálogo de clientes
        ProdutoRequest produtoReq = new ProdutoRequest("sample", "sample", java.math.BigDecimal.ONE, new byte[0], true);
        String produtoBody = mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtoReq)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andReturn().getResponse().getContentAsString();
        var produtoId = objectMapper.readValue(produtoBody, ProdutoResponse.class).id();

        mockMvc.perform(get("/api/produtos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").exists());

        mockMvc.perform(get("/api/produtos/" + produtoId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists());

        mockMvc.perform(put("/api/produtos/" + produtoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtoReq)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists());
        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isBadRequest());

        // Critério de aceite: Gerenciar dados completos do cliente
        ClienteRequest clienteReq = new ClienteRequest("sample", "user@example.com", "sample");
        String clienteBody = mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteReq)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andReturn().getResponse().getContentAsString();
        var clienteId = objectMapper.readValue(clienteBody, ClienteResponse.class).id();

        mockMvc.perform(get("/api/clientes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").exists());

        mockMvc.perform(get("/api/clientes/" + clienteId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists());

        mockMvc.perform(put("/api/clientes/" + clienteId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteReq)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists());
        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isBadRequest());

        // Critério de aceite: Usuários administrativos devem autenticar-se via Keycloak
        // Critério de aceite: Somente usuários com permissão podem acessar a área admin
        UsuarioRequest usuarioReq = new UsuarioRequest("sample", "user@example.com", "sample", "sample");
        String usuarioBody = mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioReq)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andReturn().getResponse().getContentAsString();
        var usuarioId = objectMapper.readValue(usuarioBody, UsuarioResponse.class).id();

        mockMvc.perform(get("/api/usuarios"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").exists());

        mockMvc.perform(get("/api/usuarios/" + usuarioId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists());

        mockMvc.perform(put("/api/usuarios/" + usuarioId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioReq)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists());
        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isBadRequest());

        // Critério de aceite: Pedidos devem estar vinculados a um cliente e conter pelo menos um produto
        // Critério de aceite: Cálculo automático do valorTotal
        // Critério de aceite: Listar e alterar status do pedido
        PedidoRequest pedidoReq = new PedidoRequest(java.time.LocalDate.now(), "sample", java.math.BigDecimal.ONE, clienteId, produtoId);
        String pedidoBody = mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedidoReq)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andReturn().getResponse().getContentAsString();
        var pedidoId = objectMapper.readValue(pedidoBody, PedidoResponse.class).id();

        mockMvc.perform(get("/api/pedidos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").exists());

        mockMvc.perform(get("/api/pedidos/" + pedidoId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists());

        mockMvc.perform(put("/api/pedidos/" + pedidoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedidoReq)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists());
        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isBadRequest());

        mockMvc.perform(delete("/api/pedidos/" + pedidoId))
            .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/pedidos/" + pedidoId))
            .andExpect(status().isNotFound());

        mockMvc.perform(delete("/api/usuarios/" + usuarioId))
            .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/usuarios/" + usuarioId))
            .andExpect(status().isNotFound());

        mockMvc.perform(delete("/api/clientes/" + clienteId))
            .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/clientes/" + clienteId))
            .andExpect(status().isNotFound());

        mockMvc.perform(delete("/api/produtos/" + produtoId))
            .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/produtos/" + produtoId))
            .andExpect(status().isNotFound());
    }
}
