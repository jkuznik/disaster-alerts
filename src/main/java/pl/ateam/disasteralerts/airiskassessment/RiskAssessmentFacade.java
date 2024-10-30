package pl.ateam.disasteralerts.airiskassessment;

import lombok.RequiredArgsConstructor;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;

@RequiredArgsConstructor
public class RiskAssessmentFacade {

    private final RiskAssessmentService riskAssessmentService;

    public DisasterAddDTO assessRisk(DisasterAddDTO disasterAddDTO) {
        riskAssessmentService.assessRisk(disasterAddDTO);
        return disasterAddDTO;
    }
}
