package com.br.senac.sp.controller;

import com.br.senac.sp.dto.AlterarClienteDTO;
import com.br.senac.sp.dto.CadastroClienteDTO;
import com.br.senac.sp.model.EnderecoModel;
import com.br.senac.sp.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarCliente_ReturnsResponseEntityWithCadastroClienteDTO() throws ParseException {
        CadastroClienteDTO requestDTO = new CadastroClienteDTO();
        CadastroClienteDTO responseDTO = new CadastroClienteDTO();
        when(clienteService.cadastrarCliente(any(CadastroClienteDTO.class))).thenReturn(ResponseEntity.ok(responseDTO));

        ResponseEntity<CadastroClienteDTO> response = clienteController.criarCliente(requestDTO);

        assertEquals(ResponseEntity.ok(responseDTO), response);
    }

    @Test
    void criarCliente_ThrowsParseException() throws ParseException {
        CadastroClienteDTO requestDTO = new CadastroClienteDTO();
        when(clienteService.cadastrarCliente(any(CadastroClienteDTO.class))).thenThrow(new ParseException("Parse exception", 0));

        try {
            clienteController.criarCliente(requestDTO);
        } catch (ParseException e) {
            assertEquals("Parse exception", e.getMessage());
        }
    }

    @Test
    void alterarCliente_ReturnsResponseEntityWithAlterarClienteDTO() throws ParseException {
        AlterarClienteDTO requestDTO = new AlterarClienteDTO();
        AlterarClienteDTO responseDTO = new AlterarClienteDTO();
        when(clienteService.alterarCliente(any(AlterarClienteDTO.class))).thenReturn(ResponseEntity.ok(responseDTO));

        ResponseEntity<AlterarClienteDTO> response = clienteController.alterarCliente(requestDTO);

        assertEquals(ResponseEntity.ok(responseDTO), response);
    }

    @Test
    void alterarCliente_ThrowsParseException() throws ParseException {
        AlterarClienteDTO requestDTO = new AlterarClienteDTO();
        when(clienteService.alterarCliente(any(AlterarClienteDTO.class))).thenThrow(new ParseException("Parse exception", 0));

        try {
            clienteController.alterarCliente(requestDTO);
        } catch (ParseException e) {
            assertEquals("Parse exception", e.getMessage());
        }
    }

    @Test
    void buscarUsuarioPorId_ReturnsResponseEntityWithCadastroClienteDTO() {
        Long id = 1L;
        CadastroClienteDTO responseDTO = new CadastroClienteDTO();
        when(clienteService.buscarClientePorId(id)).thenReturn(ResponseEntity.ok(responseDTO));

        ResponseEntity<CadastroClienteDTO> response = clienteController.buscarUsuarioPorId(id);

        assertEquals(ResponseEntity.ok(responseDTO), response);
    }

    @Test
    void consultarEnderecoEntrega_ReturnsResponseEntityWithEnderecoModel() {
        Long id = 1L;
        EnderecoModel responseModel = new EnderecoModel();
        when(clienteService.buscarEnderecoEntrega(id)).thenReturn(ResponseEntity.ok(responseModel));

        ResponseEntity<EnderecoModel> response = clienteController.consultarEnderecoEntrega(id);

        assertEquals(ResponseEntity.ok(responseModel), response);
    }
}