package com.mawi.sistemagestionventas;

import com.mawi.sistemagestionventas.dao.DaoFactory;
import com.mawi.sistemagestionventas.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class SistemaGestionVentasApplication implements CommandLineRunner {

    @Autowired
    private DaoFactory dao;

    public static void main(String[] args) {
        SpringApplication.run(SistemaGestionVentasApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            // Creamos roles
            Rol rolEmpleado = new Rol("Empleado");
            Rol rolCliente = new Rol("Cliente");
            dao.guardar(rolEmpleado);
            dao.guardar(rolCliente);

            // Creamos un empleado
            Empleado emp1 = new Empleado();
            emp1.setNombre("Mauricio");
            emp1.setApellido("Lucero");
            emp1.setDocumento(45678900);
            emp1.setEmail("mauri@correo.com");
            emp1.setTelefono("11223344");
            emp1.setDireccion("Luna 1");
            emp1.setRol(rolEmpleado);
            emp1.setSueldo(350000.0);
            emp1.setFechaContratacion(LocalDate.now());
            dao.guardar(emp1);

            // Creamos clientes
            Cliente cli1 = new Cliente();
            cli1.setNombre("Diego");
            cli1.setApellido("Altamirano");
            cli1.setDocumento(40200300);
            cli1.setEmail("diego@gmail.com");
            cli1.setTelefono("11998877");
            cli1.setDireccion("Jupiter 47");
            cli1.setRol(rolCliente);
            cli1.setFechaRegistro(LocalDate.now());
            dao.guardar(cli1);

            Cliente cli2 = new Cliente();
            cli2.setNombre("Maxi");
            cli2.setApellido("Pintos");
            cli2.setDocumento(40300200);
            cli2.setEmail("maxi@correo.com");
            cli2.setTelefono("11445566");
            cli2.setDireccion("Marte 32");
            cli2.setRol(rolCliente);
            cli2.setFechaRegistro(LocalDate.now());
            dao.guardar(cli2);

            Cliente cli3 = new Cliente();
            cli3.setNombre("Camila");
            cli3.setApellido("Perez");
            cli3.setDocumento(40111222);
            cli3.setEmail("cami@gmail.com");
            cli3.setTelefono("11667788");
            cli3.setDireccion("Venus 11");
            cli3.setRol(rolCliente);
            cli3.setFechaRegistro(LocalDate.now());
            dao.guardar(cli3);

            // Creamos productos
            Producto pantalon = new Producto("Pantalón", 5000.0, 50);
            Producto camisa = new Producto("Camisa", 3000.0, 30);
            Producto zapatos = new Producto("Zapatos", 7000.0, 20);

            dao.guardar(pantalon);
            dao.guardar(camisa);
            dao.guardar(zapatos);

            // Creamos un pedido
            Pedido pedido1 = new Pedido();
            pedido1.setCliente(cli1);
            pedido1.setEmpleado(emp1);
            pedido1.setFechaPedido(LocalDate.now());
            dao.guardar(pedido1);

            // Creamos una factura
            Factura factura1 = new Factura();
            dao.asignarFacturaAPedido(pedido1.getIdPedido(), factura1);

            // Creamos los detalles de la factura
            DetalleFactura detalle1 = new DetalleFactura();
            detalle1.setProducto(camisa);
            detalle1.setCantidad(2);
            detalle1.setPrecioUnitario(camisa.getPrecio());
            dao.agregarDetalleAFactura(factura1.getIdFactura(), detalle1);

            DetalleFactura detalle2 = new DetalleFactura();
            detalle2.setProducto(pantalon);
            detalle2.setCantidad(1);
            detalle2.setPrecioUnitario(pantalon.getPrecio());
            dao.agregarDetalleAFactura(factura1.getIdFactura(), detalle2);

            // Nuevo pedido
            Pedido pedido2 = new Pedido();
            pedido2.setCliente(cli2);
            pedido2.setEmpleado(emp1);
            pedido2.setFechaPedido(LocalDate.now());
            dao.guardar(pedido2);

            Factura factura2 = new Factura();
            dao.asignarFacturaAPedido(pedido2.getIdPedido(), factura2);

            DetalleFactura detalle3 = new DetalleFactura();
            detalle3.setProducto(zapatos);
            detalle3.setCantidad(1);
            detalle3.setPrecioUnitario(zapatos.getPrecio());
            dao.agregarDetalleAFactura(factura2.getIdFactura(), detalle3);

            DetalleFactura detalle4 = new DetalleFactura();
            detalle4.setProducto(camisa);
            detalle4.setCantidad(3);
            detalle4.setPrecioUnitario(camisa.getPrecio());
            dao.agregarDetalleAFactura(factura2.getIdFactura(), detalle4);

            System.out.println("=== Finalizando... ===");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
