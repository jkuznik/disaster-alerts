package pl.ateam.disasteralerts.disasteralert;

import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import pl.ateam.disasteralerts.disasteralert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.AlertDTO;
import pl.ateam.disasteralerts.user.UserFacade;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = {AlertServiceImpl.class, NotificationManager.class, UserFacade.class, AlertMapper.class, MethodValidationPostProcessor.class})
class AlertServiceImplTest {

    @Autowired
    private AlertService alertService;

    @MockBean
    private AlertRepository alertRepository;

    @MockBean
    private NotificationManager notificationManager;

    @MockBean
    private UserFacade userFacade;

    @MockBean
    private AlertMapper alertMapper;

    private final UUID disasterId = UUID.randomUUID();
    private final String alertDescription = "Test description";
    private final String alertLocation = "Test location";


    @Nested
    class CreateAlertTest {
        @Test
        void shouldReturnAlertDto_whenAlertAddDtoIsValid() {
            //given
            var alert = getAlert();
            var alertAddDto = getAlertAddDTO();
            var alertDto = getAlertDTO();

            //when
            when(alertRepository.save(any(Alert.class))).thenReturn(alert);
            when(alertMapper.mapAlertAddDtoToAlert(alertAddDto)).thenReturn(alert);
            when(alertMapper.mapAlertToAlertDto(alert)).thenReturn(alertDto);

            when(userFacade.getInterestedUsers(any(String.class))).thenReturn(null);
            doNothing().when(notificationManager).addSMSService();
            doNothing().when(notificationManager).createAlert(any(AlertAddDTO.class), any(Set.class));

            //then
            AlertDTO result = alertService.createAlert(alertAddDto);

            assertThat(result.disasterId()).isEqualTo(disasterId);
            assertThat(result.description()).isEqualTo(alertDescription);
            assertThat(result.location()).isEqualTo(alertLocation);
        }

        @Test
        void shouldThrowException_whenAlertAddDtoIsNull() {
            //when
            Assertions.assertThatThrownBy(() -> alertService.createAlert(null)).isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void shouldThrowException_whenAlertAddDtoDisasterIdIsNotValid() {
            //given
            var alertAddDto = new AlertAddDTO(
                    null,
                    alertDescription,
                    alertLocation
            );

            //when & then
            Assertions.assertThatThrownBy(() -> alertService.createAlert(alertAddDto)).isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void shouldThrowException_whenAlertAddDtoLocationIsNotValid() {
            //given
            var alertAddDto = new AlertAddDTO(
                    disasterId,
                    alertDescription,
                    ""
            );

            //when & then
            Assertions.assertThatThrownBy(() -> alertService.createAlert(alertAddDto)).isInstanceOf(ConstraintViolationException.class);
        }
    }

    @Test
    void sendNotifications() {
    }

    private Alert getAlert() {
        return new Alert(disasterId, alertDescription, alertLocation);
    }

    private AlertDTO getAlertDTO() {
        return new AlertDTO(
                disasterId,
                alertDescription,
                alertLocation
        );
    }

    private AlertAddDTO getAlertAddDTO() {
        return new AlertAddDTO(
                disasterId,
                alertDescription,
                alertLocation
        );
    }
}