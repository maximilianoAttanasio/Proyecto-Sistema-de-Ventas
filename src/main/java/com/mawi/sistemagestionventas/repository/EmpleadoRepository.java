package com.mawi.sistemagestionventas.repository;

import com.mawi.sistemagestionventas.models.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    boolean existsByDocumento(int documento);
}
