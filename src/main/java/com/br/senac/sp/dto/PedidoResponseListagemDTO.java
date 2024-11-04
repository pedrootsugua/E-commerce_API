package com.br.senac.sp.dto;

import com.br.senac.sp.model.PedidoModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PedidoResponseListagemDTO {
    private Long id;
    private Date dataPedido;
    private String status;
    private Double total;

    public PedidoResponseListagemDTO (PedidoModel model) {
        this.id = model.getId();
        this.dataPedido = model.getDataPedido();
        this.status = model.getStatus();
        this.total = model.getValorTotal();
    }
}
