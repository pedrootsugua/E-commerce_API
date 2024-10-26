// src/main/java/com/example/demo/Service/CarrinhoService.java
package com.br.senac.sp.service;

import com.br.senac.sp.dto.CarrinhoBuscarNLRequestDTO;
import com.br.senac.sp.dto.CarrinhoBuscarNLResponseDTO;
import com.br.senac.sp.dto.ProdutoQuantidadeDTO;
import com.br.senac.sp.dto.ProdutoRetornoDTO;
import com.br.senac.sp.model.ProdutoModel;
import com.br.senac.sp.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CarrinhoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public ResponseEntity<CarrinhoBuscarNLResponseDTO> buscarCarrinhoNL(CarrinhoBuscarNLRequestDTO dto) {
        Map<String, Integer> elementCountMap = countOccurrences(dto.getListaIds());
        List<ProdutoModel> produtos = produtoRepository.findByIdIn(new ArrayList<>(elementCountMap.keySet()));

        List<ProdutoRetornoDTO> produtosRetorno = produtos.stream()
                .map(produto -> new ProdutoRetornoDTO(
                        produto.getId(),
                        produto.getNomeProduto(),
                        produto.getPreco(),
                        produto.getQuantidade(),
                        produto.getAtivo(),
                        produto.getAvaliacao(),
                        produto.getCategoria(),
                        produto.getUrlImagensModels()
                ))
                .toList();
        List<ProdutoQuantidadeDTO> produtosQuantidade = produtosRetorno.stream()
                .map(produto -> new ProdutoQuantidadeDTO(produto, elementCountMap.get(produto.getId().toString())))
                .collect(Collectors.toList());

        CarrinhoBuscarNLResponseDTO response = new CarrinhoBuscarNLResponseDTO(produtosQuantidade);
        return ResponseEntity.ok(response);
    }

    public static Map<String, Integer> countOccurrences(List<String> list) {
        Map<String, Integer> elementCountMap = new HashMap<>();

        for (String element : list) {
            elementCountMap.put(element, elementCountMap.getOrDefault(element, 0) + 1);
        }

        return elementCountMap;
    }
}