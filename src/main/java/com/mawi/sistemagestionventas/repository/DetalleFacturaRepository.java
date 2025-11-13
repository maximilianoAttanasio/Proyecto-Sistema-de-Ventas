package com.mawi.sistemagestionventas.repository;

import com.mawi.sistemagestionventas.models.DetalleFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, Integer> {
}
