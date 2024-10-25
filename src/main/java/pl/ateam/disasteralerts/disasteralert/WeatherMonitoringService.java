package pl.ateam.disasteralerts.disasteralert;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class WeatherMonitoringService {

    public static final int ONE_HOUR = 3600000;
    public static final String OPEN_WEATHER_API = "OpenWeatherAPI";
    public static final String DESCRIPTION_HURRICANE = "Uwaga! Silne wichury";
    public static final String DESCRIPTION_HEAT = "Uwaga! Upały";
    private final OpenWeatherClient openWeatherClient;
    private final DisasterService disasterService;

    private final String monitoredLocation = "Warszawa";

    @Scheduled(fixedRate = ONE_HOUR)
    void monitorWeather() {
        log.info("Monitoring weather for location: {}", monitoredLocation);

        JsonNode weatherData = openWeatherClient.getWeatherData(monitoredLocation);
        if (weatherData != null) {
            log.error("No weather data received for location {}", monitoredLocation);
        }

        double windSpeed = weatherData.path("wind").path("speed").asDouble();
        double temperature = weatherData.path("main").path("temp").asDouble();

        handleWindDisaster(windSpeed);
        handleHeatDisaster(temperature);
    }

    private void handleWindDisaster(double windSpeed) {
        if (windSpeed > 2.0) {
            Optional<DisasterDTO> existingDisaster = disasterService.getActiveDisasterForTypeAndLocation(DisasterType.HURRICANE, monitoredLocation);
            if (existingDisaster.isEmpty()) {
                DisasterAddDTO disaster = new DisasterAddDTO(
                        DisasterType.HURRICANE,
                        DESCRIPTION_HURRICANE,
                        OPEN_WEATHER_API,
                        monitoredLocation,
                        LocalDateTime.now(),
                        DisasterStatus.ACTIVE,
                        null
                );
                disasterService.addDisaster(disaster);
                log.info("New wind disaster recorded for location: {}", monitoredLocation);
            } else {
                log.info("Wind disaster already exists for location: {}. Skipping.", monitoredLocation);
            }
        }
    }

    private void handleHeatDisaster(double temperature) {
        if (temperature > 35.0) {
            Optional<DisasterDTO> existingDisaster = disasterService.getActiveDisasterForTypeAndLocation(DisasterType.HEAT, monitoredLocation);
            if (existingDisaster.isEmpty()) {
                DisasterAddDTO disaster = new DisasterAddDTO(
                        DisasterType.DROUGHT,
                        DESCRIPTION_HEAT,
                        OPEN_WEATHER_API,
                        monitoredLocation,
                        LocalDateTime.now(),
                        DisasterStatus.ACTIVE,
                        null
                );
                disasterService.addDisaster(disaster);
                log.info("New heat disaster recorded for location: {}", monitoredLocation);
            } else {
                log.info("Heat disaster already exists for location: {}. Skipping.", monitoredLocation);
            }
        }
    }
}
