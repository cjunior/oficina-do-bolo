CREATE TABLE pedidos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    data_pedido DATE,
    status VARCHAR(255),
    valor_total DECIMAL(19,2),
    cliente_id BIGINT NOT NULL,
    produtos_id BIGINT NOT NULL
);
