package com.br.senac.sp.repository;

import com.br.senac.sp.model.CredencialModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CredencialRepository extends JpaRepository<CredencialModel, Long> {

    @Query("SELECT u FROM CredencialModel u WHERE u.email = ?1")
    CredencialModel buscaPorEmail(String email);

}
