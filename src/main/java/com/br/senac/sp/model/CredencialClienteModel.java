package com.br.senac.sp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "credencial_cliente")
public class CredencialClienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @JsonIgnore
    @Column(nullable = false)
    private String senha;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "clienteId")
    private ClienteModel clienteId;
}