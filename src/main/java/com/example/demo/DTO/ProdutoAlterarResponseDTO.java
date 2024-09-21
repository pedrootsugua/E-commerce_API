package com.example.demo.DTO;

import com.example.demo.Model.ProdutoModel;
import com.example.demo.Model.URLImagensModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProdutoAlterarResponseDTO {
    private String nomeProduto;
    private String descricao;
    private double preco;
    private int quantidade;
    private String categoria;
    private String marca;
    private Double avaliacao;
    private String imagemPrincipal;
    private List<String> imagens;

    public ProdutoAlterarResponseDTO(ProdutoModel produtoModel){
        this.nomeProduto = produtoModel.getNomeProduto();
        this.descricao = produtoModel.getDescricao();
        this.preco = produtoModel.getPreco();
        this.quantidade = produtoModel.getQuantidade();
        this.categoria = produtoModel.getCategoria();
        this.marca = produtoModel.getMarca();
        this.avaliacao = produtoModel.getAvaliacao();
        List<URLImagensModel> urlImagensModels = produtoModel.getUrlImagensModels();
        for (URLImagensModel urlImagensModel : produtoModel.getUrlImagensModels()) {
            if (urlImagensModel.getPadrao()) {
                this.imagemPrincipal = urlImagensModel.getUrl();
            }
        }
        this.imagens = urlImagensModels.stream()
                .filter(urlImagensModel -> !urlImagensModel.getPadrao())
                .map(URLImagensModel::getUrl)
                .toList();    }
}
