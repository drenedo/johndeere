## Before everything, some conclusions after the start

### Regarding data and aggregate values

At first glance after reading the code challenge, I thought the most complicated part would be the concurrence and aggregation, the aggregation’s value could be easily corrupted. Locking, in this case optimistic locking, could be the correct solution. But thinking more it sound something crazy, manage locking will complicate the code. It would require manage exceptions, retries, DLQs…

Another way is delegate the aggregate values to database. Aggregate functions in a RDBMS are inefficient and perform poorly. Run a query on a relation data base with sum function is not the way, also if the amount of data is not big.

Finally following KISS principle, I chose store data in PostgreSQL with simple aggregation based on a trigger. This is not a perfect solution, but it is easy and pragmatic. May be internal looking in database could be a problem with inserts but I don’t know how many event there will be per session. Many of them could be problematic but if there are, for example, one per minute it should work like a charm. In other cases we could use pg_ivm (Materialized views in real time), pg_duck (A extension to use duckdb into postgresql), timescale (A great solution for incremental aggregation). Also postgresql driver is used in other columnar databases like Aurora witch is very efficient executing aggregate functions.

### Regarding framework and libraries

I chose the more productive way, at least for me, over performance due to time constraints. For that reason I chose JPA over JOOQ or Spring over Vert.x or k8s configuration over Terraform.

### I ignored the queue

Another time... due to time constraints, I chose to ignore the queue and made endpoints instead of queue clients. It is easiest and more productive, at least for me. Hexagonal architecture should help when implementing other ports in the future.

## Requirements

- Docker-API compatible container runtime to run testcontainers
- Java 23
- Gradle or alternatively the gradle wrapper

## Swagger

The project has a swagger UI to test and document the endpoints.

It is available at http://localhost:8080/swagger-ui.html

# k8s

## Build image

Very simple, use gradle to build the project and docker to build the image.

```shell
gradle build
docker build -t renedo/johndeere .
```

## Actuator endpoints

Readiness and liveness probes are used to check the state of the application in Kubernetes.

- /actuator/health
- /actuator/health/readiness
- /actuator/health/liveness

## Configuration

It's is under `k8s` folder. The configuration has tree files:

[deployment.yml](k8s/deployment.yml) The descriptor of the pod, resources and so on. Needs some changes, for example the name of the imagen an a real repository url. This file will be used in the deployments modifying the image version.

[scale.yml](k8s/scale.yml) The descriptor of the horizontal pod autoscaler, the k8s service needs HorizontalPodAutoscaler installed.

[service.yml](k8s/service.yml) The descriptor of the service, a simple load balancer in the 8080 port. 
