package com.mawi.sistemagestionventas.services;

import com.mawi.sistemagestionventas.interfaces.CRUDInterface;
import com.mawi.sistemagestionventas.models.Producto;
import com.mawi.sistemagestionventas.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService implements CRUDInterface<Producto, Integer> {
    private final String message = "Producto no encontrado";

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Override
    public Producto findById(Integer id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(message));
    }

    @Override
    public Producto save(Producto nuevoProducto) {
        if (productoRepository.existsByNombre(nuevoProducto.getNombre())) {
            throw new IllegalStateException("Ya existe un producto con el nombre " + nuevoProducto.getNombre());
        }
        return productoRepository.save(nuevoProducto);
    }

    @Override
    @Transactional
    public Producto update(Integer id, Producto productoActualizado) {
        Producto producto = findById(id);

        if (productoActualizado.getNombre() != null && !productoActualizado.getNombre().isEmpty()) {
            producto.setNombre(productoActualizado.getNombre());
        }
        if (productoActualizado.getPrecio() != 0) {
            producto.setPrecio(productoActualizado.getPrecio());
        }
        if (productoActualizado.getStock() != 0) {
            producto.setStock(productoActualizado.getStock());
        }
        return productoRepository.save(producto);
    }

    @Override
    public void deleteById(Integer id) {
        if (!productoRepository.existsById(id)) {
            throw new IllegalArgumentException(message);
        }
        productoRepository.deleteById(id);
    }

}
