package com.mawi.sistemagestionventas.services;

import com.mawi.sistemagestionventas.dto.CrearDetalleDTO;
import com.mawi.sistemagestionventas.interfaces.CRUDInterface;
import com.mawi.sistemagestionventas.models.DetalleFactura;
import com.mawi.sistemagestionventas.models.Factura;
import com.mawi.sistemagestionventas.models.Producto;
import com.mawi.sistemagestionventas.repository.DetalleFacturaRepository;
import com.mawi.sistemagestionventas.repository.FacturaRepository;
import com.mawi.sistemagestionventas.repository.ProductoRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleFacturaService implements CRUDInterface<DetalleFactura, Integer> {

    @Autowired
    private DetalleFacturaRepository detalleRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<DetalleFactura> findAll() {
        return detalleRepository.findAll();
    }

    @Override
    public DetalleFactura findById(Integer id) {
        return detalleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Detalle no encontrado"));
    }

    @Override
    @Transactional
    public DetalleFactura save(DetalleFactura detalle) {
        if (detalle.getFactura() == null || detalle.getFactura().getIdFactura() == 0) {
            throw new IllegalArgumentException("Debe indicar la factura");
        }

        if (detalle.getProducto() == null || detalle.getProducto().getIdProducto() == 0) {
            throw new IllegalArgumentException("Debe indicar el producto");
        }

        Factura factura = facturaRepository.findById(detalle.getFactura().getIdFactura())
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));

        Producto producto = productoRepository.findById(detalle.getProducto().getIdProducto())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        if (producto.getStock() < detalle.getCantidad()) {
            throw new IllegalStateException("Stock insuficiente para el producto");
        }

        producto.setStock(producto.getStock() - detalle.getCantidad());

        detalle.setFactura(factura);
        detalle.setProducto(producto);

        productoRepository.save(producto);
        return detalleRepository.save(detalle);
    }

    @Override
    public DetalleFactura update(Integer id, DetalleFactura entity) {
        throw new UnsupportedOperationException("No se permite modificar un detalle");
    }

    @Override
    public void deleteById(Integer id) {
        throw new UnsupportedOperationException("No se permite eliminar un detalle");
    }

    @Transactional
    public DetalleFactura agregarDetalle(CrearDetalleDTO dto) {
        Factura factura = facturaRepository.findById(dto.getIdFactura())
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));
        Producto producto = productoRepository.findById(dto.getIdProducto())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        if(producto.getStock() < dto.getCantidad()) {
            throw new IllegalStateException("Stock insuficiente");
        }

        producto.setStock(producto.getStock() - dto.getCantidad());

        DetalleFactura detalle = new DetalleFactura();
        detalle.setFactura(factura);
        detalle.setProducto(producto);
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(producto.getPrecio());

        productoRepository.save(producto);
        return detalleRepository.save(detalle);
    }

}
