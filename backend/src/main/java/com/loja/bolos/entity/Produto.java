package com.loja.bolos.entity;

import jakarta.persistence.*;
    import java.math.BigDecimal;
import java.util.Objects;
import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "preco")
    private BigDecimal preco;

    @Lob
    @Column(name = "imagem")
    private byte[] imagem;

    @Column(name = "ativo")
    private Boolean ativo;

    @OneToMany(mappedBy = "produtos", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Pedido> pedidos = new java.util.ArrayList<>();

    public Produto() {}

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public byte[] getImagem() { return imagem; }
    public void setImagem(byte[] imagem) { this.imagem = imagem; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public java.util.List<Pedido> getPedidos() { return pedidos; }
    public void setPedidos(java.util.List<Pedido> pedidos) { this.pedidos = pedidos; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto other)) return false;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
