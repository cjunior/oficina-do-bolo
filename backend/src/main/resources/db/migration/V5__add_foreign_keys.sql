ALTER TABLE pedidos ADD CONSTRAINT fk_pedidos_cliente_id FOREIGN KEY (cliente_id) REFERENCES clientes(id);
ALTER TABLE pedidos ADD CONSTRAINT fk_pedidos_produtos_id FOREIGN KEY (produtos_id) REFERENCES produtos(id);
