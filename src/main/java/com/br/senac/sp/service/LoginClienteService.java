package com.br.senac.sp.service;

import com.br.senac.sp.dto.AutenticacaoLoginDTO;
import com.br.senac.sp.dto.CredencialDTO;
import com.br.senac.sp.model.ClienteModel;
import com.br.senac.sp.model.CredencialClienteModel;
import com.br.senac.sp.repository.ClienteRepository;
import com.br.senac.sp.repository.CredencialClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginClienteService {

    @Autowired
    private CredencialClienteRepository credencialClienteRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordService passwordService;

    public ResponseEntity<AutenticacaoLoginDTO> entrarCliente (CredencialDTO credencialDTO) {
        CredencialClienteModel credencial = credencialClienteRepository.buscaPorEmail(credencialDTO.getEmail());
        if (credencial != null) {
            boolean senhaCorreta = passwordService.verificarSenha(credencialDTO.getSenha(), credencial.getSenha());
            if (senhaCorreta) {
                ClienteModel cliente = clienteRepository.buscarPorId(credencial.getId());
                return new ResponseEntity<>(new AutenticacaoLoginDTO(true, cliente.getId(), cliente.getNome()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
