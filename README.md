# Aviation API Wrapper Service

A robust, fault-tolerant wrapper service for the Aviation API, built with **Spring Boot 3**.
This project demonstrates **production-grade backend engineering practices**, focusing on resilience patterns, caching strategies, and comprehensive integration testing.

---

## ⚠️ Time Management & Scope Note

Although I had a flexible timeframe for submission, I adhered to the spirit of the **90-minute guideline** by prioritizing core application logic and resilience patterns over infrastructure configuration.

### 🎯 Key Focus Areas
* **Production-Grade Code**: Implemented strict Input Validation, Global Error Handling, and Local Caching (Caffeine).
* **Resilience**: Robust Circuit Breaker & Retry implementation, validated with comprehensive integration tests (**WireMock**).
* **Observability**: Exposed standard Prometheus metrics via Spring Boot Actuator (ready for scraping).

### 🏗️ Infrastructure Decision
I intentionally **omitted containerization artifacts** (Dockerfile/Compose) to strictly focus on **Java backend engineering**. While the application is Cloud-Native ready (Stateless, Actuator enabled), I prioritized **code quality and testing coverage** over infrastructure setup for this specific assignment scope.

### 🤖 AI Usage Declaration
This project utilized AI assistance (Gemini) for architectural guidance, boilerplate generation (e.g., Global Exception Handler structure), and refining the resilience configuration strategies.
---

## 🛠️ Tech Stack

* **Java 17**
* **Spring Boot 3.4.2** (Web, Actuator, AOP, Validation)
* **Spring Cloud OpenFeign** (Declarative HTTP Client)
* **Resilience4j** (Circuit Breaker, Retry, TimeLimiter)
* **Caffeine** (High-performance Local Caching)
* **WireMock** (Integration Testing)
* **Micrometer** (Metrics Facade)

---
