package com.br.senac.sp.service;

import com.br.senac.sp.dto.CarrinhoBuscarNLRequestDTO;
import com.br.senac.sp.dto.CarrinhoBuscarNLResponseDTO;
import com.br.senac.sp.dto.ProdutoQuantidadeDTO;
import com.br.senac.sp.model.ProdutoModel;
import com.br.senac.sp.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

public class CarrinhoServiceTest {

    @InjectMocks
    private CarrinhoService carrinhoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscarCarrinhoNL_ReturnsResponseEntityWithCarrinhoBuscarNLResponseDTO() {
        CarrinhoBuscarNLRequestDTO requestDTO = new CarrinhoBuscarNLRequestDTO();
        requestDTO.setListaIds(Arrays.asList("1", "2", "1"));

        ProdutoModel produto1 = new ProdutoModel();
        produto1.setId(1L);
        produto1.setNomeProduto("Produto 1");
        produto1.setPreco(10.0);
        produto1.setQuantidade(2);
        produto1.setAtivo(true);
        produto1.setAvaliacao(5.0);
        produto1.setCategoria("Categoria 1");
        produto1.setUrlImagensModels(Collections.emptyList());

        ProdutoModel produto2 = new ProdutoModel();
        produto2.setId(2L);
        produto2.setNomeProduto("Produto 2");
        produto2.setPreco(10.0);
        produto2.setQuantidade(2);
        produto2.setAtivo(true);
        produto2.setAvaliacao(5.0);
        produto2.setCategoria("Categoria 1");
        produto2.setUrlImagensModels(Collections.emptyList());

        when(produtoRepository.findByIdIn(anyList())).thenReturn(Arrays.asList(produto1, produto2));

        ResponseEntity<CarrinhoBuscarNLResponseDTO> response = carrinhoService.buscarCarrinhoNL(requestDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getProdutos().size());
    }

    @Test
    void buscarCarrinhoNL_ReturnsEmptyResponseWhenNoProductsFound() {
        CarrinhoBuscarNLRequestDTO requestDTO = new CarrinhoBuscarNLRequestDTO();
        requestDTO.setListaIds(Collections.singletonList("3"));

        when(produtoRepository.findByIdIn(anyList())).thenReturn(Collections.emptyList());

        ResponseEntity<CarrinhoBuscarNLResponseDTO> response = carrinhoService.buscarCarrinhoNL(requestDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(0, response.getBody().getProdutos().size());
    }

    @Test
    void buscarCarrinhoNL_ReturnsCorrectProductQuantities() {
        CarrinhoBuscarNLRequestDTO requestDTO = new CarrinhoBuscarNLRequestDTO();
        requestDTO.setListaIds(Arrays.asList("1", "2", "1", "2", "2"));

        ProdutoModel produto1 = new ProdutoModel();
        produto1.setId(1L);
        produto1.setNomeProduto("Produto 1");
        produto1.setPreco(10.0);
        produto1.setQuantidade(2);
        produto1.setAtivo(true);
        produto1.setAvaliacao(5.0);
        produto1.setCategoria("Categoria 1");
        produto1.setUrlImagensModels(Collections.emptyList());

        ProdutoModel produto2 = new ProdutoModel();
        produto2.setId(2L);
        produto2.setNomeProduto("Produto 2");
        produto2.setPreco(10.0);
        produto2.setQuantidade(2);
        produto2.setAtivo(true);
        produto2.setAvaliacao(5.0);
        produto2.setCategoria("Categoria 1");
        produto2.setUrlImagensModels(Collections.emptyList());

        when(produtoRepository.findByIdIn(anyList())).thenReturn(Arrays.asList(produto1, produto2));

        ResponseEntity<CarrinhoBuscarNLResponseDTO> response = carrinhoService.buscarCarrinhoNL(requestDTO);

        List<ProdutoQuantidadeDTO> produtos = response.getBody().getProdutos();
        assertEquals(2, produtos.size());
        assertEquals(2, produtos.get(0).getQuantidade());
        assertEquals(3, produtos.get(1).getQuantidade());
    }
}