package com.br.senac.sp.controller;

import com.br.senac.sp.dto.AutenticacaoLoginDTO;
import com.br.senac.sp.dto.CredencialDTO;
import com.br.senac.sp.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@CrossOrigin("*")
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/entrar")
    public ResponseEntity<AutenticacaoLoginDTO> acessarUsuario (@RequestBody CredencialDTO dto) throws ParseException {
        return loginService.entrar(dto);
    }
}
