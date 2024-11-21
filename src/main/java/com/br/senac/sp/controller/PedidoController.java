package com.br.senac.sp.controller;

import com.br.senac.sp.dto.*;
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
    @GetMapping("/{clienteId}")
    public ResponseEntity<List<PedidoResponseListagemDTO>> listaPedidos (@PathVariable (value = "clienteId") Long clienteId) throws Exception {
        return pedidoService.listarPedidosUsuario(clienteId);
    }

    @GetMapping("detalhe/{pedidoId}")
    public ResponseEntity<PedidoResponseDetalheDTO> detalhePedido(@PathVariable (value = "pedidoId") Long pedidoId) throws Exception {
        return pedidoService.detalhePedido(pedidoId);
    }

    @GetMapping("/Estoquista")
    public ResponseEntity<List<PedidoResponseListagemDTO>> listarTodosPedidos() {
        return pedidoService.listarTodosPedidos();
    }

    @PatchMapping("/alterarStatus")
    public ResponseEntity<PedidoResponseDTO> alterarStatusPedido(@RequestBody AlterarPedidoRequest dto) throws Exception {
        return pedidoService.alterarStatusPedido(dto);
    }
}
