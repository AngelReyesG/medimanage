# MediManage - Backend API 🏥

Este repositorio contiene el núcleo del backend y la API REST para **MediManage**, una plataforma integral de software como servicio (SaaS) diseñada para optimizar la gestión operativa, el control de expedientes clínicos y el agendamiento automatizado de citas en clínicas médicas y consultorios particulares.

## 🚀 Características del Proyecto

- **Arquitectura Limpia en Capas:** Estructuración profesional dividida en Entidades, Repositorios, Servicios y Controladores para garantizar la escalabilidad del código.
- **Persistencia de Datos Avanzada:** Mapeo relacional de datos (ORM) eficiente para entidades complejas (`Usuarios`, `Pacientes`, `Citas`).
- **Optimización de Rendimiento:** Implementación de carga perezosa (`FetchType.LAZY`) en relaciones Muchos a Uno (`@ManyToOne`) para reducir el consumo de memoria en consultas masivas.
- **Seguridad y Buenas Prácticas:** Configuración desacoplada del entorno mediante el uso estricto de **Variables de Entorno**, protegiendo por completo las credenciales de infraestructura.

## 🛠️ Tecnologías Utilizadas

- **Java 17/21** (Soporte a Largo Plazo - LTS)
- **Spring Boot 3.x** (Ecosistema empresarial)
- **Spring Data JPA** (Abstracción y persistencia de datos)
- **Spring Web** (Construcción de la API REST)
- **MySQL** (Motor de base de datos relacional)
- **Lombok** (Optimización y reducción de código repetitivo)
- **Clever Cloud** (Infraestructura de base de datos en la nube)
- **Maven** (Gestión de dependencias y automatización de construcción)

## 📁 Estructura del Código

El proyecto sigue el estándar de la industria para el desarrollo backend con Spring Boot:

```text
src/main/java/com/medimanage/backend/
│
├── entities/       # Modelos de datos y mapeo de tablas JPA (Usuario, Paciente, Cita)
├── repositories/   # Interfaces de acceso a datos con Spring Data JPA (CRUD)
└── BackendApplication.java  # Clase principal y punto de arranque del sistema