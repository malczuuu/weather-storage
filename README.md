# 🌦️ Weather Storage 🌦️

A simple [**Spring Boot**][spring-boot] application that periodically fetches weather data from
[**OpenWeatherMap**][openweathermap] and stores it in [**MongoDB**][mongo].

This project was created as a learning exercise to:

- compare different alternatives to [**MockServer**][mockserver], because of its lack of updates since 2023,
- explore [**Testcontainers**][testcontainers] for writing integration testing with **MongoDB** and **WireMock**.

> **Note** that it was decided to use [**WireMock**][wiremock] project simulate integrated APIs, however
> [`mockserver-neolight`][mockserver-neolight] project, which is a "slimmed-down" fork of the original project also
> seems promising and has (somewhat limited) compatibility with it.

## Table of Contents

- [Key Learning Points](#key-learning-points)
- [How It Works](#how-it-works)
- [Running the App](#running-the-app)

## Key Learning Points

To summarize this exercise, following points were noted.

1. See [`TestcontainersConfiguration`][TestcontainersConfiguration] to see how MongoDB was configured for testing.
2. See [`OpenWeatherMapWeatherClientTest`][OpenWeatherMapWeatherClientTest] test class to see how **WireMock** was
   configured for testing.

   The different approach from `MongoDbContainer` setup is due to the fact that Spring Boot testing library does not
   support **WireMock** out of the box. See also [`OpenWeatherMapAware`][OpenWeatherMapAware] interface used for generic
   `@DynamicPropertySource` of `openweathermap.api.url` to point to **WireMock**.
3. See [`WeatherProcessorImplTest`][WeatherProcessorImplTest] test class to see how both **WireMock** and **MongoDB** is
   used in tests.

   Note that for MongoDB this test don't do anything, but [`OpenWeatherMapAware`][OpenWeatherMapAware] interface is
   used explicitly.
4. See [`docker-compose.yaml`][docker-compose.yaml] to see how **MongoDB** and **WireMock** were launched for local
   development. **Note** that **WireMock** allows static files loaded as Docker volumes for mock mappings.

## How It Works

The application periodically sends requests to a weather API and stores the returned weather data in **MongoDB**. Data
includes:

- Temperature
- Humidity
- Pressure
- Weather conditions (e.g., clouds, rain)

You can configure the source of weather data via application properties.

```yaml
openweathermap:
  enabled: false
  api:
    url: https://api.openweathermap.org
    token: verysecret
    endpoint:
      weather: ${openweathermap.api.url}/data/2.5/weather
      forecast: ${openweathermap.api.url}/data/2.5/forecast
```

---

## Running the App

Prepare local environment based on `localhost/` subdirectory (and its [`docker-compose.yaml`][docker-compose.yaml]).

> **\[Optional]** Create **OpenWeatherMap** account and generate API token.
>
> Set this in `application.yaml` or set other configurations. See table below.
>
> | application property     | JVM option               | environment variable       |
> |--------------------------|--------------------------|----------------------------|
> | `openweathermap.enabled` | `OPENWEATHERMAP_ENABLED` | `-Dopenweathermap.enabled` |
> | `openweathermap.api.url` | `OPENWEATHERMAP_API_URL` | `-Dopenweathermap.api.url` |

Turn on Spring application ([`Application`][Application] class or `./gradlew bootRun` Gradle task).

> Set `SPRING_PROFILES_ACTIVE=local` (or JVM option `-Dspring.profiles.active=local`) if you didn't use API token to
> rely on **WireMock** instance from mentioned `docker-compose.yaml`.

Observe logs and data being loaded into **MongoDB** every 30 seconds.

For running tests use `./gradlew test`. **Note** that because of depending on **Testcontainers**, working Docker engine
installation is required.

[spring-boot]: https://spring.io/projects/spring-boot

[openweathermap]: https://openweathermap.org/api

[mongo]: https://hub.docker.com/_/mongo

[testcontainers]: https://testcontainers.com/

[mockserver]: https://github.com/mock-server/mockserver

[wiremock]: https://github.com/wiremock/wiremock

[mockserver-neolight]: https://github.com/xdev-software/mockserver-neolight

[openweathermap]: https://openweathermap.org/api

[TestcontainersConfiguration]: src/test/java/io/github/malczuuu/weather/storage/testcontainers/TestcontainersConfiguration.java

[OpenWeatherMapWeatherClientTest]: src/test/java/io/github/malczuuu/weather/storage/infrastructure/openweathermap/OpenWeatherMapWeatherClientTest.java

[OpenWeatherMapAware]: src/test/java/io/github/malczuuu/weather/storage/infrastructure/testcontainers/OpenWeatherMapAware.java

[WeatherProcessorImplTest]: src/test/java/io/github/malczuuu/weather/storage/application/weather/WeatherProcessorImplTest.java

[docker-compose.yaml]: ./localhost/docker-compose.yaml

[Application]: src/main/java/io/github/malczuuu/weather/storage/Application.java
