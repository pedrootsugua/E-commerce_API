package com.br.senac.sp.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordService {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean verificarSenha(String senhaPlana, String senhaCriptografada) {
        return passwordEncoder.matches(senhaPlana, senhaCriptografada);
    }

    public String criptografar(String senha) {
       return passwordEncoder.encode(senha);
    }
}