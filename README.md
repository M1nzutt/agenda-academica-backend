# Agenda Académica Estudiantil — Backend

API REST de uso individual para gestionar materias, actividades y fechas de entrega. Sin frontend ni cuentas de usuario.

## Tecnologías
Java 21, Spring Boot 3.5.15, Spring Data MongoDB, MongoDB Atlas, Maven, Docker.

## Requisitos locales
- Java 21 y Maven 3.9 (o Docker)
- MongoDB local o URI de MongoDB Atlas

## Ejecutar
En PowerShell:
```powershell
$env:MONGODB_URI="mongodb://localhost:27017/agenda_academica"
mvn spring-boot:run
```
En Linux/macOS:
```bash
export MONGODB_URI='mongodb://localhost:27017/agenda_academica'
mvn spring-boot:run
```
Para pruebas: `mvn test`.

## API
| Método | Ruta | Operación |
|---|---|---|
| GET | /api/subjects | Listar materias |
| GET | /api/subjects/{id} | Ver materia |
| POST | /api/subjects | Crear materia |
| PUT | /api/subjects/{id} | Actualizar materia |
| DELETE | /api/subjects/{id} | Eliminar materia (409 si tiene actividades) |
| GET | /api/activities | Listar actividades, con filtros `subjectId`, `status`, `type` |
| GET | /api/activities/{id} | Ver actividad |
| POST | /api/activities | Crear actividad |
| PUT | /api/activities/{id} | Actualizar actividad (conserva estado) |
| PATCH | /api/activities/{id}/status | Cambiar estado |
| DELETE | /api/activities/{id} | Eliminar actividad |
| GET | /api/agenda | Ordenadas por fecha |
| GET | /api/agenda/summary | Resumen |
| GET | /actuator/health | Comprobar funcionamiento |

## Ejemplos en Postman

Crear materia, POST `/api/subjects`:
```json
{"name":"Arquitectura de Software","code":"ARQ101","teacher":"Docente de clase"}
```
Copia el `id` retornado y úsalo como `subjectId` para POST `/api/activities`:
```json
{"subjectId":"ID_DE_LA_MATERIA","title":"Exposición","description":"Preparar diapositivas","type":"PROJECT","priority":"HIGH","dueDate":"2026-12-03"}
```
Actualizar estado, PATCH `/api/activities/ID/status`:
```json
{"status":"COMPLETED"}
```
Valores: type = `TASK | EXAM | PROJECT`; priority = `LOW | MEDIUM | HIGH`; status = `PENDING | IN_PROGRESS | COMPLETED`.

## Despliegue en Render + MongoDB Atlas
1. Publicar este proyecto en GitHub.
2. Crear un clúster y usuario de base de datos en MongoDB Atlas; configurar el acceso de red para permitir la conexión del servicio en Render. No subir las credenciales al repositorio.
3. Copiar la URI `mongodb+srv://.../agenda_academica?retryWrites=true&w=majority` (reemplazando usuario/contraseña por los reales).
4. Crear en Render un **Web Service**, conectar el repositorio y seleccionar **Docker**.
5. Agregar la variable de entorno `MONGODB_URI` con la cadena de conexión completa.
6. Desplegar y visitar `https://TU-SERVICIO.onrender.com/actuator/health`.
7. Probar los endpoints desde Postman usando la URL pública.

Render utiliza su variable `PORT`; `application.yml` ya la lee. Nunca subas `.env` ni contraseñas.

## Estructura
- `controller`: endpoints HTTP
- `service`: lógica académica
- `repository`: acceso a MongoDB
- `model`: Subject, Activity y enumeraciones
- `dto`: validaciones de entrada y resumen
- `exception`: errores 400, 404 y 409

**Nota:** Los métodos de filtrado cargan las actividades en memoria, suficiente para esta entrega pequeña. No existe separación entre usuarios; evita almacenar información personal sensible. No se ha realizado aún una prueba de integración contra MongoDB Atlas o Render.
