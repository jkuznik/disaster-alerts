package pl.ateam.disasteralerts.disaster;

import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import pl.ateam.disasteralerts.alert.AlertFacade;
import pl.ateam.disasteralerts.disaster.enums.DisasterStatus;
import pl.ateam.disasteralerts.disaster.enums.DisasterType;
import pl.ateam.disasteralerts.disaster.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disaster.dto.DisasterDTO;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = {DisasterFacade.class, AlertFacade.class, DisasterServiceImpl.class, MethodValidationPostProcessor.class})
class DisasterFacadeTest {

    @Autowired
    DisasterFacade disasterFacade;

    @MockBean
    DisasterService disasterService;

    @MockBean
    AlertFacade alertFacade;

    private final DisasterAddDTO disasterAddDTO = getDisasterAddDTO();

    private final DisasterDTO disasterDTO = getDisasterDTO();


    @Nested
    class AddDisasterTests {
        @Test
        void addDisaster_shouldReturnDisasterWhenDisasterAddDtoIsValid() {
            //given

            //when
            when(disasterService.addDisaster(disasterAddDTO)).thenReturn(disasterDTO);

            //then
            DisasterDTO result = disasterFacade.addDisaster(disasterAddDTO);

            Assertions.assertThat(result.id()).isEqualTo(disasterDTO.id());
            Assertions.assertThat(result.location()).isEqualTo(disasterDTO.location());
        }

        @Test
        void addDisaster_shouldThrowExceptionWhenDisasterAddDtoIsNull() {
            //given

            //when

            //then
            Assertions.assertThatThrownBy(() -> disasterFacade.addDisaster(null)).isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void addDisaster_shouldThrowExceptionWhenDisasterAddDtoIsNotValid() {
            //given
            DisasterAddDTO notValidDTO = new DisasterAddDTO(
                    DisasterType.FLOOD,
                    "testDescription",
                    null,
                    "testLocation",
                    LocalDateTime.now(),
                    DisasterStatus.FAKE,
                    "testUserEmail");

            //when

            //then
            Assertions.assertThatThrownBy(() -> disasterFacade.addDisaster(notValidDTO)).isInstanceOf(ConstraintViolationException.class);
        }
    }

    private DisasterAddDTO getDisasterAddDTO() {
        return new DisasterAddDTO(
                DisasterType.FLOOD,
                "testDescription",
                "testAdd",
                "testLocation",
                LocalDateTime.now(),
                DisasterStatus.FAKE,
                "testUserEmail");
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
                "testUserEmail");
    }
}