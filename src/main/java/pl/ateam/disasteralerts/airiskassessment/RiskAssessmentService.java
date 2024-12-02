package pl.ateam.disasteralerts.airiskassessment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.ateam.disasteralerts.disaster.dto.DisasterAddDTO;

@Service
@RequiredArgsConstructor
@Slf4j
class RiskAssessmentService {

    public static final double RISK_THRESHOLD = Double.parseDouble(System.getenv("RISK_THRESHOLD"));
    private final OpenAIClient openAIClient;

    boolean assessRisk(DisasterAddDTO disasterAddDTO) {
        String riskEvaluationPrompt = String.format(
                "Rate the probability from 0.0 to 1.0 (where 0.0 is no hazard and 1.0 is very likely) for the following disaster data: "
                        + "Location: %s ,Poland Type: %s Description: %s"
                        + " Give the result alone, a number, without describing the situation",
                disasterAddDTO.location(),
                disasterAddDTO.description(),
                disasterAddDTO.type()
        );

        try {
            double riskScore = openAIClient.getRiskScore(riskEvaluationPrompt);
            log.info(String.valueOf(riskScore));
            return riskScore > RISK_THRESHOLD;
        } catch (Exception e) {
            log.error("Error assessing risk with OpenAIClient: {}", e.getMessage());
            return false;
        }
    }
}
