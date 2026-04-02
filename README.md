# Weather Reporting (Java + Spring Boot)

Minimal Spring Boot app that serves a small static UI. Select an Indian city and the app will fetch current temperature from OpenWeatherMap.

- Prerequisites
- Java 17+
- Maven
- An OpenWeatherMap API key (get one at https://openweathermap.org)
- Java 17+
- Maven
- An OpenWeatherMap API key (get one at https://openweathermap.org)

Setup
1. Export your API key (or enable mock mode):

```bash
# Real API key (recommended)
export OPENWEATHER_API_KEY=your_api_key_here

# OR enable mock mode for development (no API key required)
export WEATHER_MOCK=true
```

2. Run the app:

```bash
mvn spring-boot:run
```

3. Open http://localhost:8080 and pick a city.

Files added
- `pom.xml` — Maven build
- `src/main/java/com/example/weather/WeatherReportingApplication.java` — app entry
- `src/main/java/com/example/weather/controller/WeatherController.java` — REST endpoint `/api/weather`
- `src/main/java/com/example/weather/service/WeatherService.java` — calls OpenWeatherMap
- `src/main/resources/static/index.html` — static UI

Notes
- The app reads `OPENWEATHER_API_KEY` from the environment. If missing or the provider returns an error, you can enable `WEATHER_MOCK=true` to use deterministic fake temperatures for development.
- The `/api/weather` response includes a `mock` boolean when mock data is returned.
- This is a minimal scaffold — more features (caching, rate-limiting, improved error UI) can be added.
# weather-reporting