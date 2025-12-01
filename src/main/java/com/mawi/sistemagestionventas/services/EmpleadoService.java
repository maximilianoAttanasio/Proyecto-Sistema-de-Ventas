package com.mawi.sistemagestionventas.services;

import com.mawi.sistemagestionventas.dto.TimeResponseDTO;
import com.mawi.sistemagestionventas.interfaces.CRUDInterface;
import com.mawi.sistemagestionventas.models.Empleado;
import com.mawi.sistemagestionventas.repository.EmpleadoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmpleadoService implements CRUDInterface<Empleado, Integer> {

    private static final String NOT_FOUND_MESSAGE = "Empleado no encontrado";

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private FechaService fechaService;

    @Override
    public List<Empleado> findAll() {
        return empleadoRepository.findAll();
    }

    @Override
    public Empleado findById(Integer id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MESSAGE));
    }

    @Override
    public Empleado save(Empleado empleado) {
        if (empleadoRepository.existsByDocumento(empleado.getDocumento())) {
            throw new IllegalStateException("Ya existe un empleado con el documento" + empleado.getDocumento());
        }

        TimeResponseDTO fechaActual = fechaService.obtenerFechaYHoraActual();
        if (fechaActual != null) {
            empleado.setFechaContratacion(
                    LocalDate.of(fechaActual.getYear(), fechaActual.getMonth(), fechaActual.getDay())
            );
        }

        return empleadoRepository.save(empleado);
    }

    @Override
    @Transactional
    public Empleado update(Integer id, Empleado empleadoActualizado) {
        Empleado empleado = findById(id);

        if (empleadoActualizado.getNombre() != null && !empleadoActualizado.getNombre().isEmpty()) {
            empleado.setNombre(empleadoActualizado.getNombre());
        }
        if (empleadoActualizado.getApellido() != null && !empleadoActualizado.getApellido().isEmpty()) {
            empleado.setApellido(empleadoActualizado.getApellido());
        }
        if (empleadoActualizado.getDocumento() != 0) {
            empleado.setDocumento(empleadoActualizado.getDocumento());
        }
        if (empleadoActualizado.getEmail() != null && !empleadoActualizado.getEmail().isEmpty()) {
            empleado.setEmail(empleadoActualizado.getEmail());
        }
        if (empleadoActualizado.getTelefono() != null && !empleadoActualizado.getTelefono().isEmpty()) {
            empleado.setTelefono(empleadoActualizado.getTelefono());
        }
        if (empleadoActualizado.getDireccion() != null && !empleadoActualizado.getDireccion().isEmpty()) {
            empleado.setDireccion(empleadoActualizado.getDireccion());
        }
        return empleadoRepository.save(empleado);
    }

    @Override
    public void deleteById(Integer id) {
        if (!empleadoRepository.existsById(id)) {
            throw new RuntimeException(NOT_FOUND_MESSAGE);
        }
        empleadoRepository.deleteById(id);
    }

}
