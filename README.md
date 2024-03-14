# Restaurant Recommendation API

## Technologies

- Restaurant Service
    - JDK 17
    - Spring Boot 2.7.7
    - Solr 9.5
    - Kafka
- User Service
    - JDK 21
    - Spring Boot 3.2.3
    - Postgres 16
    - Redis
    - Kafka
    - jUnit 5
    - Mockito
- Docker

## Architecture

![architecture](./docs/architecture.png)

## Features

- Create, update, delete and list restaurants
- Create, update, delete and list users
- Create, update (score and comment), delete and list reviews
- Get recommendations by user id
- Get recommendations by location (latitude and longitude)
- Pagination and sorting for `GET /api/v1/restaurants`, `GET /api/v1/users` and `GET /api/v1/reviews`
- Search restaurant by name
- Unit and integration tests for user service application
- Validation for all endpoints

## Rooms for improvement
- Create separate APIs for users, reviews, and recommendations
- Apply CQRS pattern to services, especially for restaurant service.
    - Postgres for write operations
    - Solr for read operations
- Add more unit and integration tests for restaurant service application
- gRPC for communication between services might be a better choice than REST

## How to run

### Docker

All services have their own Dockerfile and can be run using docker-compose.
Docker VM on host machine should have at least 2GB of RAM and 4 CPUs otherwise you may encounter problems when compiling
or running services.

```bash
docker compose up -d
```

After running this command it will download some images and build the services.
This process may take up to 10 minutes depends on your bandwidth speed.

### Local/IDE

If you would like to run services locally, open the project with your IDE.
At first IDEA may ask for importing maven projects, click to yes.
You need to run some services on Docker:

- Solr
- Postgres
- Redis
- Kafka
- Zookeeper

After starting these services, you can start the Restaurant Service `(:8082)`.
Then you can start the User Service `(:8081)`.

### Swagger UI

- User Service: http://localhost:8081/swagger-ui/index.html
- Restaurant Service: http://localhost:8082/swagger-ui/index.html

## Running tests

You need to start `test-postgres` service before running the services.

![test](./docs/tests.png)

## API Preview

### Restaurant Service

![restaurant endpoints](./docs/restaurant-service-endpoints.png)

### User Service

![user endpoints](./docs/user-service-endpoints.png)