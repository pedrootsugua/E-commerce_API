package com.br.senac.sp.dto;

import com.br.senac.sp.model.ClienteModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class CadastroClienteDTO {
    private String nome;
    private String email;
    private String senha;
    private String genero;
    private String cpf;
    private Date dtNascimento;
    List<EnderecoDTO> enderecos;

    public CadastroClienteDTO(ClienteModel clienteModel) {
        this.nome = clienteModel.getNome();
        this.email = clienteModel.getCredencialClienteId().getEmail();
        this.genero = clienteModel.getGenero();
        this.cpf = clienteModel.getCpf();
        this.dtNascimento = clienteModel.getDtNascimento();
        this.enderecos = clienteModel.getEnderecos().stream().map(EnderecoDTO::new).toList();
    }
}
