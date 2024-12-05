package pl.ateam.disasteralerts.API;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
class OpenWeatherClient {

    @Value("${openweathermap.api.url}")
    private String apiUrl;

    @Value("${openweathermap.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    JsonNode getWeatherData(String location) {
        String url = String.format("%s?q=%s&appid=%s&units=metric", apiUrl, location, apiKey);
        try {
            return restTemplate.getForObject(url, JsonNode.class);
        } catch (Exception e) {
            log.error("Error while fetching weather data for location {}: {}", location, e.getMessage());
            return null;
        }
    }
}
