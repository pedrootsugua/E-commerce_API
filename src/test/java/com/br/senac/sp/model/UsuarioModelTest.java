package com.br.senac.sp.model;

import com.br.senac.sp.dto.UsuarioCredencialDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UsuarioModelTest {

    @Test
    public void testUsuarioModel() {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(1L);
        usuario.setNome("Test User");

        assertEquals(1L, usuario.getId());
        assertEquals("Test User", usuario.getNome());
    }

    @Test
    public void testUsuarioModelConstructor() {
        UsuarioCredencialDTO dto = new UsuarioCredencialDTO();
        dto.setNome("Test User");
        dto.setCpf("12345678900");
        dto.setGrupo("Admin");

        UsuarioModel usuario = new UsuarioModel(dto);

        assertEquals("Test User", usuario.getNome());
        assertEquals("12345678900", usuario.getCpf());
        assertEquals("Admin", usuario.getGrupo());
        assertTrue(usuario.isAtivo());
    }
}