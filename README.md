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


### 6. Pruebas en Postman - GET /api/products (con 3 productos)

**Request:**
```
GET http://localhost:8080/api/products
```


![alt text](assents/3produc.png)

---

### 7. Pruebas en Postman - GET /api/products/{id} (producto existente)

**Request:**
```
GET http://localhost:8080/api/products/1
```



![alt text](assents/1pro3.png)

---

### 8. Pruebas en Postman - DELETE /api/products/{id} (producto existente)

**Request:**
```
DELETE http://localhost:8080/api/products/2
```



![alt text](assents/deletepro.png)

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

![alt text](assents/borrarpro.png)

---




# Práctica 4: Spring Boot - Servicios e Inyección de Dependencias

## 8. Resultados y Evidencias

### 1. Captura de ProductServiceImpl.java

![alt text](assents/practica4-product-service-impl.png)



---

### 2. Captura de ProductsController.java

![alt text](assents/practica4-products-controller.png)


---

### 3. Explicación breve: ¿Cómo se inyecta el servicio en el controlador?

**Inyección de Dependencias por Constructor:**

1. **Spring detecta la dependencia:**
   ```java
   private final ProductService service;
   ```

2. **El controlador solicita la inyección mediante constructor:**
   ```java
   public ProductsController(ProductService service) {
       this.service = service;
   }
   ```

3. **Spring busca una implementación de `ProductService`:**
   - Encuentra `ProductServiceImpl` porque tiene anotación `@Service`

4. **Spring crea automáticamente la instancia y la inyecta:**
   - No necesitamos hacer `new ProductServiceImpl()`
   - Spring lo hace por nosotros

5. **El controlador usa el servicio:**
   ```java
   @GetMapping
   public List<ProductResponseDto> findAll() {
       return service.findAll();  // Delega al servicio
   }
   ```

**Ventaja:** El controlador queda limpio y solo coordina, no implementa lógica.









