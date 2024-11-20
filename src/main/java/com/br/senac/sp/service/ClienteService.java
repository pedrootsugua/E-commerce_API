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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CredencialClienteRepository credencialClienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    private PasswordService passwordService = new PasswordService();

    public ResponseEntity<CadastroClienteDTO> cadastrarCliente(CadastroClienteDTO dto) throws ParseException {
        try {
            if (dto.getDtNascimento() != null) {
                dto.setDtNascimento(ajustarData(dto.getDtNascimento()));
            }
            CredencialClienteModel usuarioExistente = credencialClienteRepository.buscaPorEmail(dto.getEmail());

            if (usuarioExistente != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            ClienteModel cliente = new ClienteModel(dto);
            ClienteModel clienteSalvo = clienteRepository.save(cliente);
            for (EnderecoDTO endereco : dto.getEnderecos()) {
                EnderecoModel enderecoModel = new EnderecoModel(endereco);
                enderecoModel.setClienteId(clienteSalvo);
                enderecoRepository.save(enderecoModel);
            }


            CredencialClienteModel credenciaClientelModel = new CredencialClienteModel();
            credenciaClientelModel.setEmail(dto.getEmail());
            credenciaClientelModel.setSenha(passwordService.criptografar(dto.getSenha()));
            credenciaClientelModel.setClienteId(clienteSalvo);
            credencialClienteRepository.save(credenciaClientelModel);

            dto.setSenha("");
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Date ajustarData(Date data) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        String dataFormatada = formatter.format(data);

        return formatter.parse(dataFormatada);
    }

    public ResponseEntity<AlterarClienteDTO> alterarCliente(AlterarClienteDTO dto) throws ParseException {
        ClienteModel clienteSalvo = clienteRepository.findById(dto.getIdCliente()).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado!"));
        CredencialClienteModel credencialClienteSalva = credencialClienteRepository.buscaPorEmail(dto.getEmail());
        if (clienteSalvo == null || credencialClienteSalva == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        clienteSalvo.setNome(dto.getNome());
        clienteSalvo.setGenero(dto.getGenero());
        clienteSalvo.setDtNascimento(ajustarData(dto.getDtNascimento()));
        if (dto.getSenha() != null && !dto.getSenha().trim().isEmpty()) {
            credencialClienteSalva.setSenha(passwordService.criptografar(dto.getSenha()));
        }
        boolean enderecoEntregaTrocado = false;
        EnderecoModel antigoEnderecoPadrao;
        if (dto.getIdEnderecoPadrao() != null) {
            antigoEnderecoPadrao = enderecoRepository.findByEntregaAndClienteId(true, clienteSalvo);
            EnderecoModel novoEnderecoPadrao = enderecoRepository.findById(dto.getIdEnderecoPadrao()).orElseThrow(
                    () -> new RuntimeException("Endereço não encontrado!"));
            if (novoEnderecoPadrao != null && antigoEnderecoPadrao != null) {
                antigoEnderecoPadrao.setEntrega(false);
                novoEnderecoPadrao.setEntrega(true);
                enderecoRepository.save(antigoEnderecoPadrao);
                enderecoRepository.save(novoEnderecoPadrao);
                enderecoEntregaTrocado = true;
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        if (dto.getEnderecos() != null) {
            for (EnderecoDTO endereco : dto.getEnderecos()) {
                if (endereco != null) {
                    if (endereco.getEntrega()){
                        if (enderecoEntregaTrocado){
                            return ResponseEntity.status(HttpStatus.CONFLICT).build();
                        }
                        antigoEnderecoPadrao = enderecoRepository.findByEntregaAndClienteId(true, clienteSalvo);
                        antigoEnderecoPadrao.setEntrega(false);
                        enderecoRepository.save(antigoEnderecoPadrao);
                        enderecoEntregaTrocado = true;
                    }
                    EnderecoModel enderecoModel = new EnderecoModel(endereco);
                    enderecoModel.setClienteId(clienteSalvo);
                    enderecoRepository.save(enderecoModel);
                }
            }
        }
        clienteRepository.save(clienteSalvo);
        credencialClienteRepository.save(credencialClienteSalva);
        ClienteModel clienteAlterado = clienteRepository.findById(dto.getIdCliente()).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado!"));
        AlterarClienteDTO clienteAlteradoDTO = new AlterarClienteDTO(clienteAlterado);
        clienteAlteradoDTO.setSenha("");
        return new ResponseEntity<>(clienteAlteradoDTO, HttpStatus.OK);
    }

    public ResponseEntity<CadastroClienteDTO> buscarClientePorId(Long id) {
        ClienteModel usuario = clienteRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado"));
        CadastroClienteDTO dto = new CadastroClienteDTO(usuario);
        dto.setSenha("");
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    public ResponseEntity<EnderecoModel> buscarEnderecoEntrega(Long idCliente) {
        ClienteModel cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));

        EnderecoModel enderecoPadrao = enderecoRepository.findByEntregaAndClienteId(true, cliente);
        if (enderecoPadrao == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(enderecoPadrao, HttpStatus.OK);
    }

}