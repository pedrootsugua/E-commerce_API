package com.br.senac.sp.repository;

import com.br.senac.sp.model.ClienteModel;
import com.br.senac.sp.model.CredencialClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CredencialClienteRepository extends JpaRepository<CredencialClienteModel, Long> {
    @Query("SELECT u FROM CredencialClienteModel u WHERE u.email = ?1")
    CredencialClienteModel buscaPorEmail(String email);

    CredencialClienteModel findByClienteId(ClienteModel clienteModel);
}
