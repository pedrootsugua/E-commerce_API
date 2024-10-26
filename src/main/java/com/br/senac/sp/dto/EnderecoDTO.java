package com.br.senac.sp.dto;


import com.br.senac.sp.model.EnderecoModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EnderecoDTO {
    private Long id;
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String numero;
    private String cidade;
    private String uf;
    private Boolean entrega;
    private Boolean cobranca;

    public EnderecoDTO(EnderecoModel endereco) {
        this.id = endereco.getId();
        this.cep = endereco.getCep();
        this.logradouro = endereco.getLogradouro();
        this.complemento = endereco.getComplemento();
        this.bairro = endereco.getBairro();
        this.numero = endereco.getNumero();
        this.cidade = endereco.getCidade();
        this.uf = endereco.getUf();
        this.entrega = endereco.getEntrega();
        this.cobranca = endereco.getCobranca();
    }
}
