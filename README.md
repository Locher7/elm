# ELM Ordering System

This repository documents the development of an Eleme-inspired online food ordering system, implemented through an incremental technological approach from JDBC to Spring Boot, and ultimately to Spring Cloud. The platform integrates comprehensive features including user registration, authentication, order management, and merchant administration, making it a robust system for managing multi-stakeholder interactions in the food delivery ecosystem.

## Table of Contents

1. [Overview](#overview)
2. [Technological Evolution](#technological-evolution)
   - [JDBC-Based Architecture](#jdbc-based-architecture)
   - [Transition to Spring Boot](#transition-to-spring-boot)
   - [Spring Cloud Implementation](#spring-cloud-implementation)
3. [Repository Structure](#repository-structure)
4. [Installation and Setup](#installation-and-setup)
5. [Future Directions](#future-directions)

## Overview

This project began with a goal of emulating Eleme’s food delivery workflow through a foundational model, progressively integrating more complex backend functionalities. The evolution from JDBC to Spring Cloud marks a transition from a monolithic to a microservices architecture, enhancing scalability, maintainability, and operational efficiency.

## Technological Evolution

### JDBC-Based Architecture

In the initial phase, the system employed JDBC for direct database interactions, enabling a basic two-layered structure of view and data layers. This stage focused on the core functionalities: user registration, login, merchant and item management, and order tracking. JDBC’s direct data handling provided a solid foundation for early development; however, limitations in scalability and modularity indicated the need for a more sophisticated framework.

#### Key Features:

- **Two-layer Architecture**: Simplified structure separating view and data layers.
- **Database Interaction**: Direct SQL queries for CRUD operations on entities such as users, merchants, and items.

### Transition to Spring Boot

The project then adopted Spring Boot to enhance system modularity and dependency management. By introducing a layered architecture (Controller, Service, and DAO layers), Spring Boot allowed for a more organized and manageable codebase. This shift simplified dependency injection and enabled seamless integration of RESTful APIs, facilitating both frontend-backend communication and external service interoperability.

#### Key Enhancements:

- **Layered Architecture**: Clear separation of concerns across Controller, Service, and Data Access layers.
- **REST API Support**: Enabled standardized communication across client applications and backend services.
- **Improved Dependency Management**: Leveraged Spring Boot’s dependency management to streamline configuration and module integration.

### Spring Cloud Implementation

The final and most significant phase introduced Spring Cloud, transforming the system into a fully distributed microservices architecture. Utilizing Spring Cloud’s ecosystem, each functional component operates as an independent service, promoting horizontal scalability and resilience. This architecture harnesses the power of Spring Cloud's service discovery, load balancing, and centralized configuration capabilities to meet the demands of dynamic load distribution and high availability.

#### Key Features:

- **Microservices Architecture**: Independent services for users, orders, merchants, and inventory, enhancing scalability and resilience.
- **Service Discovery with Eureka**: Dynamic discovery of services within the network, enabling automated load balancing and routing.
- **Centralized Configuration**: Simplified service configuration management across distributed environments.
- **Circuit Breakers and Resilience Patterns**: Ensured service continuity and fault tolerance through resilient handling of service failures.

### Comparative Insights

The transition from JDBC to Spring Cloud exemplifies the technological maturation needed to meet production-scale demands. While JDBC provided a straightforward approach to data persistence, Spring Boot introduced structural improvements through a layered MVC architecture. Spring Cloud’s microservices approach further amplified these improvements, enabling independent deployment and management of services, essential for large-scale operations.

## Repository Structure

- **JDBC-Branch**: Contains the initial JDBC implementation with SQL-based CRUD operations.
- **SpringBoot-Branch**: Spring Boot implementation, incorporating REST APIs and modularized architecture.
- **SpringCloud-Branch**: Final Spring Cloud microservices configuration, utilizing Eureka, Feign, and centralized configuration management.

## Installation and Setup

To deploy the Spring Cloud version of this project, follow these instructions:

1. Ensure all required services (Eureka Server, Config Server, and microservices) are configured and run.
2. Use Docker Compose or Kubernetes to orchestrate microservices in a containerized environment.
3. For a detailed setup, refer to the installation documentation in the SpringCloud-Branch.

## Future Directions

Future improvements will focus on enhancing the resilience of microservices, integrating a CI/CD pipeline, and implementing distributed tracing to monitor inter-service communications. Additionally, adopting serverless architecture for selected functions could provide cost-efficient scalability.
