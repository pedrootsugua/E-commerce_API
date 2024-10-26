package com.br.senac.sp.model;

import com.br.senac.sp.dto.CadastroClienteDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cliente")
public class ClienteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @CPF
    @Column(nullable = false, unique = true)
    private String cpf;
    @Column(nullable = false, name = "dt_nascimento")
    private Date dtNascimento;
    @Column(nullable = false)
    private String genero;

    @OneToOne(mappedBy = "clienteId", cascade = CascadeType.ALL)
    private CredencialClienteModel credencialClienteId;

    @JsonManagedReference
    @OneToMany(mappedBy = "clienteId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EnderecoModel> enderecos;

    @JsonManagedReference
    @OneToMany(mappedBy = "clienteId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PedidoModel> pedidos;

    public ClienteModel(CadastroClienteDTO dto) {
        this.nome = dto.getNome();
        this.genero = dto.getGenero();
        this.cpf = dto.getCpf();
        this.dtNascimento = dto.getDtNascimento();
    }
}