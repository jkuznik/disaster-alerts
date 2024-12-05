package pl.ateam.disasteralerts.API;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeatherMonitoringFacade {

    private final WeatherMonitoringService weatherMonitoringService;
}
