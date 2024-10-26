package com.br.senac.sp.model;

import com.br.senac.sp.dto.ProdutoDTO;
import com.br.senac.sp.exception.AvaliacaoInvalidaException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "produto")
public class ProdutoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 200)
    private String nomeProduto;
    @Column(nullable = false, length = 2000)
    private String descricao;
    @Column(nullable = false)
    private Double preco;
    @Column(nullable = false)
    private int quantidade;
    @Column(nullable = false)
    private String categoria;
    @Column(nullable = false)
    private String marca;
    @Column(nullable = false)
    private Boolean ativo;
    @Column(nullable = false)
    private Double avaliacao;
    @OneToMany(mappedBy = "produtoId", cascade = CascadeType.ALL)
    private List<URLImagensModel> urlImagensModels;

    public ProdutoModel(ProdutoDTO dto){
        this.nomeProduto = dto.getNomeProduto();
        this.descricao = dto.getDescricao();
        this.preco = arredondarParaDuasCasasDecimais(dto.getPreco());
        this.quantidade = dto.getQuantidade();
        this.categoria = dto.getCategoria();
        this.marca = dto.getMarca();
        this.ativo = true;
        this.avaliacao = dto.getAvaliacao();
    }
    public void setPreco(double preco) {
        this.preco = arredondarParaDuasCasasDecimais(preco);
    }

    private double arredondarParaDuasCasasDecimais(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void setAvaliacao(Double avaliacao) {
        if (avaliacao < 1 || avaliacao > 5 || (avaliacao * 10) % 5 != 0) {
            throw new AvaliacaoInvalidaException("Avaliação deve estar entre 1 e 5 e variar de 0,5 em 0,5.");
        }
        this.avaliacao = avaliacao;
    }

}
