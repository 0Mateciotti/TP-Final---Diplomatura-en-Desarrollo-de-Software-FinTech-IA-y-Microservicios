# TP Final — Diplomatura en Desarrollo de Software FinTech: IA y Microservicios

Ecosistema de microservicios en Spring Boot / Spring Cloud que simula el backend de un homebanking: un servicio de clientes que consulta, vía Feign y Eureka, los productos bancarios de otro servicio, con toda la configuración centralizada en un Config Server.

## Arquitectura

```
                 ┌──────────────────┐
                 │  config-repo/     │  (carpeta de este mismo repo)
                 └────────┬─────────┘
                          │ lee al arrancar
                 ┌────────▼────────┐
                 │  config-server  │  :8888
                 └────────┬────────┘
          ┌───────────────┼───────────────┐
          │ pide config   │               │ pide config
   ┌──────▼──────┐  ┌─────▼──────┐  ┌──────▼───────┐
   │ eureka-srv  │  │  customer  │  │   product    │
   │   :8761     │◄─┤  service   │  │   service    │
   └─────────────┘  │   :8081    │  │   :8082      │
        ▲  ▲        └─────┬──────┘  └──────▲───────┘
        │  └──────────────┘  Feign Client  │
        │       se registran en Eureka     │
        └──────────────────────────────────┘
```

- **`eureka-server`**: registro y descubrimiento de servicios (service discovery).
- **`config-server`**: centraliza la configuración (puertos, datasource, credenciales, URL de Eureka) de `customer-service` y `product-service`, leída desde `config-repo/` en este mismo repositorio remoto.
- **`product-service`**: gestiona los productos bancarios de un cliente. No depende de nadie más.
- **`customer-service`**: gestiona clientes. Consulta los productos de un cliente llamando a `product-service` mediante **Feign**, resuelto por nombre a través de **Eureka**.

Cada tipo de producto bancario es una entidad propia (`Cuenta`, `PrestamoPersonal`, `TarjetaCredito`), todas heredando los campos comunes (`id`, `clienteId`, `activo`, `fechaAlta`) de una clase base `Producto` (`@MappedSuperclass`, sin tabla propia).

## Servicios y puertos

| Servicio | Puerto | Rol |
|---|---|---|
| `eureka-server` | 8761 | Service discovery |
| `config-server` | 8888 | Configuración centralizada |
| `product-service` | 8082 | Cuentas, préstamos y tarjetas |
| `customer-service` | 8081 | Clientes + agregador de productos |

## Requisitos previos

- Java 21
- Maven (o usar el `mvnw` incluido en cada proyecto)
- MySQL 8+ corriendo en `localhost:3306`

## Configuración inicial

Crear las dos bases de datos antes de levantar los servicios:

```sql
CREATE DATABASE customerdb;
CREATE DATABASE productdb;
```

Las credenciales de MySQL están en `config-repo/customer-service.yml` y `config-repo/product-service.yml` (por defecto `root` / `12345` — ajustar ahí si tu instalación usa otras).

## Orden de arranque

**`config-server` → `eureka-server` → `product-service` → `customer-service`**

`customer-service` y `product-service` están configurados con reintento (`spring.cloud.config.retry`) para tolerar que arranquen en un orden ligeramente distinto o en paralelo (por ejemplo, con una Run Configuration "Compound" de IntelliJ) — reintentan conectarse al Config Server varias veces antes de fallar.

## Cómo levantar cada servicio

Desde la carpeta de cada proyecto:

```bash
./mvnw spring-boot:run
```

O abrir el monorepo en IntelliJ, importar los 4 `pom.xml` como proyectos Maven, y correr cada `*Application.java` (o armar una Compound Run Configuration con los 4).

**Verificaciones rápidas:**
- Dashboard de Eureka: [http://localhost:8761](http://localhost:8761) — deberían aparecer `CUSTOMER-SERVICE` y `PRODUCT-SERVICE`.
- Config Server sirviendo la config real: `GET http://localhost:8888/customer-service/default`.

## Endpoints

### `customer-service` — `/customers`

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/customers` | Listar clientes |
| GET | `/customers/{id}` | Obtener cliente por id |
| POST | `/customers` | Crear cliente |
| PUT | `/customers/{id}` | Actualizar cliente |
| DELETE | `/customers/{id}` | Eliminar cliente |
| GET | `/customers/{id}/products` | Perfil del cliente: cliente + sus cuentas + préstamos + tarjetas (vía Feign a `product-service`) |

Body de `Customer` (request/response): `nombre`, `apellido`, `documento`, `direccion`, `email` (`id` y `createdAt` los genera el servidor).

### `product-service` — `/cuentas`, `/prestamos`, `/tarjetas`

Los 3 recursos exponen el mismo set de operaciones:

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/{recurso}` | Listar todos |
| GET | `/{recurso}/{id}` | Obtener por id |
| GET | `/{recurso}/cliente/{clienteId}` | Filtrar por cliente (usado por el Feign de `customer-service`) |
| POST | `/{recurso}` | Crear |
| PUT | `/{recurso}/{id}` | Actualizar |
| DELETE | `/{recurso}/{id}` | Eliminar |

Campos propios de cada uno:
- **Cuenta**: `clienteId`, `tipoCuenta` (`CUENTA_CORRIENTE` / `CAJA_AHORRO`), `saldo`, `activo`.
- **PrestamoPersonal**: `clienteId`, `montoPrestamo`, `tasaInteres`, `cantidadCuotas`, `activo`.
- **TarjetaCredito**: `clienteId`, `limite`, `activo`.

### Manejo de errores

Ambos servicios responden los "no encontrado" con 404 y un cuerpo consistente:

```json
{ "status": 404, "message": "Cliente no encontrado con id: 999" }
```

Cualquier otro error no controlado devuelve 500 con el mismo formato, en vez de un stacktrace crudo.

## Colección de Postman

En `postman/` hay una colección (`TP-Final-FinTech.postman_collection.json`) y su environment (`TP-Final-FinTech.postman_environment.json`) para probar los 4 servicios de punta a punta: crear cliente → crear cuenta/préstamo/tarjeta → consultar el agregador → limpiar. Los IDs se encadenan solos entre requests.

## Estructura del repositorio

```
.
├── eureka-server/       # Service discovery
├── config-server/       # Config Server (Spring Cloud Config)
├── config-repo/         # Config remota que lee el config-server (customer-service.yml, product-service.yml)
├── product-service/     # Cuenta / PrestamoPersonal / TarjetaCredito
├── customer-service/     # Customer + Feign Client hacia product-service
└── postman/             # Colección y environment para pruebas manuales
```

## Revisión de código con IA

Como parte del proceso de desarrollo, la IA Claude analizó y sugirió estos cambios que fueron luego aplicados en el proyecto:

> Hice de revisor: leí todo el código fuente de los 4 proyectos (controllers, services, repos, mappers, entidades, DTOs, excepciones, poms, yaml). Como ya lo probé funcionando de punta a punta antes, no encontré bugs funcionales — el review es sobre diseño/buenas prácticas. Esto es lo que encontré:
>
> **Hallazgos**
>
> 1. Sin Bean Validation en los DTOs de request — hoy nada impide un `POST /customers` con `nombre` vacío o un `/cuentas` con `saldo` negativo. Es fácil de agregar (`spring-boot-starter-validation` + `@NotBlank`/`@NotNull`/`@PositiveOrZero` en los records + `@Valid` en los controllers) y suma directo al criterio de "diseño y buenas prácticas". Recomiendo aplicarlo.
> 2. Los `DELETE` devuelven 200 en vez de 204 — lo confirmé en la prueba end-to-end (`HTTP/1.1 200, Content-Length: 0`). Semánticamente un delete exitoso sin body debería ser `204 No Content`. Cambio chico y de bajo riesgo (`ResponseEntity.noContent().build()` en los 4 controllers). Recomiendo aplicarlo.
> 3. `@Autowired` por campo en vez de inyección por constructor — lo detecté en todos los services/controllers. Técnicamente no es "lo más moderno" en Spring, pero fue una decisión explícita para calzar con el estilo de `TarjetasApi` que ya charlamos. No lo tocaría salvo que quieras que sí.

Los puntos 1 y 2 se aplicaron: se agregó `spring-boot-starter-validation` con anotaciones (`@NotBlank`, `@NotNull`, `@Positive`, `@PositiveOrZero`, `@Email`) en todos los DTOs de request, `@Valid` en los endpoints POST/PUT, un handler de `MethodArgumentNotValidException` en los `GlobalExceptionHandler` (devuelve 400 con el detalle de los campos inválidos), y los `DELETE` ahora devuelven `204 No Content`. El punto 3 se dejó como está, por ser una decisión de estilo ya tomada a propósito.
