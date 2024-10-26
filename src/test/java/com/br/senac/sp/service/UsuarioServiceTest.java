package com.br.senac.sp.service;

import com.br.senac.sp.dto.UsuarioCredencialDTO;
import com.br.senac.sp.model.CredencialModel;
import com.br.senac.sp.model.UsuarioModel;
import com.br.senac.sp.repository.CredencialRepository;
import com.br.senac.sp.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CredencialRepository credencialRepository;

    @Mock
    private PasswordService passwordService;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastrarUsuario_Success() throws ParseException {
        UsuarioCredencialDTO dto = new UsuarioCredencialDTO();
        dto.setEmail("test@example.com");
        dto.setSenha("password");

        when(credencialRepository.buscaPorEmail(anyString())).thenReturn(null);
        when(usuarioRepository.save(any(UsuarioModel.class))).thenReturn(new UsuarioModel(dto));
        when(passwordService.criptografar(anyString())).thenReturn("hashedPassword");
        when(credencialRepository.save(any(CredencialModel.class))).thenReturn(new CredencialModel());

        ResponseEntity<UsuarioCredencialDTO> response = usuarioService.cadastrarUsuario(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("", response.getBody().getSenha());
    }

    @Test
    public void testCadastrarUsuario_Conflict() throws ParseException {
        UsuarioCredencialDTO dto = new UsuarioCredencialDTO();
        dto.setEmail("test@example.com");

        when(credencialRepository.buscaPorEmail(anyString())).thenReturn(new CredencialModel());

        ResponseEntity<UsuarioCredencialDTO> response = usuarioService.cadastrarUsuario(dto);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testListarUsuarios() {
        UsuarioModel usuario1 = new UsuarioModel();
        UsuarioModel usuario2 = new UsuarioModel();
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario1, usuario2));

        ResponseEntity<List<UsuarioModel>> response = usuarioService.listarUsuarios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testDesativarUsuario_Success() throws Exception {
        UsuarioModel usuario = new UsuarioModel();
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));

        usuarioService.desativarUsuario(1L);

        assertFalse(usuario.isAtivo());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    public void testDesativarUsuario_NotFound() {
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            usuarioService.desativarUsuario(1L);
        });

        assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @Test
    public void testAtivarUsuario_Success() throws Exception {
        UsuarioModel usuario = new UsuarioModel();
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));

        usuarioService.ativarUsuario(1L);

        assertTrue(usuario.isAtivo());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    public void testAtivarUsuario_NotFound() {
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            usuarioService.ativarUsuario(1L);
        });

        assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @Test
    public void testBuscarUsuarioPorId_Success() {
        UsuarioModel usuario = new UsuarioModel();
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));

        ResponseEntity<UsuarioModel> response = usuarioService.buscarUsuarioPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testBuscarUsuarioPorId_NotFound() {
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.buscarUsuarioPorId(1L);
        });

        assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @Test
    public void testAlterarUsuario_Success() {
        UsuarioModel usuario = new UsuarioModel();
        CredencialModel credencial = new CredencialModel();
        UsuarioCredencialDTO dto = new UsuarioCredencialDTO();
        dto.setEmail("test@example.com");
        dto.setSenha("newPassword");

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
        when(credencialRepository.buscaPorEmail(anyString())).thenReturn(credencial);
        when(passwordService.criptografar(anyString())).thenReturn("hashedPassword");

        ResponseEntity<UsuarioModel> response = usuarioService.alterarUsuario(1L, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(usuarioRepository, times(1)).save(usuario);
        verify(credencialRepository, times(1)).save(credencial);
    }

    @Test
    public void testAlterarUsuario_NotFound() {
        UsuarioCredencialDTO dto = new UsuarioCredencialDTO();
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.alterarUsuario(1L, dto);
        });

        assertEquals("Usuário não encontrado", exception.getMessage());
    }
}