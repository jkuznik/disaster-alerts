package pl.ateam.disasteralerts.airiskassessment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;

@Service
@RequiredArgsConstructor
class RiskAssessmentService {

    private final OpenAIClient openAIClient;

    DisasterAddDTO assessRisk(DisasterAddDTO disasterAddDTO) {
        String riskEvaluationPrompt = String.format(
                "Evaluate the risk for the following disaster data:\n"
                        + "Location: %s\nType: %s\nDescription: %s\nReported By: %s\n",
                disasterAddDTO.location(),
                disasterAddDTO.description(),
                disasterAddDTO.type()
        );

        double riskScore = openAIClient.getRiskScore(riskEvaluationPrompt);

        if (riskScore > 0.7) {

        }

        return disasterAddDTO;
    }
}
