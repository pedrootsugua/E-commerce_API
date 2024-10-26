package com.br.senac.sp.repository;

import com.br.senac.sp.model.URLImagensModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface URLImagensRepository extends JpaRepository<URLImagensModel, Long> {
    @Query("SELECT u FROM URLImagensModel u WHERE u.url = :url AND u.produtoId.id = :produtoId")
    URLImagensModel findByUrl(String url, Long produtoId);
    @Query("SELECT u FROM URLImagensModel u WHERE u.padrao = true AND u.produtoId.id = :produtoId")
    URLImagensModel findPadrao(Long produtoId);
}