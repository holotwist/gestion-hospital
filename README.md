# Gestión Hospitalaria

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

Este proyecto es una aplicación web para la gestión de un hospital, desarrollada para el programa Programación II (2025-1).  Aplicando los principios SOLID y el uso de buenas prácticas de desarrollo. Incluye la gestión de pacientes, médicos, citas y la configuración global del hospital.

## Problema Planteado

Una empresa de gestión hospitalaria necesita un sistema para administrar la información de pacientes, médicos, citas y las configuraciones globales del hospital. El sistema debe:

1.  **Gestionar Pacientes, Médicos y Citas:**
    *   Implementar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para pacientes y médicos.
    *   Permitir reservar y cancelar citas.
    *   Listar citas por fecha.
    *   Identificar pacientes con nombres palíndromos.
    *   Identificar pacientes con al menos dos vocales iguales en su nombre.

2.  **Gestor de Configuración del Hospital (Singleton):**
    *   Tener una única instancia de un gestor de configuración que almacene:
        *   Horario de atención.
        *   Número máximo de pacientes por médico.
        *   Reglas de facturación.
    *   Permitir que cualquier parte del sistema acceda y modifique esta configuración, pero evitar la creación de múltiples instancias.

3.  **Sistema de Clonación de Pacientes:**
    *   Permitir la clonación de historiales médicos de pacientes para casos recurrentes.
    *   La clonación debe incluir:
        *   Nombre
        *   Edad
        *   Historial de enfermedades
        *   Medicamentos
    *   El nuevo paciente (clon) debe ser personalizable sin afectar al paciente original.  El ID del clon debe ser diferente.

## Solución Implementada

Se desarrolló una aplicación web utilizando Spring Boot y Vaadin, con una interfaz de usuario intuitiva y funcionalidades para gestionar pacientes, médicos, citas y la configuración del hospital. La aplicación se estructura en capas, siguiendo los principios SOLID, y utiliza un patrón Singleton para el gestor de configuración y Prototype para la clonación de pacientes.

## Tecnologías Utilizadas

*   **Spring Boot:** Framework de Java para crear aplicaciones web y servicios de forma rápida y sencilla.
*   **Vaadin:** Framework de código abierto para construir interfaces de usuario web modernas con Java.
*   **Lombok:** Librería que reduce el código repetitivo (getters, setters, etc.) mediante anotaciones.
*   **H2 Database:** Base de datos en memoria, ideal para desarrollo y pruebas.
*   **Spring Data JPA:** Simplifica el acceso a la base de datos, generando implementaciones de repositorios automáticamente.

## Arquitectura

La aplicación sigue una arquitectura en capas:

*   **Presentación (View):**  Interfaz de usuario construida con Vaadin.  Presenta la información y permite la interacción del usuario.
*   **Control (Controller):** En este caso la capa de presentación y de control se unifican al usar Vaadin, los componentes y las vistas actuan de controlador, manejando eventos de usuario.
*   **Servicio (Service):** Contiene la lógica de negocio de la aplicación (operaciones CRUD, gestión de citas, etc.).
*   **Acceso a Datos (Repository):**  Interfaces que interactúan con la base de datos (usando Spring Data JPA).
*   **Modelo (Model):**  Clases que representan las entidades del dominio (Paciente, Médico, Cita, Configurador, etc.).
*   **Gestor (Utility):** Clases utilitarias, en este caso usada para la gestión de pacientes especiales.

## Principios SOLID

El proyecto se adhiere a los principios SOLID:

*   **Single Responsibility Principle (SRP):** Cada clase tiene una única responsabilidad (por ejemplo, `Paciente` representa a un paciente, `HospitalService` gestiona la lógica de negocio).
*   **Open/Closed Principle (OCP):**  La funcionalidad se puede extender sin modificar el código existente (por ejemplo, se podrían añadir nuevas reglas de facturación sin cambiar la clase `Configurador`).
*   **Liskov Substitution Principle (LSP):**  `Paciente` y `Medico` heredan de `Persona`, se comportan como personas, y se espera.
*   **Interface Segregation Principle (ISP):** No hay interfaces "grandes"; este proyecto no presenta interfaces complejas que requieran segregación.
*   **Dependency Inversion Principle (DIP):**  Se utiliza la inyección de dependencias de Spring.  Por ejemplo, `HospitalService` depende de las interfaces de los repositorios, no de las implementaciones concretas.

## Patrones de Diseño

*   **Singleton:**  La clase `Configurador` utiliza el patrón Singleton para asegurar que solo exista una instancia de la configuración del hospital.
* **Prototype:** La clase `Paciente` implementa la interfaz `Cloneable` y el metodo `clone` para permitir clonar pacientes.
* **Utility Class:** La clase `GestorPacientes` dentro de la carpeta `gestor` es una clase de utilidad que se usa para los pacientes especiales.

## Configuración e Instalación

1.  **Requisitos:**

    *   Java 17 o superior.
    *   Maven.
    *   IDE recomendado: IntelliJ IDEA (Community o Ultimate).

2.  **Clonar el repositorio:**

    ```bash
    git clone https://github.com/holotwist/gestion-hospital
    cd gestionhospital
    ```

3.  **Generar JAR de producción y ejecutar:**

    ```bash
    mvn clean package -Pproduction
    java -jar target/gestion-hospital-0.0.1-SNAPSHOT.jar # El nombre del JAR puede variar
    ```

4.  **Acceder a la aplicación:**

    Abre tu navegador y ve a `http://localhost:8080`.

## Uso

La aplicación permite:

*   **Gestionar Pacientes:** Crear, editar, eliminar y clonar pacientes.
*   **Gestionar Médicos:** Crear, editar y eliminar médicos.
*   **Gestionar Citas:** Crear, editar, eliminar y buscar citas por fecha.
*   **Configuración del Hospital:** Modificar el horario de atención, el máximo de pacientes por médico y las reglas de facturación (con opciones predefinidas en un menú desplegable).
* **Pacientes Especiales:** Listar pacientes con nombres palíndromos o con dos vocales iguales.

La interfaz de usuario proporciona un menú lateral para navegar entre las diferentes secciones (Pacientes, Médicos, Citas, Configuración, Pacientes Especiales).  Cada sección tiene su propia vista con los formularios y tablas correspondientes.
## Licencia

Este proyecto está licenciado bajo la [Licencia MIT](LICENSE).
