package pl.ateam.disasteralerts.airiskassessment;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
class OpenAIClient {

    private final RestTemplate restTemplate;

    double getRiskScore(String prompt) {
        JsonNode response = restTemplate.getForObject(null, JsonNode.class);

        return response.path("choices").get(0).path("score").asDouble();
    }
}
