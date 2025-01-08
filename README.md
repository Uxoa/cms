Aerolinea Factoría F5
Giacomo F5 Classroom
•
1:47 PM (Edited 1:53 PM)
Spring boot
•
100 points
Due Jan 31
Descripción del Proyecto:
El proyecto tiene como objetivo desarrollar un sistema de gestión para una aerolínea utilizando Spring con Spring Boot y Spring Security. Este sistema permitirá la gestión integral de usuarios, vuelos, reservas y destinos, con funcionalidades avanzadas como autenticación segura mediante Basic Auth o JWT. El sistema no puede permitir la selección  de vuelos sin plazas disponibles o que hayan superado la fecha límite. El proyecto se implementará utilizando Java 21, Maven y MySQL o PostgreSQL.

Objetivos del Proyecto:
Reforzar los conceptos de creación de APIs.
Aplicar relaciones de BBDD.
Asentar conocimientos de login con Spring Security y Basic Auth o JWT.
Requisitos funcionales del Proyecto:
Gestión de Clientes
Registro, autenticación y manejo de roles (ROLE_ADMIN y ROLE_USER).
Generación y validación de tokens JWT o de las COOKIES de sesión en caso de usar Basic Auth para sesiones seguras.
Gestión de Vuelos:
Los vuelos deben generarse automáticamente en base de datos al momento de compilación. (Mediante archivo .sql)
Cambio de estado del vuelo disponible a “false” cuando el vuelo quede sin plazas disponibles o fuera de fecha.
Buscador:
Se deberá proporcionar el aeropuerto de salida así como el aeropuerto de destino. La fecha y el número de plazas a reservar. No es necesario catalogar el tipo de plazas.
Gestión de Reservas:
Permitir realizar reservas de vuelos solo si existe el trayecto seleccionado y si hay disponibilidad de plazas.
Verificación de disponibilidad antes de confirmar una reserva.
Una vez iniciada la gestión de la reserva del vuelo, el sistema deberá  bloquear las plazas durante un periodo de 15 minutos para garantizar la disponibilidad de los asientos.
Gestiones autorizadas para el usuario ADMIN (ROLE_ADMIN):
CRUD de aeropuertos
CRUD de los trayectos de vuelos.
Listado resumido de las reservas efectuadas por parte de los clientes.
Debemos poder obtener la lista del historial de reservas por cada usuario (ROLE_USER)
Gestiones autorizadas para el usuario CLIENTE (ROLE_USER):
Deberá poder registrarse
Deberá tener la posibilidad de hacer un upload de su imagen de perfil y encaso de no tenerla configurada entonces se mostrará una por defecto.
Deberá poder hacer login
Los clientes deberán poder obtener el listado de sus reservas con información de los vuelos
Los clientes no podrán realizar una reserva sin previo login.
Gestión de Excepciones:
Poder manejar las excepciones de manera personalizada.
 Requisitos No Funcionales
Seguridad: Uso de Spring Security y a elegir entre Basic Auth o JWT para proteger la API.
Rendimiento: Optimizaciones como el cambio automático de estados de los vuelos y la validación de reservas para mantener la eficiencia del sistema.
Disponibilidad: Implementación de Test para asegurar la estabilidad del sistema en producción.
Extras:
Dockerizar y subir imagen a Docker Hub
Uso de GitHub Action para automatizar la parte de CI (solo build y pasar los tests)
Automatizar pruebas con Postman
Requisitos técnicos del Proyecto:
Conocimientos de programación en Java.
Conocimientos en Programación Orientada a Objetos (POO).
Spring, Spring boot & Spring Security
Basic Auth & JWT
Testing
Docker
Conocimientos BBDD (relaciones, precarga de datos, etc)
Modalidades de evaluación:
Individual
Entregables Esperados:
Código fuente del backend desarrollado en Spring Boot. (Enlace al repositorio de GitHub)
Colección de Postman con todos los endpoints y pruebas realizadas. (Solo mostrar durante la revisión)
Documentación de la API. (Readme completo, Diagramas, Listado de endpoints, etc ...)
Presentación del proceso que se ha seguido para desarrollar la API.
Imagen de la aplicación subida a Docker Hub (Extra)
Cronograma del Proyecto:
1 Sprint de 4 semanas
Inicio -> Martes 07/01/2025
Review final individual -> Jueves 30/01/2025 y Viernes 31/01/2025 (todo el día)
Entrega 31/01/2025
Marcos de competencias:
Gestión de proyecto con metodologías ágiles
Desarrollar la API de una aplicación web
Administrar bases de datos
Desarrollar y Ejecutar pruebas
