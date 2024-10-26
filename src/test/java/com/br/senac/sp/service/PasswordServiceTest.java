package com.br.senac.sp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class PasswordServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PasswordService passwordService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testVerificarSenha_Success() {
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        boolean result = passwordService.verificarSenha(rawPassword, encodedPassword);

        assertTrue(result);
    }

    @Test
    public void testVerificarSenha_Failure() {
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        boolean result = passwordService.verificarSenha(rawPassword, encodedPassword);

        assertTrue(!result);
    }

    @Test
    public void testCriptografar() {
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);

        String result = passwordService.criptografar(rawPassword);

        assertNotNull(result);
        assertTrue(result.equals(encodedPassword));
    }
}