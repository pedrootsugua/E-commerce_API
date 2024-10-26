package com.br.senac.sp.service;

import com.br.senac.sp.dto.PedidoRequestDTO;
import com.br.senac.sp.dto.PedidoResponseDTO;
import com.br.senac.sp.dto.ProdutoQtdDTO;
import com.br.senac.sp.keys.ItemPedidoKey;
import com.br.senac.sp.model.*;
import com.br.senac.sp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CredencialClienteRepository credencialClienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public ResponseEntity<PedidoResponseDTO> cadastrarPedido(PedidoRequestDTO pedidoDTO) throws Exception {
        ClienteModel clienteSalvo = clienteRepository.findById(pedidoDTO.getClienteId()).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado!"));
        CredencialClienteModel credencialClienteSalva = credencialClienteRepository.findByClienteId(clienteSalvo);
        EnderecoModel enderecoSalvo = enderecoRepository.findById(pedidoDTO.getEnderecoId()).orElseThrow(
                () -> new RuntimeException("Endereço não encontrado!"));
        Double subTotal = calcularValorTotalPedido(pedidoDTO.getProdutoQtd());
        PedidoModel pedidoModel = new PedidoModel(pedidoDTO);
        pedidoModel.setDataPedido(Date.from(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant()));
        pedidoModel.setClienteId(clienteSalvo);
        pedidoModel.setEnderecoId(enderecoSalvo);
        pedidoModel.setSubTotal(subTotal);
        pedidoModel.setValorTotal(subTotal + pedidoDTO.getFrete());

        PedidoModel pedidoSalvo = pedidoRepository.save(pedidoModel);
        pedidoDTO.getProdutoQtd().forEach(
                produtoQtdDTO -> {
                    ProdutoModel produtoModel = produtoRepository.findById(produtoQtdDTO.getIdProduto()).orElseThrow(
                            () -> new RuntimeException("Produto não encontrado"));
                    cadastrarItemPedido(pedidoSalvo, produtoModel, produtoQtdDTO);
                }
        );

        List<ItemPedidoModel> itemPedidoModelSalvo = itemPedidoRepository.findByIdPedidoId(pedidoSalvo);

        return ResponseEntity.ok(new PedidoResponseDTO(pedidoSalvo, itemPedidoModelSalvo));
    }

    private Double calcularValorTotalPedido(List<ProdutoQtdDTO> produtoQtd){
        return produtoQtd.stream()
                .mapToDouble(produtoQtdDTO -> produtoQtdDTO.getValorUnitario() * produtoQtdDTO.getQuantidade())
                .sum();
    }

    private void cadastrarItemPedido(PedidoModel pedidoModel, ProdutoModel produtoModel,ProdutoQtdDTO produtoQtd) {
            ItemPedidoModel itemPedidoModel = new ItemPedidoModel(produtoQtd);
            itemPedidoModel.setId(new ItemPedidoKey(pedidoModel, produtoModel));
            itemPedidoRepository.save(itemPedidoModel);
    }
}
