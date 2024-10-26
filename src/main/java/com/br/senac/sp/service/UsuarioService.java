package com.br.senac.sp.service;

import com.br.senac.sp.dto.UsuarioCredencialDTO;
import com.br.senac.sp.model.CredencialModel;
import com.br.senac.sp.model.UsuarioModel;
import com.br.senac.sp.repository.CredencialRepository;
import com.br.senac.sp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CredencialRepository credencialRepository;

    private PasswordService passwordService = new PasswordService();
    public ResponseEntity<UsuarioCredencialDTO> cadastrarUsuario(UsuarioCredencialDTO dto) throws ParseException {

        try {
            CredencialModel usuarioExistente = credencialRepository.buscaPorEmail(dto.getEmail());

            if(usuarioExistente != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            UsuarioModel usuarioSalvo = usuarioRepository.save(new UsuarioModel(dto));

            CredencialModel credencialModel = new CredencialModel();
            credencialModel.setEmail(dto.getEmail());
            credencialModel.setSenha(passwordService.criptografar(dto.getSenha()));
            credencialModel.setUsuarioId(usuarioSalvo);
            credencialRepository.save(credencialModel);
            dto.setSenha("");
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<List<UsuarioModel>> listarUsuarios() {
        List<UsuarioModel> listaCliente = usuarioRepository.findAll();
        return new ResponseEntity<>(listaCliente, HttpStatus.OK);
    }

    public ResponseEntity<Void> desativarUsuario(Long id) throws Exception {
        UsuarioModel usuario = usuarioRepository.findById(id).orElseThrow(
                () -> new Exception("Usuário não encontrado"));
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> ativarUsuario(Long id) throws Exception {
        UsuarioModel usuario = usuarioRepository.findById(id).orElseThrow(
                () -> new Exception("Usuário não encontrado"));
        usuario.setAtivo(true);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<UsuarioModel> buscarUsuarioPorId(Long id) {
        UsuarioModel usuario = usuarioRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado"));
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    public ResponseEntity<UsuarioModel> alterarUsuario(Long id, UsuarioCredencialDTO dto) {
        UsuarioModel usuarioSalvo = usuarioRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado"));
        CredencialModel credencial = credencialRepository.buscaPorEmail(dto.getEmail());

        usuarioSalvo.setNome(dto.getNome());
        usuarioSalvo.setCpf(dto.getCpf());
        usuarioSalvo.setGrupo(dto.getGrupo());
        if (dto.getSenha() != null && !dto.getSenha().trim().isEmpty()) {
            credencial.setSenha(passwordService.criptografar(dto.getSenha()));
        }


        usuarioRepository.save(usuarioSalvo);
        credencialRepository.save(credencial);
        return new ResponseEntity<>(usuarioSalvo, HttpStatus.OK);
    }
}