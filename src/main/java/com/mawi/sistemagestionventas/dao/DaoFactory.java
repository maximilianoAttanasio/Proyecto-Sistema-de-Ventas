package com.mawi.sistemagestionventas.dao;

import com.mawi.sistemagestionventas.models.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;


@Repository
public class DaoFactory {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void guardar(Object entidad) {
        em.persist(entidad);
    }

    @Transactional
    public void actualizar(Object entidad) {
        em.merge(entidad);
    }

    @Transactional
    public void eliminar(Object entidad) {
        if (em.contains(entidad)) {
            em.remove(entidad);
        } else {
            em.remove(em.merge(entidad));
        }
    }

    public Pedido buscarPedidoPorId(int id) throws Exception {
        try {
            return em.find(Pedido.class, id);
        } catch (Exception e) {
            throw new Exception("No se encontró el pedido con ID " + id);
        }
    }

    public Factura buscarFacturaPorId(int id) throws Exception {
        try {
            return em.find(Factura.class, id);
        } catch (Exception e) {
            throw new Exception("No se encontró la factura con ID " + id);
        }
    }

    @Transactional
    public void asignarFacturaAPedido(int idPedido, Factura factura) throws Exception {
        Pedido pedido = buscarPedidoPorId(idPedido);
        if (pedido == null) {
            throw new Exception("No se encontró el pedido con ID " + idPedido);
        }
        factura.setPedido(pedido);
        em.persist(factura);
    }

    @Transactional
    public void agregarDetalleAFactura(int idFactura, DetalleFactura detalle) throws Exception {
        Factura factura = buscarFacturaPorId(idFactura);
        if (factura == null) {
            throw new Exception("No se encontró la factura con ID " + idFactura);
        }
        detalle.setFactura(factura);
        em.persist(detalle);
    }
}
