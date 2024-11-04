package com.br.senac.sp.service;

import com.br.senac.sp.dto.*;
import com.br.senac.sp.keys.ItemPedidoKey;
import com.br.senac.sp.model.*;
import com.br.senac.sp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        Double subTotal = BigDecimal.valueOf(calcularValorTotalPedido(pedidoDTO.getProdutoQtd()))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
        PedidoModel pedidoModel = new PedidoModel(pedidoDTO);
        pedidoModel.setDataPedido(Date.from(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant()));
        pedidoModel.setClienteId(clienteSalvo);
        pedidoModel.setEnderecoId(enderecoSalvo);
        pedidoModel.setSubTotal(subTotal);
        pedidoModel.setValorTotal(BigDecimal.valueOf(subTotal + pedidoDTO.getFrete())
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue());

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

    private Double calcularValorTotalPedido(List<ProdutoQtdDTO> produtoQtd) {
        return produtoQtd.stream()
                .mapToDouble(produtoQtdDTO -> produtoQtdDTO.getValorUnitario() * produtoQtdDTO.getQuantidade())
                .sum();
    }

    private void cadastrarItemPedido(PedidoModel pedidoModel, ProdutoModel produtoModel, ProdutoQtdDTO produtoQtd) {
        ItemPedidoModel itemPedidoModel = new ItemPedidoModel(produtoQtd);
        itemPedidoModel.setId(new ItemPedidoKey(pedidoModel, produtoModel));
        itemPedidoRepository.save(itemPedidoModel);
    }

    public ResponseEntity<List<PedidoResponseListagemDTO>> listarPedidosUsuario(Long id) throws Exception {
        ClienteModel cliente = clienteRepository.findById(id).orElseThrow(
                () -> new Exception("Usuário não encontrado com esse ID"));

        List<PedidoModel> pedidos = cliente.getPedidos();
        List<PedidoResponseListagemDTO> pedidoResponseDTOs = pedidos.stream().map(PedidoResponseListagemDTO::new).toList();

        return new ResponseEntity<>(pedidoResponseDTOs, HttpStatus.OK);
    }

    public ResponseEntity<PedidoResponseDetalheDTO> detalhePedido(Long pedidoId) {
        PedidoModel pedido = pedidoRepository.findById(pedidoId).orElseThrow(
                () -> new RuntimeException("Pedido não encontrado!"));

        List<ItemPedidoModel> itemPedidoModelSalvo = itemPedidoRepository.findByIdPedidoId(pedido);
        PedidoResponseDetalheDTO pedidoResponseDTOs = new PedidoResponseDetalheDTO(pedido, itemPedidoModelSalvo);

        return new ResponseEntity<>(pedidoResponseDTOs, HttpStatus.OK);
    }
}
