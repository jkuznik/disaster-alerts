package pl.ateam.disasteralerts.disasteralert;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;
import pl.ateam.disasteralerts.util.CitiesInPoland;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
class WeatherMonitoringService {

    public static final int ONE_HOUR = 3600000;
    public static final String DESCRIPTION_HURRICANE = "Uwaga! Silne wichury";
    public static final String DESCRIPTION_HEAT = "Uwaga! Upały";
    private final OpenWeatherClient openWeatherClient;
    private final DisasterService disasterService;
    private final String API_AS_DISASTER_SOURCE = "Weather Monitoring System";

    private final List<String> monitoredLocations = CitiesInPoland.getList();

    @Scheduled(fixedRate = ONE_HOUR)
    void monitorWeather() {
        for (String location : monitoredLocations) {
            log.info("Monitoring weather for location: {}", location);

            JsonNode weatherData = openWeatherClient.getWeatherData(location);
            if (weatherData == null) {
                log.error("No weather data received for location {}", location);
                continue;
            }

            double windSpeed = weatherData.path("wind").path("speed").asDouble();
            double temperature = weatherData.path("main").path("temp").asDouble();

            handleWindDisaster(windSpeed, location);
            handleHeatDisaster(temperature, location);
        }
    }

    private void handleWindDisaster(double windSpeed, String location) {
        if (windSpeed > 10.0) {
            Optional<DisasterDTO> existingDisaster = disasterService.getActiveDisasterForTypeAndLocation(DisasterType.HURRICANE, location);
            if (existingDisaster.isEmpty()) {
                DisasterAddDTO disaster = new DisasterAddDTO(
                        DisasterType.HURRICANE,
                        DESCRIPTION_HURRICANE,
                        location,
                        UUID.randomUUID()   //TODO: wygenerować uuid dedykowane dla WeatherMonitoring i na sztywno przypisać
                );
                disasterService.createDisaster(disaster, API_AS_DISASTER_SOURCE);
                log.info("New wind disaster recorded for location: {}", location);
            } else {
                log.info("Wind disaster already exists for location: {}. Skipping.", location);
            }
        }
    }

    private void handleHeatDisaster(double temperature, String location) {
        if (temperature > 35.0) {
            Optional<DisasterDTO> existingDisaster = disasterService.getActiveDisasterForTypeAndLocation(DisasterType.HEAT, location);
            if (existingDisaster.isEmpty()) {
                DisasterAddDTO disaster = new DisasterAddDTO(
                        DisasterType.DROUGHT,
                        DESCRIPTION_HEAT,
                        location,
                        UUID.randomUUID()   //TODO: wygenerować uuid dedykowane dla WeatherMonitoring i na sztywno przypisać
                );
                disasterService.createDisaster(disaster, API_AS_DISASTER_SOURCE);
                log.info("New heat disaster recorded for location: {}", location);
            } else {
                log.info("Heat disaster already exists for location: {}. Skipping.", location);
            }
        }
    }
}
