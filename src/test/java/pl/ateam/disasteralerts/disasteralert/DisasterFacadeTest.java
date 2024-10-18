package pl.ateam.disasteralerts.disasteralert;

import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import pl.ateam.disasteralerts.domain.disaster.DisasterFacade;
import pl.ateam.disasteralerts.domain.disaster.DisasterService;
import pl.ateam.disasteralerts.domain.disaster.enums.DisasterStatus;
import pl.ateam.disasteralerts.domain.disaster.enums.DisasterType;
import pl.ateam.disasteralerts.domain.disaster.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.domain.disaster.dto.DisasterDTO;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = {DisasterFacade.class, DisasterServiceImpl.class, MethodValidationPostProcessor.class})
class DisasterFacadeTest {

    @Autowired
    DisasterFacade disasterFacade;

    @MockBean
    DisasterService disasterService;

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
                    Instant.now(),
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
                Instant.now(),
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
                Instant.now(),
                Instant.now().plusSeconds(10),
                DisasterStatus.FAKE,
                "testUserEmail",
                null);
    }
}