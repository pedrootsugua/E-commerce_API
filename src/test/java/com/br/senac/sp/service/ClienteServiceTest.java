package com.br.senac.sp.service;

import com.br.senac.sp.dto.AlterarClienteDTO;
import com.br.senac.sp.dto.CadastroClienteDTO;
import com.br.senac.sp.dto.EnderecoDTO;
import com.br.senac.sp.model.ClienteModel;
import com.br.senac.sp.model.CredencialClienteModel;
import com.br.senac.sp.model.EnderecoModel;
import com.br.senac.sp.repository.ClienteRepository;
import com.br.senac.sp.repository.CredencialClienteRepository;
import com.br.senac.sp.repository.EnderecoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private CredencialClienteRepository credencialClienteRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private PasswordService passwordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cadastrarCliente_ReturnsCreatedResponse() throws ParseException {
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setCep("12345678");

        CadastroClienteDTO dto = new CadastroClienteDTO();
        dto.setEmail("test@example.com");
        dto.setSenha("password");
        dto.setEnderecos(Collections.singletonList(enderecoDTO));
        dto.setDtNascimento(new Date());

        when(credencialClienteRepository.buscaPorEmail(dto.getEmail())).thenReturn(null);
        when(clienteRepository.save(any(ClienteModel.class))).thenReturn(new ClienteModel(dto));
        when(passwordService.criptografar(dto.getSenha())).thenReturn("encryptedPassword");

        ResponseEntity<CadastroClienteDTO> response = clienteService.cadastrarCliente(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void cadastrarCliente_ReturnsConflictWhenEmailExists() throws ParseException {
        CadastroClienteDTO dto = new CadastroClienteDTO();
        dto.setEmail("test@example.com");
        when(credencialClienteRepository.buscaPorEmail(dto.getEmail())).thenReturn(new CredencialClienteModel());

        ResponseEntity<CadastroClienteDTO> response = clienteService.cadastrarCliente(dto);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void alterarCliente_ChangingAlreadyExistedSite_ReturnsUpdatedClient() throws ParseException {
        EnderecoDTO enderecoDTO1 = new EnderecoDTO();
        enderecoDTO1.setCep("12345679");
        enderecoDTO1.setEntrega(false);
        EnderecoDTO enderecoDTO2 = new EnderecoDTO();
        enderecoDTO2.setCep("12345677");
        enderecoDTO2.setEntrega(false);

        List<EnderecoDTO> enderecos = List.of(enderecoDTO1, enderecoDTO2);

        AlterarClienteDTO dto = new AlterarClienteDTO();
        dto.setIdCliente(1L);
        dto.setEmail("test@example.com");
        dto.setSenha("newPassword");
        dto.setDtNascimento(new Date());
        dto.setIdEnderecoPadrao(1L);
        dto.setEnderecos(enderecos);

        EnderecoModel enderecoModel = new EnderecoModel();
        enderecoModel.setCep("12345678");

        CredencialClienteModel credencialClienteModel = new CredencialClienteModel();
        credencialClienteModel.setEmail("teste@teste.com");

        ClienteModel clienteModel = new ClienteModel();
        clienteModel.setId(1L);
        clienteModel.setCredencialClienteId(credencialClienteModel);
        clienteModel.setEnderecos(Collections.singletonList(enderecoModel));
        clienteModel.setDtNascimento(new Date());
        when(clienteRepository.findById(dto.getIdCliente())).thenReturn(Optional.of(clienteModel));
        when(credencialClienteRepository.buscaPorEmail(dto.getEmail())).thenReturn(credencialClienteModel);
        when(passwordService.criptografar(dto.getSenha())).thenReturn("encryptedPassword");
        when(enderecoRepository.findByEntregaAndClienteId(true, clienteModel)).thenReturn(enderecoModel);
        when(enderecoRepository.findById(dto.getIdEnderecoPadrao())).thenReturn(Optional.of(enderecoModel));

        ResponseEntity<AlterarClienteDTO> response = clienteService.alterarCliente(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void alterarCliente_ReturnsNotFoundWhenClientNotFound() throws ParseException {
        AlterarClienteDTO dto = new AlterarClienteDTO();
        dto.setIdCliente(1L);
        dto.setEmail("test@example.com");

        assertThrows(RuntimeException.class, () -> clienteService.alterarCliente(dto));
    }

    @Test
    void buscarClientePorId_ReturnsClientWhenFound() throws ParseException {
        Long id = 1L;
        EnderecoModel enderecoModel = new EnderecoModel();
        enderecoModel.setCep("12345678");

        CredencialClienteModel credencialClienteModel = new CredencialClienteModel();
        credencialClienteModel.setEmail("teste@teste.com");

        ClienteModel clienteModel = new ClienteModel();
        clienteModel.setId(id);
        clienteModel.setCredencialClienteId(credencialClienteModel);
        clienteModel.setEnderecos(Collections.singletonList(enderecoModel));
        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteModel));

        ResponseEntity<CadastroClienteDTO> response = clienteService.buscarClientePorId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void buscarClientePorId_ReturnsNotFoundWhenClientNotFound() {
        Long id = 1L;
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        try {
            clienteService.buscarClientePorId(id);
        } catch (RuntimeException e) {
            assertEquals("Cliente não encontrado", e.getMessage());
        }
    }

    @Test
    void buscarEnderecoEntrega_ReturnsEnderecoWhenFound() {
        Long idCliente = 1L;
        ClienteModel clienteModel = new ClienteModel();
        EnderecoModel enderecoModel = new EnderecoModel();
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(clienteModel));
        when(enderecoRepository.findByEntregaAndClienteId(true, clienteModel)).thenReturn(enderecoModel);

        ResponseEntity<EnderecoModel> response = clienteService.buscarEnderecoEntrega(idCliente);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void buscarEnderecoEntrega_ReturnsNotFoundWhenEnderecoNotFound() {
        Long idCliente = 1L;
        ClienteModel clienteModel = new ClienteModel();
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(clienteModel));
        when(enderecoRepository.findByEntregaAndClienteId(true, clienteModel)).thenReturn(null);

        ResponseEntity<EnderecoModel> response = clienteService.buscarEnderecoEntrega(idCliente);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}