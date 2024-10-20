package pl.ateam.disasteralerts.disasteralert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
class OpenWeatherClient {

    private final RestTemplate restTemplate;

    DisasterDTO getWeather(String location) {
        return null;
    }
}
