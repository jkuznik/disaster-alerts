package pl.ateam.disasteralerts.disasteralert;

import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@SpringJUnitConfig(classes = {DisasterAlertFacade.class, AlertServiceImpl.class, DisasterServiceImpl.class, MethodValidationPostProcessor.class})
class DisasterAlertFacadeTest {

    @Autowired
    DisasterAlertFacade disasterAlertFacade;

    @MockBean
    DisasterService disasterService;

    @MockBean
    AlertService alertService;

    private final UUID testUserId = UUID.randomUUID();
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

            assertThat(result.id()).isEqualTo(disasterDTO.id());
            assertThat(result.location()).isEqualTo(disasterDTO.location());
        }

        @Test
        void addDisaster_shouldThrowExceptionWhenDisasterAddDtoIsNull() {
            //given

            //when

            //then
            Assertions.assertThatThrownBy(() -> disasterAlertFacade.addDisaster(null)).isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void addDisaster_shouldThrowExceptionWhenDisasterAddDtoIsNotValid() {
            //given
            DisasterAddDTO notValidDTO = new DisasterAddDTO(
                    DisasterType.FLOOD,
                    "testDescription",
                    null,
                    testUserId);

            //when

            //then
            Assertions.assertThatThrownBy(() -> disasterAlertFacade.addDisaster(notValidDTO)).isInstanceOf(ConstraintViolationException.class);
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