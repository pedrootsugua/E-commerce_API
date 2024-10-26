package com.br.senac.sp.controller;

import com.br.senac.sp.dto.PedidoRequestDTO;
import com.br.senac.sp.dto.PedidoResponseDTO;
import com.br.senac.sp.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
