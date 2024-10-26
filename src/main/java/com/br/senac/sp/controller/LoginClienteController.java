package com.br.senac.sp.controller;

import com.br.senac.sp.dto.AutenticacaoLoginDTO;
import com.br.senac.sp.dto.CredencialDTO;
import com.br.senac.sp.service.LoginClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@CrossOrigin("*")
@RequestMapping("/loginCliente")
public class LoginClienteController {

    @Autowired
    private LoginClienteService loginClienteService;

    @PostMapping("/entrarCliente")
    public ResponseEntity<AutenticacaoLoginDTO> acessarCliente (@RequestBody CredencialDTO dto) throws ParseException {
        return loginClienteService.entrarCliente(dto);
    }
}
