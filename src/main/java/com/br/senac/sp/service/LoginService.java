package com.br.senac.sp.service;

import com.br.senac.sp.dto.AutenticacaoLoginDTO;
import com.br.senac.sp.dto.CredencialDTO;
import com.br.senac.sp.model.CredencialModel;
import com.br.senac.sp.model.UsuarioModel;
import com.br.senac.sp.repository.CredencialRepository;
import com.br.senac.sp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private CredencialRepository credencialRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private PasswordService passwordService = new PasswordService();

    public ResponseEntity<AutenticacaoLoginDTO> entrar(CredencialDTO credencialDTO) {
        CredencialModel credencial = credencialRepository.buscaPorEmail(credencialDTO.getEmail());
        if (credencial != null) {
            boolean senhaCorreta = passwordService.verificarSenha(credencialDTO.getSenha(), credencial.getSenha());
            if (senhaCorreta) {
                UsuarioModel usuario = usuarioRepository.buscarPorId(credencial.getId());
                return new ResponseEntity<>(new AutenticacaoLoginDTO(true, usuario.getId(), usuario.getNome(), usuario.getGrupo(), usuario.isAtivo()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
