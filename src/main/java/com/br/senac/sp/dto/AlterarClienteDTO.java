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
@Getter @Setter
public class AlterarClienteDTO {
    private Long idCliente;
    private Long idEnderecoPadrao;
    private String nome;
    private String email;
    private String senha;
    private Date dtNascimento;
    private String genero;
    List<EnderecoDTO> enderecos;

    public AlterarClienteDTO (ClienteModel cliente) {
        this.idCliente = cliente.getId();
        this.nome = cliente.getNome();
        this.email = cliente.getCredencialClienteId().getEmail();
        this.dtNascimento = cliente.getDtNascimento();
        this.genero = cliente.getGenero();
        this.enderecos = cliente.getEnderecos().stream().map(EnderecoDTO::new).toList();
    }
}
