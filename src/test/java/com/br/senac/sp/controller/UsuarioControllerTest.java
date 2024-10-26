package com.br.senac.sp.controller;

import com.br.senac.sp.dto.UsuarioCredencialDTO;
import com.br.senac.sp.model.UsuarioModel;
import com.br.senac.sp.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCriarCliente_Success() throws ParseException {
        UsuarioCredencialDTO dto = new UsuarioCredencialDTO();
        dto.setEmail("test@example.com");
        dto.setSenha("password");

        when(usuarioService.cadastrarUsuario(any(UsuarioCredencialDTO.class))).thenReturn(new ResponseEntity<>(dto, HttpStatus.CREATED));

        ResponseEntity<UsuarioCredencialDTO> response = usuarioController.criarCliente(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("test@example.com", response.getBody().getEmail());
    }

    @Test
    public void testListarClientes() {
        UsuarioModel usuario1 = new UsuarioModel();
        UsuarioModel usuario2 = new UsuarioModel();
        when(usuarioService.listarUsuarios()).thenReturn(new ResponseEntity<>(List.of(usuario1, usuario2), HttpStatus.OK));

        ResponseEntity<List<UsuarioModel>> response = usuarioController.listarClientes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testDesativarUsuario_Success() throws Exception {
        when(usuarioService.desativarUsuario(anyLong())).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<Void> response = usuarioController.desativarUsuario(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testAtivarUsuario_Success() throws Exception {
        when(usuarioService.ativarUsuario(anyLong())).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<Void> response = usuarioController.ativarUsuario(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testBuscarUsuarioPorId_Success() {
        UsuarioModel usuario = new UsuarioModel();
        when(usuarioService.buscarUsuarioPorId(anyLong())).thenReturn(new ResponseEntity<>(usuario, HttpStatus.OK));

        ResponseEntity<UsuarioModel> response = usuarioController.buscarUsuarioPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testAlterarUsuario_Success() {
        UsuarioModel usuario = new UsuarioModel();
        UsuarioCredencialDTO dto = new UsuarioCredencialDTO();
        dto.setEmail("test@example.com");
        dto.setSenha("newPassword");

        when(usuarioService.alterarUsuario(anyLong(), any(UsuarioCredencialDTO.class))).thenReturn(new ResponseEntity<>(usuario, HttpStatus.OK));

        ResponseEntity<UsuarioModel> response = usuarioController.alterarUsuario(1L, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}