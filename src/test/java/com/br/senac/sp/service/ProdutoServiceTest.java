package com.br.senac.sp.service;

import com.br.senac.sp.dto.AlterarProdutoRequestDTO;
import com.br.senac.sp.dto.AlterarProdutoResponseDTO;
import com.br.senac.sp.dto.ProdutoDTO;
import com.br.senac.sp.model.ProdutoModel;
import com.br.senac.sp.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cadastrarProduto_ReturnsCreatedResponse() throws Exception {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        ProdutoModel produtoModel = new ProdutoModel(produtoDTO);
        when(produtoRepository.save(any(ProdutoModel.class))).thenReturn(produtoModel);

        ResponseEntity<ProdutoDTO> response = produtoService.cadastrarProduto(produtoDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(produtoDTO, response.getBody());
    }

    @Test
    void listarProdutos_ReturnsPagedProducts() {
        int page = 0;
        int size = 10;
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

        List<ProdutoModel> produtoModels = List.of(produto1, produto2);

        Page<ProdutoModel> produtoPage = new PageImpl<>(produtoModels);
        when(produtoRepository.findAll(any(Pageable.class))).thenReturn(produtoPage);

        ResponseEntity<Map<String, Object>> response = produtoService.listarProdutos(page, size);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void buscarProduto_ReturnsProductWhenFound() {
        Long id = 1L;
        ProdutoModel produto1 = new ProdutoModel();
        produto1.setId(1L);
        produto1.setNomeProduto("Produto 1");
        produto1.setPreco(10.0);
        produto1.setQuantidade(2);
        produto1.setAtivo(true);
        produto1.setAvaliacao(5.0);
        produto1.setCategoria("Categoria 1");
        produto1.setUrlImagensModels(Collections.emptyList());
        when(produtoRepository.findById(id)).thenReturn(Optional.of(produto1));

        ResponseEntity<AlterarProdutoResponseDTO> response = produtoService.buscarProduto(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void buscarProduto_ReturnsNotFoundWhenProductNotFound() {
        Long id = 1L;
        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<AlterarProdutoResponseDTO> response = produtoService.buscarProduto(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void desativarProduto_ReturnsOkWhenProductDeactivated() throws Exception {
        Long id = 1L;
        ProdutoModel produtoModel = new ProdutoModel();
        when(produtoRepository.findById(id)).thenReturn(Optional.of(produtoModel));

        ResponseEntity<Void> response = produtoService.desativarProduto(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void desativarProduto_ThrowsExceptionWhenProductNotFound() {
        Long id = 1L;
        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        try {
            produtoService.desativarProduto(id);
        } catch (Exception e) {
            assertEquals("Produto não encontrado", e.getMessage());
        }
    }

    @Test
    void ativarProduto_ReturnsOkWhenProductActivated() throws Exception {
        Long id = 1L;
        ProdutoModel produtoModel = new ProdutoModel();
        when(produtoRepository.findById(id)).thenReturn(Optional.of(produtoModel));

        ResponseEntity<Void> response = produtoService.ativarProduto(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void ativarProduto_ThrowsExceptionWhenProductNotFound() {
        Long id = 1L;
        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        try {
            produtoService.ativarProduto(id);
        } catch (Exception e) {
            assertEquals("Produto não encontrado", e.getMessage());
        }
    }

    @Test
    void listarProdutosAtivos_ReturnsActiveProducts() {
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

        List<ProdutoModel> produtoModels = List.of(produto1, produto2);
        when(produtoRepository.findByAtivo(true)).thenReturn(produtoModels);

        ResponseEntity<Map<String, Object>> response = produtoService.listarProdutosAtivos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void buscarProdutoPorNome_ReturnsPagedProducts() {
        String nome = "Produto";
        int page = 0;
        int size = 10;
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

        List<ProdutoModel> produtoModels = List.of(produto1, produto2);

        Page<ProdutoModel> produtoPage = new PageImpl<>(produtoModels);
        when(produtoRepository.buscaPorNome(anyString(), any(Pageable.class))).thenReturn(produtoPage);

        ResponseEntity<Map<String, Object>> response = produtoService.buscarProdutoPorNome(nome, page, size);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void buscarProdutoPorNome_ReturnsNotFoundWhenNoProducts() {
        String nome = "Produto";
        int page = 0;
        int size = 10;
        Page<ProdutoModel> produtoPage = mock(Page.class);
        when(produtoPage.getContent()).thenReturn(Collections.emptyList());
        when(produtoRepository.buscaPorNome(anyString(), any(Pageable.class))).thenReturn(produtoPage);

        ResponseEntity<Map<String, Object>> response = produtoService.buscarProdutoPorNome(nome, page, size);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void alterarProduto_ReturnsUpdatedProduct() throws Exception {
        AlterarProdutoRequestDTO requestDTO = new AlterarProdutoRequestDTO();
        requestDTO.setId(1L);
        requestDTO.setNomeProduto("Produto Atualizado");
        requestDTO.setDescricao("Descrição Atualizada");
        requestDTO.setPreco(20.0);
        requestDTO.setQuantidade(5);
        requestDTO.setCategoria("Categoria Atualizada");
        requestDTO.setMarca("Marca Atualizada");
        requestDTO.setAvaliacao(4.5);
        requestDTO.setUrlImagensExcluidas(Collections.emptyList());

        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setUrlImagensModels(Collections.emptyList());
        when(produtoRepository.findById(requestDTO.getId())).thenReturn(Optional.of(produtoModel));
        when(produtoRepository.save(any(ProdutoModel.class))).thenReturn(produtoModel);

        ResponseEntity<AlterarProdutoResponseDTO> response = produtoService.alterarProduto(requestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void alterarProduto_ThrowsExceptionWhenProductNotFound() {
        AlterarProdutoRequestDTO requestDTO = new AlterarProdutoRequestDTO();
        requestDTO.setId(1L);

        when(produtoRepository.findById(requestDTO.getId())).thenReturn(Optional.empty());

        try {
            produtoService.alterarProduto(requestDTO);
        } catch (RuntimeException e) {
            assertEquals("Produto não encontrado!", e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}