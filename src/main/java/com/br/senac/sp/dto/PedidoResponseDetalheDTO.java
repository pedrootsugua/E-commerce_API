package com.br.senac.sp.dto;

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
}
