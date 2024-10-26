package com.br.senac.sp.repository;

import com.br.senac.sp.model.ItemPedidoModel;
import com.br.senac.sp.model.PedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedidoModel, Long> {
    List<ItemPedidoModel> findByIdPedidoId(PedidoModel pedidoId);
}
