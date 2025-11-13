package com.mawi.sistemagestionventas.repository;

import com.mawi.sistemagestionventas.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    boolean existsByDocumento(int documento);

    boolean existsByEmail(String email);
}
