package com.br.senac.sp.keys;

import com.br.senac.sp.model.PedidoModel;
import com.br.senac.sp.model.ProdutoModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class ItemPedidoKey {
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fk_pedido_id")
    private PedidoModel pedidoId;

    @ManyToOne
    @JoinColumn(name = "fk_produto_id")
    private ProdutoModel produtoId;
}
