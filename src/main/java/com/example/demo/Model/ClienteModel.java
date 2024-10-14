package com.example.demo.Model;

import com.example.demo.DTO.CadastroClienteDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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

    public ClienteModel(CadastroClienteDTO dto) {
        this.nome = dto.getNome();
        this.genero = dto.getGenero();
        this.cpf = dto.getCpf();
        this.dtNascimento = dto.getDtNascimento();
    }
}