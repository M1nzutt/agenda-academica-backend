# Diagramas UML — Agenda Académica Estudiantil

> **Estado:** cinco vistas documentadas y sus cinco imágenes JPG publicadas en `docs/uml/`. El archivo editable `.vpp` todavía no consta en esta carpeta.

## Índice
1. [Vista de contexto](#1-vista-de-contexto)
2. [Vista funcional — componentes](#2-vista-funcional--componentes)
3. [Vista lógica — modelo de dominio](#3-vista-lógica--modelo-de-dominio)
4. [Vista de desarrollo — clases en capas](#4-vista-de-desarrollo--clases-en-capas)
5. [Vista de despliegue](#5-vista-de-despliegue)
6. [Consistencia y evidencias](#6-consistencia-y-evidencias)

## 1. Vista de contexto

![Vista de contexto](uml/Vista%2001%20-%20contexto%20corregida.jpg)

**Propósito:** identificar el límite del sistema y sus interacciones externas sin detallar clases o servicios internos.

- **Estudiante / Postman → Agenda Académica:** solicitudes HTTPS a la API REST.
- **Agenda Académica → Estudiante / Postman:** respuestas JSON y códigos HTTP.
- **Agenda Académica → MongoDB Atlas:** operaciones de consulta y almacenamiento.
- **MongoDB Atlas → Agenda Académica:** resultados de consultas y confirmaciones.

Postman es el cliente de prueba porque el alcance actual no incluye frontend. Render aloja la API y MongoDB Atlas mantiene los datos; el diagrama de despliegue precisa dónde funcionan.

## 2. Vista funcional — componentes

![Vista funcional](uml/Vista%2002%20-%20funcional.jpg)

**Propósito:** identificar responsabilidades funcionales y servicios consumidos/proporcionados.

**Elementos:** Postman; API REST (Spring Boot); Controladores REST; Gestión de materias; Gestión de actividades; Consulta de agenda; Persistencia MongoDB; MongoDB Atlas.

**Interacciones funcionales:**

| Solicitante | Proveedor | Contrato o uso |
|---|---|---|
| Postman | API REST | Solicitudes HTTP / respuestas JSON |
| API REST | Controladores REST | Recepción y distribución de peticiones |
| Controladores REST | Gestión de materias | GestiónMaterias: CRUD de materias |
| Controladores REST | Gestión de actividades | GestionActividades: CRUD y cambio de estado |
| Controladores REST | Consulta de agenda | ConsultaAgenda: agenda ordenada y resumen |
| Gestión de materias | Persistencia MongoDB | Acceso a materias y comprobación de actividades asociadas |
| Gestión de actividades | Persistencia MongoDB | Acceso a actividades y verificación de materia existente |
| Consulta de agenda | Persistencia MongoDB | Lectura de actividades para agenda y estadísticas |
| Persistencia MongoDB | MongoDB Atlas | Operaciones MongoDB/TLS |

**Notación:** las interfaces proporcionadas (círculo) y requeridas (semicírculo) representan contratos UML. Una interfaz consumida necesita estar ensamblada con la correspondiente ofrecida. El dibujo de varias líneas hacia `Repositorio` no implica que exista un único método genérico en código: en Java se utilizan `SubjectRepository` y `ActivityRepository`.

## 3. Vista lógica — modelo de dominio

![Vista lógica](uml/Vista%2003%20-%20logica.jpg)

**Propósito:** describir las entidades y restricciones de los datos sin mezclar la infraestructura.

### Subject
- `id: String`: identificador de MongoDB.
- `name: String`: nombre de la materia.
- `code: String`: código académico.
- `teacher: String`: docente.

### Activity
- `id: String`: identificador de MongoDB.
- `subjectId: String`: referencia a la materia.
- `title: String`, `description: String`.
- `type: ActivityType`, `priority: Priority`.
- `dueDate: LocalDate`, `status: ActivityStatus`.

**Relación:** `Subject 1 — 0..* Activity`. Cada actividad refiere una materia mediante `subjectId`; una materia puede tener cero o más actividades. Es una relación lógica por identificador, **no una llave foránea SQL**.

**Enumeraciones:**
- `ActivityType`: `TASK`, `EXAM`, `PROJECT`.
- `Priority`: `LOW`, `MEDIUM`, `HIGH`.
- `ActivityStatus`: `PENDING`, `IN_PROGRESS`, `COMPLETED`.

Las dependencias hacia las enumeraciones se justifican porque los tipos de los atributos de `Activity` las utilizan.

## 4. Vista de desarrollo — clases en capas

![Vista de desarrollo](uml/Vista%2004%20-%20desarrollo%20(Controller%2C%20Service%2C%20Repository%2C%20Model).jpg)

**Propósito:** explicar la organización del backend Java. Se representa mediante un **diagrama de clases UML en cuatro capas**, no mediante paquetes.

| Capa | Clases o interfaces | Responsabilidad |
|---|---|---|
| Controller | `SubjectController`, `ActivityController`, `AgendaController` | Exponer endpoints REST, recibir solicitudes y responder |
| Service | `SubjectService`, `ActivityService`, `AgendaService` | Ejecutar reglas de negocio |
| Repository | `SubjectRepository`, `ActivityRepository` | Proporcionar persistencia mediante Spring Data MongoDB |
| Model | `Subject`, `Activity` | Representar materias y actividades |

**Relaciones navegables:** `SubjectController → SubjectService`; `ActivityController → ActivityService`; `AgendaController → AgendaService`; `SubjectService → SubjectRepository` y `ActivityRepository`; `ActivityService → SubjectRepository` y `ActivityRepository`; `AgendaService → ActivityRepository`.

**Dependencias de tipo:** `SubjectRepository → Subject` y `ActivityRepository → Activity`.

**Regla importante:** `SubjectService` consulta `ActivityRepository` para impedir eliminar una materia que tiene actividades: el backend devuelve **HTTP 409 Conflict**.

## 5. Vista de despliegue

![Vista de despliegue](uml/Vista%2005%20-%20despliegue.jpg)

**Propósito:** mostrar los entornos donde opera la aplicación y sus rutas de comunicación.

| Nodo / artefacto | Función |
|---|---|
| Cliente Postman | Ejecuta peticiones de prueba |
| Render Web Service | Aloja el backend accesible por HTTPS |
| Docker / Java 21 JRE | Entorno de ejecución dentro del servicio |
| `agenda-academica-1.0.0.jar` | Artefacto desplegado de Spring Boot |
| MongoDB Atlas | Clúster administrado de base de datos |

**Conexiones:** Postman — **HTTPS** — Render; Render — **MongoDB/TLS** — Atlas. La relación UML `«deploy»` asocia el artefacto ejecutable con el entorno Java.

**API pública verificada:** https://agenda-academica-backend-bz64.onrender.com

## 6. Consistencia y evidencias

- La arquitectura corresponde al backend Java 21 / Spring Boot y sus dos colecciones principales: `subjects` y `activities`.
- Las 10 historias de usuario están publicadas en [GitHub Issues](https://github.com/M1nzutt/agenda-academica-backend/issues).
- Se verificaron en producción con Postman las operaciones básicas, el filtrado, la agenda, el resumen y los errores HTTP 400, 404 y 409.
- **Archivo editable:** el entregable formal de los diagramas es un proyecto `.vpp` de Visual Paradigm. Las imágenes solo sirven para consulta; no sustituyen el modelo.
- **Capturas:** las cinco exportaciones JPG originales están enlazadas en cada vista de esta página.

> **Nota de alcance:** la documentación representa el sistema entregado, sin frontend ni autenticación. No atribuye funciones a servicios que no existen.
