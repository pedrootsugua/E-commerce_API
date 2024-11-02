package com.br.senac.sp.controller;

import com.br.senac.sp.dto.PedidoRequestDTO;
import com.br.senac.sp.dto.PedidoResponseDTO;
import com.br.senac.sp.model.ItemPedidoModel;
import com.br.senac.sp.model.PedidoModel;
import com.br.senac.sp.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<PedidoResponseDTO> cadastrarPedido(@RequestBody PedidoRequestDTO pedidoDTO) throws Exception {
        return pedidoService.cadastrarPedido(pedidoDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<PedidoModel>> listaPedidos (@PathVariable (value = "id") Long id) throws Exception {
        return pedidoService.listarPedidosUsuario(id);

    }
    @GetMapping("/{pedidoId}/itens")
    public ResponseEntity<List<ItemPedidoModel>> listarItensPorPedido(@PathVariable Long pedidoId) throws Exception {
        return pedidoService.listarItensPorPedido(pedidoId);
    }

}
