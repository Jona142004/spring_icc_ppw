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
