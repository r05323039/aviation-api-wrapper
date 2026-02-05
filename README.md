> ## ⚠️ Time Management & Scope Note
>
> Although I had a flexible timeframe for submission, I adhered to the spirit of the **90-minute guideline** by prioritizing core application logic and resilience patterns over complex infrastructure.
>
> ### 🎯 What I focused on:
> * **Production-Grade Code**: Implemented strict Input Validation, Global Error Handling, and Local Caching (Caffeine).
> * **Resilience**: Robust Circuit Breaker & Retry implementation validated with comprehensive integration tests (**WireMock**).
> * **Observability**: Exposed standard Prometheus metrics via Spring Boot Actuator.
>
> ### 🏗️ Infra Decision:
> I included a lightweight `docker-compose.yml` with **Prometheus** to verify `metrics readiness`. I intentionally omitted complex setups like Grafana/ELK/RabbitMQ to avoid **over-engineering** for this specific assignment scope.