package com.br.senac.sp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AutenticacaoLoginDTO {

    private boolean autenticado;
    private Long id;
    private String nome;
    private String grupo;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean ativo;

    public AutenticacaoLoginDTO(boolean autenticado, Long id, String nome) {
        this.autenticado = autenticado;
        this.id = id;
        this.nome = nome;
    }

    public AutenticacaoLoginDTO(boolean autenticado, Long id, String nome, String grupo, boolean ativo) {
        this.autenticado = autenticado;
        this.id = id;
        this.nome = nome;
        this.grupo = grupo;
        this.ativo = ativo;
    }
}
