package com.mawi.sistemagestionventas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenApi() {
        return new OpenAPI().info(new Info()
                        .title("Sistema Gestion Ventas")
                        .version("1.0")
                        .description("API REST para la gestión de facturas, pedidos, clientes, empleados y productos. Permite crear facturas y sus detalles, consultar información completa o parcial, y administrar el sistema de ventas con persistencia en MySQL.")
                        .contact(new Contact()
                                .name("Attanasio Maximiliano")
                                .email("maxiev1331@gmail.com"))
                        .license(null))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor Local"),
                        new Server()
                                .url("https://proyecto-sistema-de-ventas.onrender.com")
                                .description("Servidor en Render")
                ));
    }
}
