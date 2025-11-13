package com.mawi.sistemagestionventas.repository;

import com.mawi.sistemagestionventas.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    boolean existsByNombre(String nombre);
}
