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

## How to run 

```shell
./gradlew bootRun
```

## k8s