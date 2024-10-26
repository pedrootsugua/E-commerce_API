package com.br.senac.sp.controller;

import com.br.senac.sp.dto.AutenticacaoLoginDTO;
import com.br.senac.sp.dto.CredencialDTO;
import com.br.senac.sp.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class LoginControllerTest {

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAcessarUsuario_Success() throws ParseException {
        CredencialDTO credencialDTO = new CredencialDTO();
        credencialDTO.setEmail("test@example.com");
        credencialDTO.setSenha("password");

        AutenticacaoLoginDTO autenticacaoLoginDTO = new AutenticacaoLoginDTO(true, 1L, "Test User", "Admin", true);

        when(loginService.entrar(any(CredencialDTO.class))).thenReturn(new ResponseEntity<>(autenticacaoLoginDTO, HttpStatus.OK));

        ResponseEntity<AutenticacaoLoginDTO> response = loginController.acessarUsuario(credencialDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(autenticacaoLoginDTO, response.getBody());
    }

    @Test
    public void testAcessarUsuario_Unauthorized() throws ParseException {
        CredencialDTO credencialDTO = new CredencialDTO();
        credencialDTO.setEmail("test@example.com");
        credencialDTO.setSenha("wrongpassword");

        when(loginService.entrar(any(CredencialDTO.class))).thenReturn(new ResponseEntity<>(HttpStatus.FORBIDDEN));

        ResponseEntity<AutenticacaoLoginDTO> response = loginController.acessarUsuario(credencialDTO);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}