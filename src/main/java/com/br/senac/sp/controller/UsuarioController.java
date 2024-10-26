package com.br.senac.sp.controller;

import com.br.senac.sp.dto.UsuarioCredencialDTO;
import com.br.senac.sp.model.UsuarioModel;
import com.br.senac.sp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioCredencialDTO> criarCliente(@RequestBody UsuarioCredencialDTO dto) throws ParseException {
        return usuarioService.cadastrarUsuario(dto);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioModel>> listarClientes() {
        return usuarioService.listarUsuarios();
    }

    @PatchMapping("/desativar/{id}")
    public ResponseEntity<Void> desativarUsuario(@PathVariable("id") Long id) throws Exception {
        return usuarioService.desativarUsuario(id);
    }

    @PatchMapping("/ativar/{id}")
    public ResponseEntity<Void> ativarUsuario(@PathVariable("id") Long id) throws Exception {
        return usuarioService.ativarUsuario(id);
    }

    @GetMapping("/consultar/{id}")
    public ResponseEntity<UsuarioModel> buscarUsuarioPorId(@PathVariable Long id) {
        return usuarioService.buscarUsuarioPorId(id);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<UsuarioModel> alterarUsuario(@PathVariable Long id, @RequestBody UsuarioCredencialDTO dto) {
        return usuarioService.alterarUsuario(id, dto);
    }
}
