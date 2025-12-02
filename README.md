# Sistema de Gestión de Ventas

## Descripción

Proyecto de simulación de un sistema de gestión de ventas, desarrollado en Java con Spring Boot.  
Permite modelar las entidades principales y sus relaciones para la gestión de clientes, empleados, pedidos, facturas y productos.

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
- No se crearon IDs independientes para empleados o clientes, se usa el de `Persona`.
- Se pueden utilizar dos entornos: local (MySQL) y Render (PostgreSQL).
    - Cambiar las secciones correspondientes en pom.xml y application.properties.
    - Luego hacer un Reload Maven Project en el IDE.

La base de Render es independiente de la local y se puede acceder mediante Postman o Swagger.

## Swagger

Para acceder a Swagger, abrir en el navegador:

- Si lo Utilizas en Local: `http://localhost:8080/swagger-ui.html`
- Si lo Utilizas Desde Render: `https://proyecto-sistema-de-ventas.onrender.com/swagger-ui.html`

*Recuerda Seleccionar el Server Correspondiente Dependiendo del Entorno Sobre el que se Trabaja*

## Endpoints para Postman

*Si Trabajas en local:* `http://localhost:8080`

*Si Trabajas en Render:* `https://proyecto-sistema-de-ventas.onrender.com`

### Empleados y Clientes

- **Obtener Todos los Empleados**
    - GET `/api/empleados`
    - GET `/api/empleados/{empleadoId}`
- **Crear Empleado:** POST `/api/empleados/create`
    - Body:
    ```json
    {
        "nombre": "nombre",
        "apellido": "apellido",
        "documento": "12345678",
        "email": "correo@test.com",
        "telefono": "1234567890",
        "direccion": "1.048596",
        "sueldo": "350000",
        "rol": { "idRol": 1 }
    }
    ```
- **Actualizar Empleado por ID:** PUT `/api/empleados/{empleadoId}`
    - Body: (Puedes Enviar Solo los Campos a Modificar)
    ```json
    {
        "nombre": "Nuevo Nombre"
    }
    ```
- **Eliminar Empleado por ID:** DELETE `/api/empleados/{empleadoId}`

- **Obtener Todos los Clientes o por ID**
    - GET `/api/clientes`
    - GET `/api/clientes/{clienteId}`
- **Crear Cliente:** POST `/api/clientes/create`
    - Body:
    ```json
    {
        "nombre": "nombre",
        "apellido": "apellido",
        "documento": 12345678,
        "email": "correo@test.com",
        "telefono": "1234567890",
        "direccion": "1.048596",
        "rol": { "idRol": 2 }
    }
    ```
- **Actualizar Cliente Por ID:** PUT `/api/clientes/{clienteId}`
    - Body: (Puedes Enviar Solo los Campos a Modificar)
    ```json
    {
        "email": "nuevoCorreo@test.com"
    }
    ```
- **Eliminar Cliente por ID:** DELETE `/api/clientes/{clienteId}`

### Productos

- **Obtener Lista de Productos o por ID**
    - GET `/api/productos`
    - GET `/api/productos/{productoId}`
- **Crear Producto:** POST `/api/productos/create`
    - Body:
    ```json
    {
        "nombre": "nombre",
        "precio": 1000,
        "stock": 10
    }
    ```
- **Actualizar Producto por ID:** PUT `/api/productos/{productoId}`
    - Body: (Puedes Enviar Solo los Campos a Modificar)
    ```json
    {
        "precio": 1500,
        "stock": 15
    }
    ```
- **Eliminar Producto por ID:** DELETE `/api/productos/{productoId}`

### Pedidos

- **Obtener Lista de Pedidos o por ID**
    - GET `/api/pedidos`
    - GET `/api/pedidos/{pedidoId}`
- **Crear Pedido:** POST `/api/pedidos/create`
    - Body:
    ```json
    {
        "cliente": { "idPersona": 4 },
        "empleado": { "idPersona": 8 }
    }
    ```

### Facturas

- **Obtener Lista de Facturas o por ID con Detalles Reducidos**
    - GET `/api/facturas`
    - GET `/api/facturas/{facturaId}/detalles`
- **Crear Factura y Asociar a Pedido:** POST `/api/facturas/create`
    - Body:
    ```json
    {
        "pedido": { "idPedido": 3 }
    }
    ```

### Detalles de Factura

- **Crear Detalles y Asignar a Factura:** POST `/api/detalles/agregar-detalle`
    - Body:
    ```json
    {
        "idFactura": 1,
        "idProducto": 2,
        "cantidad": 3
    }
    ```

### Roles

- **Obtener Roles**
    - GET `/api/roles`
    - GET `/api/roles/{rolId}`
- **Crear un Nuevo Rol:** POST `/api/roles/create`
    - Body:
    ```json
    {
        "nombreRol": "Admin"
    }
    ```
- **Actualizar Rol por ID:** PUT `/api/roles/{rolId}`
    - Body:
    ```json
    {
        "nombreRol": "Administrador"
    }
    ```