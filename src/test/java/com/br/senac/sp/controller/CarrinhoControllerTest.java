package com.br.senac.sp.controller;

import com.br.senac.sp.dto.CarrinhoBuscarNLRequestDTO;
import com.br.senac.sp.dto.CarrinhoBuscarNLResponseDTO;
import com.br.senac.sp.service.CarrinhoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CarrinhoControllerTest {

    @InjectMocks
    private CarrinhoController carrinhoController;

    @Mock
    private CarrinhoService carrinhoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscarProdutosCarrinhoNL_ReturnsResponseEntityWithCarrinhoBuscarNLResponseDTO() throws Exception {
        CarrinhoBuscarNLRequestDTO requestDTO = new CarrinhoBuscarNLRequestDTO();
        CarrinhoBuscarNLResponseDTO responseDTO = new CarrinhoBuscarNLResponseDTO();
        when(carrinhoService.buscarCarrinhoNL(any(CarrinhoBuscarNLRequestDTO.class))).thenReturn(ResponseEntity.ok(responseDTO));

        ResponseEntity<CarrinhoBuscarNLResponseDTO> response = carrinhoController.buscarProdutosCarrinhoNL(requestDTO);

        assertEquals(ResponseEntity.ok(responseDTO), response);
    }

    @Test
    void buscarProdutosCarrinhoNL_ThrowsRuntimeException() {
        CarrinhoBuscarNLRequestDTO requestDTO = new CarrinhoBuscarNLRequestDTO();
        when(carrinhoService.buscarCarrinhoNL(any(CarrinhoBuscarNLRequestDTO.class))).thenThrow(new RuntimeException("Service exception"));

        try {
            carrinhoController.buscarProdutosCarrinhoNL(requestDTO);
        } catch (RuntimeException e) {
            assertEquals("Service exception", e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}