package com.br.senac.sp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UsuarioCredencialDTO {

    private String nome;
    private String cpf;
    private String grupo;
    private String email;
    private String senha;
}
