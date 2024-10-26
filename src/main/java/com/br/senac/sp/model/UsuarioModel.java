package com.br.senac.sp.model;

import com.br.senac.sp.dto.UsuarioCredencialDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Table(name = "usuario")
public class UsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @CPF
    @Column(nullable = false)
    private String cpf;
    @Column(nullable = false)
    private String grupo;
    @Column(nullable = false)
    private boolean ativo;

    @OneToOne(mappedBy = "usuarioId", cascade = CascadeType.ALL)
    private CredencialModel credencialId;

    public UsuarioModel(UsuarioCredencialDTO dto){
        this.nome = dto.getNome();
        this.cpf = dto.getCpf();
        this.grupo = dto.getGrupo();
        this.ativo = true;
    }
}
