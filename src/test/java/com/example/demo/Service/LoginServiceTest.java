package com.example.demo.Service;

import com.example.demo.DTO.AutenticacaoLoginDTO;
import com.example.demo.DTO.CredencialDTO;
import com.example.demo.Model.CredencialModel;
import com.example.demo.Model.UsuarioModel;
import com.example.demo.Repository.CredencialRepository;
import com.example.demo.Repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class LoginServiceTest {

    @Mock
    private CredencialRepository credencialRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordService passwordService;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEntrar_Success() {
        CredencialDTO credencialDTO = new CredencialDTO();
        credencialDTO.setEmail("test@example.com");
        credencialDTO.setSenha("password");

        CredencialModel credencialModel = new CredencialModel();
        credencialModel.setEmail("test@example.com");
        credencialModel.setSenha("hashedPassword");

        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setId(1L);
        usuarioModel.setNome("Test User");
        usuarioModel.setGrupo("Admin");
        usuarioModel.setAtivo(true);

        when(credencialRepository.buscaPorEmail(anyString())).thenReturn(credencialModel);
        when(passwordService.verificarSenha(anyString(), anyString())).thenReturn(true);
        when(usuarioRepository.buscarPorId(credencialModel.getId())).thenReturn(usuarioModel);

        ResponseEntity<AutenticacaoLoginDTO> response = loginService.entrar(credencialDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().isAutenticado());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Test User", response.getBody().getNome());
        assertEquals("Admin", response.getBody().getGrupo());
        assertEquals(true, response.getBody().isAtivo());
    }

    @Test
    public void testEntrar_Failure() {
        CredencialDTO credencialDTO = new CredencialDTO();
        credencialDTO.setEmail("test@example.com");
        credencialDTO.setSenha("password");

        when(credencialRepository.buscaPorEmail(anyString())).thenReturn(null);

        ResponseEntity<AutenticacaoLoginDTO> response = loginService.entrar(credencialDTO);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}