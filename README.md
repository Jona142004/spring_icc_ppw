# Práctica 1: Spring Boot - Instalación y Configuración

## 10. Resultados y Evidencias

### 1. Verificación de Java
Comando:
```bash
java -version

```
![alt text](assents/image.png)



### 2. Servidor Spring Boot ejecutándose
Salida de consola:
```
:: Spring Boot :: (v4.1.0)

Tomcat started on port 8080

Started Fundamentos01Application in 3.659 seconds
```

### 3. Endpoint `/api/status` funcionando
Acceso: `http://localhost:8080/api/status`
Respuesta JSON:
![alt text](assents/image-1.png)



### 4. Estructura del proyecto - Controlador creado
![alt text](assents/image-2.png)

**Funcionamiento del endpoint:**
El controlador `StatusController` utiliza la anotación `@RestController` para exponer un endpoint HTTP GET en la ruta `/api/status`. El método `status()` devuelve un `Map` que se serializa automáticamente a JSON por Spring Boot. La anotación `@GetMapping("/api/status")` mapea las solicitudes HTTP GET a este método específico.

**Función de Spring Boot:**
Spring Boot proporciona un servidor embebido (Tomcat) integrado que se inicia automáticamente junto con la aplicación. Esto elimina la necesidad de configurar servidores externos. La auto-configuración de Spring Boot detecta automáticamente las dependencias (spring-boot-starter-web) e inicializa el servidor en el puerto 8080 por defecto, permitiendo que la aplicación sea un servicio autónomo desplegable como un único archivo `.jar`.
# Práctica 2: Spring Boot - Estructura del Proyecto y Arquitectura Modular

## 11. Resultados y Evidencias

### 1. Captura del IDE mostrando la estructura modular

**Visualización de la estructura en VSCode/IntelliJ:**

![alt text](assents/spring%20src.png)

**Estructura visible:**
- ✅ Paquete raíz: `ec.edu.ups.icc.fundamentos01`
- ✅ Módulo `products/` con carpetas: `controllers/`, `services/`, `repositories/`, `entities/`, `dtos/`, `mappers/`
- ✅ Módulo `users/` con la misma estructura
- ✅ Módulo `auth/` con la misma estructura
- ✅ Carpetas globales: `config/`, `utils/`
- ✅ Archivo principal: `Fundamentos01Application.java`

---

### 2. Captura del archivo `Fundamentos01Application.java`

**Ubicación:** `src/main/java/ec/edu/ups/icc/fundamentos01/Fundamentos01Application.java`

![alt text](assents/spring%20fun%2001.png)

**Código:**
```java
package ec.edu.ups.icc.fundamentos01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Fundamentos01Application {

	public static void main(String[] args) {
		SpringApplication.run(Fundamentos01Application.class, args);
	}

}
```

**Verificación:**
- ✅ Package raíz correcto: `ec.edu.ups.icc.fundamentos01`
- ✅ Anotación `@SpringBootApplication` que activa `@ComponentScan`
- ✅ Ubicación permite que Spring detecte todos los controladores y servicios en los subpaquetes

---

### 3. Explicación breve: ¿Por qué es importante tener módulos separados?

**Respuesta:**

Tener módulos separados es importante porque:

1. **Escalabilidad**: Cada módulo (products, users, auth) es independiente. Cuando la aplicación crece, se pueden agregar nuevos módulos sin afectar los existentes.

2. **Mantenibilidad**: El código es más fácil de entender y modificar cuando está organizado por dominios. Un desarrollador que trabaja con productos no afecta el código de usuarios.

3. **Reutilización**: Los servicios de cada módulo pueden ser reutilizados por múltiples controladores, evitando duplicación de código.

4. **Trabajo en equipo**: Diferentes desarrolladores pueden trabajar en módulos diferentes simultáneamente sin conflictos.

5. **Testabilidad**: Cada módulo puede probarse de forma aislada, facilitando las pruebas unitarias e integración.

6. **Separación de responsabilidades**: Cada carpeta tiene una función clara:
   - `controllers/`: reciben solicitudes HTTP
   - `services/`: lógica de negocio
   - `repositories/`: acceso a datos
   - `entities/`: modelos de datos
   - `dtos/`: transferencia de datos

Esta estructura sigue patrones profesionales de arquitectura backend y es la estándar en aplicaciones empresariales reales.
# Práctica 3: Spring Boot - API REST y CRUD con DTOs y Mappers



## 10. Resultados y Evidencias

### 1. Estructura de carpetas implementada

**Captura de la estructura en VSCode:**

![alt text](assents/practica3-estructura.png)

**Estructura visible:**
- ✅ `users/` con: `models/`, `dtos/`, `mappers/`, `controllers/`
- ✅ `products/` con la misma estructura
- ✅ Todos los archivos creados y organizados por dominio

---

### 2. Componentes principales creados

#### UserModel
**Ubicación:** `src/main/java/ec/edu/ups/icc/fundamentos01/users/models/UserModel.java`

![alt text](assents/user-model.png)

**Características:**
- Propiedades: id, name, email, password, passwordHash, createdAt
- No tiene anotaciones JPA (solo es un modelo de dominio)
- Getters y setters completos

---

#### DTOs (CreateUserDto, UpdateUserDto, PartialUpdateUserDto, UserResponseDto)

**Ejemplo de CreateUserDto:**

![alt text](assents/create-user-dto.png)

```java
public class CreateUserDto {
    private String name;
    private String email;
    private String password;
    // getters y setters
}
```

**Ejemplo de UserResponseDto:**

![alt text](assents/user-response-dto.png)

```java
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    // No incluye password ni passwordHash (seguridad)
}
```

**Diferencia:**
- CreateUserDto: acepta name, email, password (para crear)
- UpdateUserDto: acepta name, email (sin password)
- PartialUpdateUserDto: todos los campos opcionales (null)
- UserResponseDto: solo id, name, email (datos públicos)

---

#### UserMapper

**Ubicación:** `src/main/java/ec/edu/ups/icc/fundamentos01/users/mappers/UserMapper.java`

![alt text](assents/user-mapper.png)

**Funciones:**
```java
// Convierte DTO → Model (entrada)
public static UserModel toModel(CreateUserDto dto) { ... }

// Convierte Model → DTO (salida)
public static UserResponseDto toResponse(UserModel model) { ... }
```

**Ventaja:** Separación entre lo que entra (DTO) y lo que maneja la aplicación (Model)

---

#### UsersController - CRUD completo

**Ubicación:** `src/main/java/ec/edu/ups/icc/fundamentos01/users/controllers/UsersController.java`

![alt text](assents/users-controller-full.png)

**6 Endpoints implementados:**

| Método | Endpoint | Función |
|--------|----------|---------|
| GET | `/api/users` | Obtiene todos |
| GET | `/api/users/{id}` | Obtiene uno por ID |
| POST | `/api/users` | Crea nuevo |
| PUT | `/api/users/{id}` | Reemplaza completo |
| PATCH | `/api/users/{id}` | Actualiza parcial |
| DELETE | `/api/users/{id}` | Elimina |

**Almacenamiento:**
- `List<UserModel> users` - simula base de datos en memoria
- `currentId` - genera IDs únicos automáticamente

---

#### ProductModel

**Ubicación:** `src/main/java/ec/edu/ups/icc/fundamentos01/products/models/ProductModel.java`

![alt text](assents/product-model.png)

**Propiedades:**
```java
private Long id;
private String name;
private Double price;
private Integer stock;
private LocalDateTime createdAt;
```

---

#### ProductsController - CRUD idéntico a Users

**Ubicación:** `src/main/java/ec/edu/ups/icc/fundamentos01/products/controllers/ProductsController.java`

![alt text](assents/products-controller-full.png)

**6 Endpoints:**

| Método | Endpoint | Función |
|--------|----------|---------|
| GET | `/api/products` | Obtiene todos |
| GET | `/api/products/{id}` | Obtiene uno por ID |
| POST | `/api/products` | Crea nuevo |
| PUT | `/api/products/{id}` | Reemplaza completo |
| PATCH | `/api/products/{id}` | Actualiza parcial |
| DELETE | `/api/products/{id}` | Elimina |

---

### 3. Aplicación ejecutándose

**Comando:**
```bash
./gradlew bootRun
```

**Salida esperada:**
```
Tomcat started on port(s): 8080

Started Fundamentos01Application in 2.5 seconds
```

![alt text](assents/practica3-running.png)

---

### 4. Pruebas en Postman - GET /api/products (vacío)

**Request:**
```
GET http://localhost:8080/api/products
```

**Respuesta:**
```json
[]
```

![alt text](assents/postman-get-empty.png)

---

### 5. Pruebas en Postman - POST /api/products (crear 3 productos)

#### Crear Producto 1: Laptop

**Request:**
```
POST http://localhost:8080/api/products
Content-Type: application/json

{
  "name": "Laptop",
  "price": 999.99,
  "stock": 10
}
```

**Respuesta:**
```json
{
  "id": 1,
  "name": "Laptop",
  "price": 999.99,
  "stock": 10
}
```

![alt text](assents/postman-post-laptop.png)

---

#### Crear Producto 2: Mouse

**Request:**
```
POST http://localhost:8080/api/products
Content-Type: application/json

{
  "name": "Mouse",
  "price": 29.99,
  "stock": 50
}
```

**Respuesta:**
```json
{
  "id": 2,
  "name": "Mouse",
  "price": 29.99,
  "stock": 50
}
```

![alt text](assents/postman-post-mouse.png)

---

#### Crear Producto 3: Keyboard

**Request:**
```
POST http://localhost:8080/api/products
Content-Type: application/json

{
  "name": "Keyboard",
  "price": 79.99,
  "stock": 25
}
```

**Respuesta:**
```json
{
  "id": 3,
  "name": "Keyboard",
  "price": 79.99,
  "stock": 25
}
```

![alt text](assents/postman-post-keyboard.png)

---

### 6. Pruebas en Postman - GET /api/products (con 3 productos)

**Request:**
```
GET http://localhost:8080/api/products
```

**Respuesta:**
```json
[
  {
    "id": 1,
    "name": "Laptop",
    "price": 999.99,
    "stock": 10
  },
  {
    "id": 2,
    "name": "Mouse",
    "price": 29.99,
    "stock": 50
  },
  {
    "id": 3,
    "name": "Keyboard",
    "price": 79.99,
    "stock": 25
  }
]
```

![alt text](assents/postman-get-three.png)

---

### 7. Pruebas en Postman - GET /api/products/{id} (producto existente)

**Request:**
```
GET http://localhost:8080/api/products/1
```

**Respuesta:**
```json
{
  "id": 1,
  "name": "Laptop",
  "price": 999.99,
  "stock": 10
}
```

![alt text](assents/postman-get-by-id.png)

---

### 8. Pruebas en Postman - DELETE /api/products/{id} (producto existente)

**Request:**
```
DELETE http://localhost:8080/api/products/2
```

**Respuesta:**
```json
{
  "message": "Deleted successfully"
}
```

![alt text](assents/postman-delete-success.png)

---

### 9. Pruebas en Postman - DELETE /api/products/{id} (producto no existente)

**Request:**
```
DELETE http://localhost:8080/api/products/999
```

**Respuesta:**
```json
{
  "error": "Product not found"
}
```

![alt text](assents/postman-delete-fail.png)

---

## 11. Explicación de la arquitectura

### Flujo de una solicitud HTTP

```
POST /api/products
{
  "name": "Laptop",
  "price": 999.99,
  "stock": 10
}
        ↓
@RestController ProductsController
        ↓
@PostMapping create(CreateProductDto dto)
        ↓
ProductMapper.toModel(dto) → ProductModel
        ↓
Asignar ID automático
        ↓
Agregar a List<ProductModel> products
        ↓
ProductMapper.toResponse(model) → ProductResponseDto
        ↓
HTTP 200 OK
{
  "id": 1,
  "name": "Laptop",
  "price": 999.99,
  "stock": 10
}
```

---

### Ventajas de los DTOs

**Sin DTOs (problema):**
```java
// El cliente envaría todo, incluso datos internos
{
  "id": 1,
  "name": "Laptop",
  "password": "123456",      // ❌ Expone datos sensibles
  "passwordHash": "HASH_123",// ❌ Datos internos
  "createdAt": "2024-12-18" // ❌ No debería venir del cliente
}
```

**Con DTOs (solución):**
```java
// CreateProductDto - Solo acepta lo necesario
{
  "name": "Laptop",
  "price": 999.99,
  "stock": 10
}

// ProductResponseDto - Solo devuelve datos públicos
{
  "id": 1,
  "name": "Laptop",
  "price": 999.99,
  "stock": 10
}
// Nota: No expone createdAt ni datos internos
```

---

### Ventajas de los Mappers

**Sin Mapper (problema - duplicación):**
```java
@PostMapping
public ProductModel create(@RequestBody CreateProductDto dto) {
    ProductModel product = new ProductModel();
    product.setName(dto.getName());          // ❌ Copia manual
    product.setPrice(dto.getPrice());        // ❌ Copia manual
    product.setStock(dto.getStock());        // ❌ Copia manual
    product.setId(currentId++);
    product.setCreatedAt(LocalDateTime.now());
    products.add(product);
    
    ProductResponseDto response = new ProductResponseDto();
    response.setId(product.getId());         // ❌ Copia manual
    response.setName(product.getName());     // ❌ Copia manual
    response.setPrice(product.getPrice());   // ❌ Copia manual
    response.setStock(product.getStock());   // ❌ Copia manual
    return response;
}
```

**Con Mapper (solución - centralizado):**
```java
@PostMapping
public ProductResponseDto create(@RequestBody CreateProductDto dto) {
    ProductModel product = ProductMapper.toModel(dto);    // ✅ Delegado
    product.setId(currentId++);
    products.add(product);
    return ProductMapper.toResponse(product);             // ✅ Delegado
}
```

**Ventaja:** Si cambian los campos, solo se edita el Mapper, no todos los endpoints.

---

## 12. Diferencia entre CRUD sin servicios vs con servicios (siguiente práctica)

### Práctica 3 (actual - sin servicios)
```
Request → Controller → Lógica de negocio → List (memoria) → Response
```

**Problema:** El controlador hace todo (mezcla responsabilidades)

---

### Práctica 4 (siguiente - con servicios y JPA)
```
Request → Controller → Service → Repository → Base de datos → Response
```

**Mejora:** Cada capa tiene su responsabilidad clara

---

## 13. Resumen de lo implementado

✅ **UserModel y ProductModel** - Modelos de dominio sin JPA  
✅ **4 DTOs por recurso** - Create, Update, PartialUpdate, Response  
✅ **Mappers** - Conversión automática entre DTOs y Models  
✅ **6 Endpoints CRUD** - GET, GET/:id, POST, PUT, PATCH, DELETE  
✅ **Almacenamiento en memoria** - List<Model> como BD temporal  
✅ **IDs automáticos** - currentId generado por el backend  
✅ **Manejo de errores** - Respuestas claras cuando no existe recurso  
✅ **Separación de responsabilidades** - DTOs, Models, Mappers, Controllers  

---

## 14. Próximos pasos (Práctica 4)

- Implementar `@Service` para lógica de negocio
- Usar `@Repository` con Spring Data JPA
- Conectar a base de datos H2 o PostgreSQL
- Agregar validaciones con `@Valid`
- Implementar manejo global de excepciones









