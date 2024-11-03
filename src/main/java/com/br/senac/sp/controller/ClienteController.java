package com.br.senac.sp.controller;

import com.br.senac.sp.dto.AlterarClienteDTO;
import com.br.senac.sp.dto.CadastroClienteDTO;
import com.br.senac.sp.model.EnderecoModel;
import com.br.senac.sp.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping("/cadastro")
    public ResponseEntity<CadastroClienteDTO> criarCliente(@RequestBody CadastroClienteDTO dto) throws ParseException {
        return clienteService.cadastrarCliente(dto);
    }

    @PutMapping("/alterar")
    public ResponseEntity<AlterarClienteDTO> alterarCliente(@RequestBody AlterarClienteDTO dto) throws ParseException {
        return clienteService.alterarCliente(dto);
    }

    @GetMapping("/consultar/{id}")
    public ResponseEntity<CadastroClienteDTO> buscarUsuarioPorId(@PathVariable Long id) {
        return clienteService.buscarClientePorId(id);
    }

    @GetMapping("/enderecoEntrega/{id}")
    public ResponseEntity<EnderecoModel> consultarEnderecoEntrega (@PathVariable Long id) {
        return clienteService.buscarEnderecoEntrega(id);
    }

}
