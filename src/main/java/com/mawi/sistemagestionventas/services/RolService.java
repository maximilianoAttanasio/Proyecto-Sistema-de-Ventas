package com.mawi.sistemagestionventas.services;

import com.mawi.sistemagestionventas.dto.RolDTO;
import com.mawi.sistemagestionventas.models.Rol;
import com.mawi.sistemagestionventas.repository.RolRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolService {

    private static final String NOT_FOUND_MESSAGE = "Rol no encontrado";

    @Autowired
    private RolRepository rolRepository;

    public List<RolDTO> obtenerRolesDTO() {
        return rolRepository.findAll()
                .stream()
                .map(rol -> new RolDTO(rol.getIdRol(), rol.getNombreRol()))
                .collect(Collectors.toList());
    }


    public Rol findById(Integer id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MESSAGE));
    }

    public Rol save(Rol rol) {
        if (rol.getNombreRol() == null || rol.getNombreRol().isEmpty()) {
            throw new IllegalStateException("El nombre del rol no puede estar vacío");
        }
        return rolRepository.save(rol);
    }

    @Transactional
    public Rol update(Integer id, Rol rolActualizado) {
        Rol rol = findById(id);

        if (rolActualizado.getNombreRol() != null && !rolActualizado.getNombreRol().isEmpty()) {
            rol.setNombreRol(rolActualizado.getNombreRol());
        }

        return rolRepository.save(rol);
    }
}
