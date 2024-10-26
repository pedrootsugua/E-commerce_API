package com.br.senac.sp.dto;

import com.br.senac.sp.model.ItemPedidoModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProdutoQtdDTO {
    private Long idProduto;
    private Integer quantidade;
    private Double valorUnitario;
    private Double valorTotal;

    public ProdutoQtdDTO (ItemPedidoModel itemPedidoModel) {
        this.idProduto = itemPedidoModel.getId().getProdutoId().getId();
        this.quantidade = itemPedidoModel.getQuantidade();
        this.valorUnitario = itemPedidoModel.getValorUnitario();
        this.valorTotal = itemPedidoModel.getSubTotal();
    }
}
