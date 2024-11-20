package com.br.senac.sp.dto;

import com.br.senac.sp.model.ItemPedidoModel;
import com.br.senac.sp.model.PedidoModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PedidoResponseDetalheDTO {
    private List<ProdutoQtdDTO> produtoQtd;
    private String formaPagamento;
    private String status;
    private Double frete;
    private Double subtotal;
    private Double valorTotal;
    private Date dataPedido;
    private EnderecoDTO endereco;

    public PedidoResponseDetalheDTO(PedidoModel pedido, List<ItemPedidoModel> itemPedidoModelSalvo) {
        this.produtoQtd = pedido.getItensPedidoModel().stream().map(itemPedidoModel -> new ProdutoQtdDTO(itemPedidoModel, itemPedidoModel.getId().getProdutoId().getNomeProduto())).toList();
        this.formaPagamento = pedido.getFormaPagamento();
        this.status = pedido.getStatus();
        this.frete = pedido.getFrete();
        this.subtotal = pedido.getSubTotal();
        this.valorTotal = pedido.getValorTotal();
        this.dataPedido = pedido.getDataPedido();
        this.endereco = new EnderecoDTO(pedido.getEnderecoId());
    }
}
