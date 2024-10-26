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
public class PedidoResponseDTO {
    private List<ProdutoQtdDTO> produtoQtd;
    private String formaPagamento;
    private Double frete;
    private String status;
    private Double valorTotal;
    private Date dataPedido;
    private CadastroClienteDTO cliente;
    private EnderecoDTO endereco;

    public PedidoResponseDTO(PedidoModel pedido, List<ItemPedidoModel> itemPedidoModelSalvo) {
        this.produtoQtd = itemPedidoModelSalvo.stream().map(ProdutoQtdDTO::new).toList();
        this.formaPagamento = pedido.getFormaPagamento();
        this.frete = pedido.getFrete();
        this.status = pedido.getStatus();
        this.valorTotal = pedido.getValorTotal();
        this.dataPedido = pedido.getDataPedido();
        this.cliente = new CadastroClienteDTO(pedido.getClienteId());
        this.endereco = new EnderecoDTO(pedido.getEnderecoId());
    }
}
