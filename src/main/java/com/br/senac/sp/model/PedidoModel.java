package com.br.senac.sp.model;

import com.br.senac.sp.constants.Pagamento;
import com.br.senac.sp.dto.PedidoRequestDTO;
import com.br.senac.sp.exception.InvalidPaymentMethodException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pedido")
@Component
public class PedidoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "dataPedido", nullable = false)
    private Date dataPedido;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "formaPagamento", nullable = false)
    private String formaPagamento;

    @Column(name = "valorTotal", nullable = false)
    private Double valorTotal;

    @Column(name = "subTotal", nullable = false)
    private Double subTotal;

    @Column(name = "frete", nullable = false)
    private Double frete;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteModel clienteId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private EnderecoModel enderecoId;

    @OneToMany(mappedBy = "id.pedidoId", cascade = CascadeType.ALL)
    private List<ItemPedidoModel> itensPedidoModel;

    public PedidoModel(PedidoRequestDTO dto) {
        this.status = "aguardando pagamento";
        if (Pagamento.isValid(dto.getFormaPagamento())) {
            this.formaPagamento = dto.getFormaPagamento();
        } else {
            throw new InvalidPaymentMethodException("Método de pagamento inválido. Os tipos de pagamento aceitos são:" + String.join(", ", Arrays.toString(Pagamento.values())));
        }
        this.frete = dto.getFrete();
    }
}
