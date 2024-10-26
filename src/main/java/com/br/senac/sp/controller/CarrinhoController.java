package com.br.senac.sp.controller;

import com.br.senac.sp.dto.CarrinhoBuscarNLRequestDTO;
import com.br.senac.sp.dto.CarrinhoBuscarNLResponseDTO;
import com.br.senac.sp.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @PostMapping("/buscarCarrinhoNL")
    public ResponseEntity<CarrinhoBuscarNLResponseDTO> buscarProdutosCarrinhoNL(@RequestBody CarrinhoBuscarNLRequestDTO dto) throws Exception {
        return carrinhoService.buscarCarrinhoNL(dto);
    }
}
