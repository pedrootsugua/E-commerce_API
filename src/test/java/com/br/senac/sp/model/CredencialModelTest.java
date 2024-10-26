package com.br.senac.sp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CredencialModelTest {

    @Test
    public void testCredencialModel() {
        CredencialModel credencial = new CredencialModel();
        credencial.setId(1L);
        credencial.setEmail("test@example.com");
        credencial.setSenha("hashedPassword");

        assertEquals(1L, credencial.getId());
        assertEquals("test@example.com", credencial.getEmail());
        assertEquals("hashedPassword", credencial.getSenha());
    }
}