package com.br.senac.sp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PedidoRequestDTO {
    private List<ProdutoQtdDTO> produtoQtd;
    private String formaPagamento;
    private Double frete;
    private Long clienteId;
    private Long enderecoId;
}
