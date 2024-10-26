package com.br.senac.sp.repository;

import com.br.senac.sp.model.ClienteModel;
import com.br.senac.sp.model.EnderecoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoModel, Long> {
    @Query("SELECT e FROM EnderecoModel e WHERE e.cep = ?1")
    EnderecoModel buscaPorCep(String cep);

    EnderecoModel findByEntregaAndClienteId(boolean entrega, ClienteModel clienteId);
}
