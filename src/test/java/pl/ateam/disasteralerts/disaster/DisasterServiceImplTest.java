package pl.ateam.disasteralerts.disaster;

import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import pl.ateam.disasteralerts.airiskassessment.RiskAssessmentFacade;
import pl.ateam.disasteralerts.alert.AlertFacade;
import pl.ateam.disasteralerts.disaster.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disaster.dto.DisasterDTO;
import pl.ateam.disasteralerts.disaster.enums.DisasterStatus;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = {DisasterServiceImpl.class, AlertFacade.class, DisasterMapperImpl.class, MethodValidationPostProcessor.class})
class DisasterServiceImplTest {

    @Autowired
    DisasterService disasterService;

    @MockBean
    DisasterRepository disasterRepository;

    @MockBean
    AlertFacade alertFacade;

    @MockBean
    DisasterMapper disasterMapper;

    @MockBean
    private RiskAssessmentFacade riskAssessment;

    private final UUID testUserId = UUID.randomUUID();
    private final DisasterAddDTO disasterAddDTO = getDisasterAddDTO();
    private final DisasterDTO disasterDTO = getDisasterDTO();
    private final String USER_AS_DISASTER_SOURCE = "testUser";

    Disaster disaster = new Disaster();

    @Nested
    class PostMethodsTests {
        @Test
        void addDisaster_shouldReturnDisasterDtoWhenDisasterAddDtoIsValid() {
            //when
            when(disasterMapper.mapDisasterAddDtoToDisaster(any(DisasterAddDTO.class))).thenReturn(disaster);
            when(disasterRepository.save(any(Disaster.class))).thenReturn(disaster);
            when(disasterRepository.findById(disaster.getId())).thenReturn(Optional.of(disaster));
            when(disasterMapper.mapDisasterToDisasterDto(disaster)).thenReturn(disasterDTO);
            when(riskAssessment.assessRisk(disasterAddDTO)).thenReturn(true);

            //then
            DisasterDTO result = disasterService.createDisaster(disasterAddDTO, USER_AS_DISASTER_SOURCE);

            Assertions.assertThat(result.id()).isEqualTo(disasterDTO.id());
            Assertions.assertThat(result.location()).isEqualTo(disasterDTO.location());
        }

        @Test
        void addDisaster_shouldThrowExceptionWhenDisasterAddDtoIsNull() {
            //when & then
            Assertions.assertThatThrownBy(() -> disasterService.createDisaster(null, USER_AS_DISASTER_SOURCE)).isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void addDisaster_shouldThrowExceptionWhenDisasterAddDtoIsNotValid() {
            //given
            DisasterAddDTO notValidDTO = new DisasterAddDTO(
                    DisasterType.FLOOD,
                    "testDescription",
                    null,
                    testUserId);

            //when & then
            Assertions.assertThatThrownBy(() -> disasterService.createDisaster(notValidDTO, USER_AS_DISASTER_SOURCE)).isInstanceOf(ConstraintViolationException.class);
        }
    }

    private DisasterAddDTO getDisasterAddDTO() {
        return new DisasterAddDTO(
                DisasterType.FLOOD,
                "testDescription",
                "testLocation",
                testUserId);
    }

    private DisasterDTO getDisasterDTO() {
        return new DisasterDTO(
                UUID.randomUUID(),
                DisasterType.FLOOD,
                "testDescription",
                "testAdd",
                "testLocation",
                LocalDateTime.now(),
                LocalDateTime.now().plusSeconds(10),
                DisasterStatus.FAKE,
                testUserId);
    }
}