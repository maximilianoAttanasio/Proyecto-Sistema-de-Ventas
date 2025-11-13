package com.mawi.sistemagestionventas.services;

import com.mawi.sistemagestionventas.interfaces.CRUDInterface;
import com.mawi.sistemagestionventas.models.Cliente;
import com.mawi.sistemagestionventas.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService implements CRUDInterface<Cliente, Integer> {

    private static final String NOT_FOUND_MESSAGE = "Cliente no encontrado";

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente findById(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(NOT_FOUND_MESSAGE));
    }

    @Override
    public Cliente save(Cliente cliente) {
        if (clienteRepository.existsByDocumento(cliente.getDocumento())) {
            throw new IllegalStateException("Ya existe un cliente con el documento " + cliente.getDocumento());
        }
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new IllegalStateException("Ya existe un cliente con el email " + cliente.getEmail());
        }
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional
    public Cliente update(Integer id, Cliente clienteActualizado) {
        Cliente cliente = findById(id);

        if (clienteActualizado.getNombre() != null && !clienteActualizado.getNombre().isEmpty()) {
            cliente.setNombre(clienteActualizado.getNombre());
        }
        if (clienteActualizado.getApellido() != null && !clienteActualizado.getApellido().isEmpty()) {
            cliente.setApellido(clienteActualizado.getApellido());
        }
        if (clienteActualizado.getDocumento() != 0) {
            cliente.setDocumento(clienteActualizado.getDocumento());
        }
        if (clienteActualizado.getEmail() != null && !clienteActualizado.getEmail().isEmpty()) {
            cliente.setEmail(clienteActualizado.getEmail());
        }
        if (clienteActualizado.getTelefono() != null && !clienteActualizado.getTelefono().isEmpty()) {
            cliente.setTelefono(clienteActualizado.getTelefono());
        }
        if (clienteActualizado.getDireccion() != null && !clienteActualizado.getDireccion().isEmpty()) {
            cliente.setDireccion(clienteActualizado.getDireccion());
        }
        return clienteRepository.save(cliente);
    }

    @Override
    public void deleteById(Integer id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException(NOT_FOUND_MESSAGE);
        }
        clienteRepository.deleteById(id);
    }

}
