package com.br.senac.sp.controller;

import com.br.senac.sp.dto.AlterarProdutoRequestDTO;
import com.br.senac.sp.dto.AlterarProdutoResponseDTO;
import com.br.senac.sp.dto.ProdutoDTO;
import com.br.senac.sp.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/cadastro")
    public ResponseEntity<ProdutoDTO> cadastrarProduto(
            @RequestPart("produto") ProdutoDTO dto,
            @RequestPart("imagemPrincipal") MultipartFile imagemPrincipal,
            @RequestPart(value = "imagens", required = false) List<MultipartFile> imagens) throws Exception {
        dto.setImagemPrincipal(imagemPrincipal);
        dto.setImagens(imagens);
        return produtoService.cadastrarProduto(dto);
    }

    @GetMapping("/listagem")
    public ResponseEntity<Map<String, Object>> listarProdutos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return produtoService.listarProdutos(page, size);
    }

    @GetMapping("/buscaID")
    public ResponseEntity<AlterarProdutoResponseDTO> buscarProdutoPorId(
            @RequestParam("id") Long id) {
        return produtoService.buscarProduto(id);
    }

    @GetMapping("/buscaNome")
    public ResponseEntity<Map<String, Object>> buscarProdutoPorNome(
            @RequestParam("nome") String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return produtoService.buscarProdutoPorNome(nome, page, size);
    }

    @PutMapping("/alterar")
    public ResponseEntity<AlterarProdutoResponseDTO> alterarProduto(
            @RequestPart AlterarProdutoRequestDTO produto,
            @RequestPart(value = "imagemPrincipal", required = false) MultipartFile imagemPrincipal,
            @RequestPart(value = "imagensNovas", required = false) List<MultipartFile> imagensNovas) throws Exception {
        produto.setImagemPrincipal(imagemPrincipal);
        produto.setImagensNovas(imagensNovas);
        return produtoService.alterarProduto(produto);
    }

    @PatchMapping("/desativar/{id}")
    public ResponseEntity<Void> desativarProduto (@PathVariable("id") Long id) throws Exception {
        return produtoService.desativarProduto(id);
    }

    @PatchMapping("/ativar/{id}")
    public ResponseEntity<Void> ativarProduto (@PathVariable("id") Long id) throws Exception {
        return produtoService.ativarProduto(id);
    }

    @GetMapping("/listagemAtivos")
    public ResponseEntity<Map<String, Object>> listarProdutosAtivos() {
        return produtoService.listarProdutosAtivos();
    }
}