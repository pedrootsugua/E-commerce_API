package com.br.senac.sp.repository;

import com.br.senac.sp.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
    @Query("SELECT u FROM UsuarioModel u WHERE u.id = ?1")
    UsuarioModel buscarPorId(Long id);
}
