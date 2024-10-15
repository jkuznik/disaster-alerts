package pl.ateam.disasteralerts.disasteralert;

import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = {DisasterAlertFacade.class, DisasterServiceImpl.class, MethodValidationPostProcessor.class})
class DisasterAlertFacadeTest {

    @Autowired
    DisasterAlertFacade disasterAlertFacade;

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
            DisasterDTO result = disasterAlertFacade.addDisaster(disasterAddDTO);

            Assertions.assertThat(result.id()).isEqualTo(disasterDTO.id());
            Assertions.assertThat(result.location()).isEqualTo(disasterDTO.location());
        }

        @Test
        void addDisaster_shouldThrowExceptionWhenDisasterAddDtoIsNull() {
            //given

            //when

            //then
            Assertions.assertThatThrownBy(() -> disasterAlertFacade.addDisaster(null)).isInstanceOf(ConstraintViolationException.class);
        }


    }

    private DisasterAddDTO getDisasterAddDTO() {
        return new DisasterAddDTO(UUID.randomUUID(),
                DisasterType.FLOOD,
                "testSource",
                "testLocation",
                Instant.now(),
                DisasterStatus.FAKE);
    }

    private DisasterDTO getDisasterDTO() {
        return new DisasterDTO(UUID.randomUUID(),
                DisasterType.FLOOD,
                "testSource",
                "testLocation",
                Instant.now(),
                Instant.now().plusSeconds(10),
                DisasterStatus.FAKE,
                null);
    }
}