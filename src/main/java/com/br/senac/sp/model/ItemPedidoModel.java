package com.br.senac.sp.model;

import com.br.senac.sp.dto.ProdutoQtdDTO;
import com.br.senac.sp.keys.ItemPedidoKey;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item_pedido")
@Component
public class ItemPedidoModel {

    @EmbeddedId
    private ItemPedidoKey id;

    @Column(nullable = false, name = "quantidade")
    private Integer quantidade;

    @Column(nullable = false, name = "valor_unitario")
    private Double valorUnitario;

    @Column(nullable = false, name = "subTotal")
    private Double subTotal;

    public ItemPedidoModel(ProdutoQtdDTO dto) {
        this.quantidade = dto.getQuantidade();
        this.valorUnitario = dto.getValorUnitario();
        this.subTotal = dto.getValorUnitario() * dto.getQuantidade();
    }
}
