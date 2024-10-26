package com.br.senac.sp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AutenticacaoLoginDTO {

    private boolean autenticado;
    private Long id;
    private String nome;
    private String grupo;
    private boolean ativo;
}
