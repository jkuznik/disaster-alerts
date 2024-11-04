package pl.ateam.disasteralerts.airiskassessment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;

@Service
@RequiredArgsConstructor
class RiskAssessmentService {

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

        double riskScore = openAIClient.getRiskScore(riskEvaluationPrompt);

        if (riskScore > 0.7) {
            return true;
        }
        return false;
    }
}
