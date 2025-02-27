#  API Productos

## 📝 Descripción

API REST desarrollada en **Spring Boot** para la gestión de productos. Permite realizar operaciones CRUD utilizando **metodos HTTP** y devuelve **códigos de estado** que indican el resultado de cada operación. El proyecto está diseñado con una arquitectura basada en capas:

- **Controllers**: Gestiona las solicitudes HTTP y se comunica con los servicios para interactuar con la base de datos.
- **Validator**: Implementa validaciones y lanza excepciones si los datos no cumplen con determinadas condiciones.
- **Domain**: Clases POJO que representan las entidades del dominio.
- **Service**: Define la lógica de negocio a través de interfaces e implementaciones.

La persistencia de datos se realiza con **MySQL** y **JDBC** para gestionar las consultas.

---

## 🛠️ Tecnologías Usadas

- **Spring Boot**: Framework principal para el desarrollo de la API.
- **MySQL**: Base de datos relacional utilizada para almacenar los productos.
- **JDBC**: Manejo directo de consultas SQL.
- **Spring Annotations**:
  - **@RestController**: Define la clase como un controlador REST.
  - **@RequestMapping**: Asocia un controlador a una ruta base.
  - **@PathVariable**: Extrae parámetros de la URL.
  - **@RequestBody**: Mapea el cuerpo de la solicitud a un objeto Java.
  - **@Autowired**: Gestiona la inyección de dependencias.
  - **@Service**: Marca la clase como un servicio que contiene la lógica de negocio.

---

# 🌐 Endpoints Disponibles

## 🔍 Obtener todos los productos
**`GET /producto`**  
- **200 OK**: Lista de productos obtenida correctamente.  
- **204 No Content**: No hay productos disponibles.  

---

## 🔍 Obtener producto por ID
**`GET /producto/{idProducto}`**  
- **200 OK**: Producto encontrado.  
- **404 Not Found**: Producto no encontrado.  

---

## ➕ Crear un producto
**`POST /producto`**  
- **201 Created**: Producto creado exitosamente. Retorna la URI del recurso.  
- **400 Bad Request**: Datos inválidos en la solicitud.  

---

## 🗑️ Eliminar un producto
**`DELETE /producto/{idProducto}`**  
- **204 No Content**: Producto eliminado exitosamente.  
- **404 Not Found**: Producto no encontrado.  

---

## 🔄 Actualización completa de un producto
**`PUT /producto`**  
- **200 OK**: Producto actualizado correctamente.  
- **400 Bad Request**: Solicitud inválida.  
- **404 Not Found**: Producto no encontrado.  

---

## ✏️ Actualización parcial de un producto
**`PATCH /producto`**  
- **200 OK**: Producto actualizado parcialmente.  
- **400 Bad Request**: Solicitud inválida.  
- **404 Not Found**: Producto no encontrado.  

---

## 📉 Descontar stock
**`PATCH /producto/actualizarStock/{idProducto}/{unidadesSolicitadas}`**  
- **200 OK**: Stock actualizado correctamente.  
- **400 Bad Request**: Datos inválidos o unidades insuficientes.

# 🗄️ Configuración de la Base de Datos

## 🎯 Script para crear la base de datos
```sql
CREATE DATABASE db_productos;
USE db_productos;

CREATE TABLE Producto (
   id INT PRIMARY KEY AUTO_INCREMENT,
   nombre VARCHAR(30),
   descripcion VARCHAR(100),
   stock INT,
   precio DOUBLE
);

-- Registros para hacer las primeras pruebas.
INSERT INTO Producto (nombre, descripcion, stock, precio)
VALUES ("CocaCola", "2.5Lts", 23, 3200);

INSERT INTO Producto (nombre, descripcion, stock, precio)
VALUES ("Sprite", "2.5Lts", 10, 3150);
```

# Configuración para conectarse a la base de datos MySQL  
spring.datasource.url=jdbc:mysql://localhost:3306/db_productos?useSSL=false&serverTimezone=UTC  
spring.datasource.username=TU-NOMBRE-DE-USUARIO  
spring.datasource.password=TU-CONTRASEÑA  

# 📬 Postman Collection: Gianca-Workspace

Esta colección de Postman contiene todos los endpoints necesarios para interactuar con la API de productos. Puedes importar esta colección en Postman para probar y documentar cada funcionalidad.

---

## 🌟 Cómo importar la colección

1. Haz clic en el enlace de la colección:  
   [Gianca-Workspace - Postman Collection](https://gianca-8786.postman.co/workspace/Gianca-Workspace~f1b18955-e9f5-42ae-81db-11bb3360f7ea/collection/40242380-62708e0f-bbfb-48e7-b3cf-6958027d5659?action=share&creator=40242380)

2. En Postman, selecciona **Importar** en la esquina superior izquierda.

3. Pega el enlace o sube el archivo JSON exportado para agregar la colección a tu workspace.

---

  
