package com.mawi.sistemagestionventas.services;

import com.mawi.sistemagestionventas.interfaces.CRUDInterface;
import com.mawi.sistemagestionventas.models.Cliente;
import com.mawi.sistemagestionventas.models.Empleado;
import com.mawi.sistemagestionventas.models.Pedido;
import com.mawi.sistemagestionventas.repository.ClienteRepository;
import com.mawi.sistemagestionventas.repository.EmpleadoRepository;
import com.mawi.sistemagestionventas.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService implements CRUDInterface<Pedido, Integer> {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private FechaService fechaService;

    @Override
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido findById(Integer id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));
    }

    @Override
    @Transactional
    public Pedido save(Pedido nuevoPedido) {
        if (nuevoPedido.getCliente() == null || nuevoPedido.getCliente().getIdPersona() == 0) {
            throw new IllegalArgumentException("El pedido debe tener un cliente asociado.");
        }
        if (nuevoPedido.getEmpleado() == null || nuevoPedido.getEmpleado().getIdPersona() == 0) {
            throw new IllegalArgumentException("El pedido debe tener un empleado asignado.");
        }

        Cliente cliente = clienteRepository.findById(nuevoPedido.getCliente().getIdPersona())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado."));

        Empleado empleado = empleadoRepository.findById(nuevoPedido.getEmpleado().getIdPersona())
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado."));

        nuevoPedido.setCliente(cliente);
        nuevoPedido.setEmpleado(empleado);

        var fechaActual = fechaService.obtenerFechaYHoraActual();
        if (fechaActual != null) {
            nuevoPedido.setFechaPedido(
                    LocalDateTime.of(fechaActual.getYear(),
                            fechaActual.getMonth(),
                            fechaActual.getDay(),
                            fechaActual.getHour(),
                            fechaActual.getMinute())
            );
        }

        return pedidoRepository.save(nuevoPedido);
    }

    @Override
    public Pedido update(Integer id, Pedido nuevoPedido) {
        throw new UnsupportedOperationException("No se permite modificar un pedido.");
    }

    @Override
    public void deleteById(Integer id) {
        throw new UnsupportedOperationException("No se permite eliminar un pedido.");
    }

}
