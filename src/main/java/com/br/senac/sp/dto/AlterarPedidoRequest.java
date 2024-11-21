package com.br.senac.sp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AlterarPedidoRequest {
    private Long idPedido;
    private String status;
}
