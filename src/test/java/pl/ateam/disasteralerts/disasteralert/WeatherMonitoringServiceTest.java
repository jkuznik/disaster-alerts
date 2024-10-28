package pl.ateam.disasteralerts.disasteralert;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.util.CitiesInPoland;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherMonitoringServiceTest {

    @Mock
    private OpenWeatherClient openWeatherClient;

    @Mock
    private DisasterService disasterService;

    @InjectMocks
    private WeatherMonitoringService weatherMonitoringService;

    private JsonNode createWeatherData(double windSpeed, double temperature) throws Exception {
        String json = String.format("{\"wind\": {\"speed\": %s}, \"main\": {\"temp\": %s}}", windSpeed, temperature);
        return new ObjectMapper().readTree(json);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        openWeatherClient = mock(OpenWeatherClient.class);
        weatherMonitoringService = new WeatherMonitoringService(openWeatherClient, disasterService);
    }

    @Test
    void shouldMonitorWeatherForAllLocations() throws Exception {
        // Given
        List<String> cities = CitiesInPoland.getList();
        when(openWeatherClient.getWeatherData(anyString())).thenReturn(createWeatherData(3.5, 30.0));

        // When
        weatherMonitoringService.monitorWeather();

        // Then
        for (String city : cities) {
            verify(openWeatherClient).getWeatherData(city);
        }
    }

    @Test
    void shouldNotRecordDisastersWhenThresholdsNotExceeded() throws Exception {
        // Given
        JsonNode weatherDataWithLowWindAndHeat = createWeatherData(1.0, 2.0);
        when(openWeatherClient.getWeatherData(anyString())).thenReturn(weatherDataWithLowWindAndHeat);

        // When
        weatherMonitoringService.monitorWeather();

        // Then
        verify(disasterService, never()).createDisaster(any(DisasterAddDTO.class));
    }
}
