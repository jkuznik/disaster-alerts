package pl.ateam.disasteralerts.airiskassessment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.ateam.disasteralerts.disasteralert.DisasterType;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.util.CitiesInPoland;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RiskAssessmentServiceTest {

    public static final String FLOOD_DESCRIPTION = "Duża powódź";
    public static final String FIRE_DESCRIPTION = "Ognisko palone przez turystów nad rzeką";
    public static final Double PROBABILITY_OF_RISK_HIGH = 0.8;
    public static final Double PROBABILITY_OF_RISK_LOW = 0.3;

    @Mock
    private OpenAIClient openAIClient;

    @InjectMocks
    private RiskAssessmentService riskAssessmentService;

    private final List<String> citiesInPoland = CitiesInPoland.getList();

//    @Test
//    void shouldReturnTrueWhenRiskScoreAboveThreshold() {
//        // given
//        DisasterAddDTO disasterAddDTO = new DisasterAddDTO(
//                DisasterType.FLOOD,
//                FLOOD_DESCRIPTION,
//                citiesInPoland.get(1),
//                UUID.randomUUID());
//
//        // when
//        when(openAIClient.getRiskScore(Mockito.anyString())).thenReturn(PROBABILITY_OF_RISK_HIGH);
//
//        // then
//        assertTrue(riskAssessmentService.assessRisk(disasterAddDTO));
//    }
//
//    @Test
//    void shouldReturnFalseWhenRiskScoreBelowThreshold() {
//        // given
//        DisasterAddDTO disasterAddDTO = new DisasterAddDTO(
//                DisasterType.FIRE,
//                FIRE_DESCRIPTION,
//                citiesInPoland.get(1),
//                UUID.randomUUID());
//
//        // when
//        when(openAIClient.getRiskScore(Mockito.anyString())).thenReturn(PROBABILITY_OF_RISK_LOW);
//
//        // then
//        assertFalse(riskAssessmentService.assessRisk(disasterAddDTO));
//    }
//
//    @Test
//    void shouldReturnFalseWhenOpenAIClientThrowsException() {
//        // given
//        DisasterAddDTO disasterAddDTO = new DisasterAddDTO(
//                DisasterType.FIRE,
//                FIRE_DESCRIPTION,
//                citiesInPoland.get(1),
//                UUID.randomUUID());
//
//        // when
//        when(openAIClient.getRiskScore(Mockito.anyString())).thenThrow(new RuntimeException("Connection error"));
//
//        //then
//        assertFalse(riskAssessmentService.assessRisk(disasterAddDTO));
//    }
}
