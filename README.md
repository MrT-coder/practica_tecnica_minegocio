# Prueba Práctica

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15+-blue.svg)](https://www.postgresql.org/)
[![Maven](https://img.shields.io/badge/Maven-3.8+-red.svg)](https://maven.apache.org/)

## Descripción

Sistema de gestión de clientes desarrollado en **Java 17** con **Spring Boot** que permite registrar, editar, eliminar y buscar clientes, así como gestionar sus direcciones (matriz y adicionales), cumpliendo casos de uso y criterios de validación.


## Tecnologías Utilizadas

| Tecnología | Versión | Descripción |
|------------|---------|-------------|
| **Java** | 17 | Lenguaje de programación principal |
| **Spring Boot** | 3.5.5 | Framework de desarrollo |
| **Spring Data JPA** | - | Persistencia de datos |
| **Hibernate** | - | ORM (Object-Relational Mapping) |
| **PostgreSQL** | 15+ | Base de datos relacional |
| **Maven** | 3.8+ | Gestión de dependencias |
| **Lombok** | - | Reducción de código boilerplate |
| **Bean Validation** | - | Validación de datos |
| **JUnit 5** | - | Framework de testing |
| **Mockito** | - | Mocking para pruebas unitarias |



## Instalación y Configuración

### Prerrequisitos

- **Java 17** o superior
- **Maven 3.8** o superior
- **PostgreSQL 15** o superior
- **Git** para clonar el repositorio

### Configuración de Base de Datos

1. **Crear la base de datos en PostgreSQL:**
```sql
CREATE DATABASE minegocio_db;
```

2. **Configurar usuario si no existe y permisos:**
```sql
CREATE USER postgres WITH PASSWORD '123';
GRANT ALL PRIVILEGES ON DATABASE minegocio_db TO postgres;
```

### Instalación

1. **Clonar el repositorio:**
```bash
git clone https://github.com/MrT-coder/practica_tecnica_minegocio.git
cd practica_tecnica_minegocio/facturacion
```

2. **Configurar la base de datos:**
   
   Editar `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/minegocio_db?currentSchema=public
spring.datasource.username=postgres
spring.datasource.password=123
```

3. **Compilar el proyecto:**
```bash
mvn clean compile
```

4. **Ejecutar la aplicación:**
```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## API REST - Endpoints

### Gestión de Clientes

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/clientes` | Buscar clientes (todos o por criterio) |
| `GET` | `/api/clientes/{id}` | Obtener cliente por ID |
| `POST` | `/api/clientes` | Crear nuevo cliente con dirección matriz |
| `PUT` | `/api/clientes/{id}` | Actualizar datos del cliente |
| `DELETE` | `/api/clientes/{id}` | Eliminar cliente |

### Gestión de Direcciones

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/clientes/{id}/direcciones` | Listar direcciones del cliente |
| `POST` | `/api/clientes/{id}/direcciones` | Registrar nueva dirección |

## Ejemplos de Uso

### Crear Cliente con Dirección Matriz

**POST** `http://localhost:8080/api/clientes`
```json
{
  "tipo_identificacion": "CEDULA",
  "numero_identificacion": "1234567890",
  "nombres": "Juan Carlos Pérez",
  "correo": "juan.perez@email.com",
  "numero_celular": "0987654321",
  "direccion_matriz": {
    "provincia": "Pichincha",
    "ciudad": "Quito",
    "direccion": "Av. 10 de Agosto N24-123 y Cordero"
  }
}
```

### Buscar Cliente por Número de Identificación

**GET** `http://localhost:8080/api/clientes?criterio=1234567890`

### Buscar Cliente por Nombre

**GET** `http://localhost:8080/api/clientes?criterio=Juan`

### Actualizar Cliente

**PUT** `http://localhost:8080/api/clientes/1`
```json
{
  "tipo_identificacion": "CEDULA",
  "numero_identificacion": "1234567890",
  "nombres": "Juan Carlos Pérez García",
  "correo": "juan.carlos@nuevoemail.com",
  "numero_celular": "0999888777"
}
```

### Registrar Nueva Dirección

**POST** `http://localhost:8080/api/clientes/1/direcciones`
```json
{
  "provincia": "Guayas",
  "ciudad": "Guayaquil",
  "direccion": "Av. 9 de Octubre y Malecón, Edificio Torres del Río"
}
```

### Listar Direcciones del Cliente

**GET** `http://localhost:8080/api/clientes/1/direcciones`

### Eliminar Cliente

**DELETE** `http://localhost:8080/api/clientes/1`



## Ejecutar Pruebas

### Ejecutar todas las pruebas:
```bash
mvn test
```

### Ejecutar pruebas específicas:
```bash
mvn test -Dtest=ClienteServiceTest
mvn test -Dtest=DireccionClienteServiceTest
```

## Funcionalidades Implementadas

- **Buscar y obtener listado de clientes**
- **Crear nuevo cliente con dirección matriz** 
- **Editar datos del cliente**
- **Eliminar cliente**
- **Registrar nueva dirección por cliente**
- **Listar direcciones adicionales del cliente**

