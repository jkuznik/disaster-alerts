package pl.ateam.disasteralerts.airiskassessment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ateam.disasteralerts.disaster.dto.DisasterAddDTO;

@Component
@RequiredArgsConstructor
public class RiskAssessmentFacade {

    private final RiskAssessmentService riskAssessmentService;

    public boolean assessRisk(DisasterAddDTO disasterAddDTO) {
        return riskAssessmentService.assessRisk(disasterAddDTO);
    }
}
