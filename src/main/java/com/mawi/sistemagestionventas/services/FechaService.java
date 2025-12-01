package com.mawi.sistemagestionventas.services;

import com.mawi.sistemagestionventas.dto.TimeResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FechaService {

    @Autowired
    private RestTemplate restTemplate;

    public TimeResponseDTO obtenerFechaYHoraActual() {
        try {
            final String URL = "https://timeapi.io/api/Time/current/zone?timeZone=America/Argentina/Buenos_Aires";
            return restTemplate.getForObject(URL, TimeResponseDTO.class);
        } catch (Exception e) {
            System.err.println("Error, no se puedo conectar a la API " + e.getMessage());
            return null;
        }
    }
}
