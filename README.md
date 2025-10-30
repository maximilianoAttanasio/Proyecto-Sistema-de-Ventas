# Sistema de Gestión de Ventas

## Descripción

Proyecto de simulación de un sistema de gestión de ventas, desarrollado en Java con Spring Boot.  
Permite modelar las entidades principales y sus relaciones para la gestión de clientes, empleados, pedidos, facturas y
productos.

## Entidades principales

- **Persona** (base para Empleado y Cliente)
- **Empleado**
- **Cliente**
- **Producto**
- **Pedido**
- **Factura**
- **DetalleFactura**
- **Rol**

## Relaciones

- `Empleado` y `Cliente` heredan de `Persona`.
- `Pedido` está asociado a un `Cliente` y un `Empleado`.
- `Factura` corresponde a un `Pedido`.
- `DetalleFactura` referencia a una `Factura` y un `Producto`.
- Cada `Persona` tiene asignado un `Rol`.

## Base de datos

- Se genera automáticamente a partir de las clases JPA.
- Cada `Persona` tiene un `idPersona`, que se usa para identificar empleados y clientes en las tablas relacionadas.
- No se crearon IDs independientes para empleados o clientes; se usa el de `Persona`.

## Carga de prueba

Al iniciar la aplicación se crean datos de ejemplo para:

- Roles (Empleado, Cliente)
- Empleados y clientes
- Productos
- Pedidos con sus respectivas facturas y detalles