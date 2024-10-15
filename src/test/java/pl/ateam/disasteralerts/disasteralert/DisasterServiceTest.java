package pl.ateam.disasteralerts.disasteralert;

import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = {DisasterService.class, DisasterMapperImpl.class, MethodValidationPostProcessor.class})
class DisasterServiceTest {

    @Autowired
    DisasterServiceAPI disasterService;

    @MockBean
    DisasterRepository disasterRepository;

    @MockBean
    DisasterMapper disasterMapper;

    private final DisasterAddDTO disasterAddDTO = new DisasterAddDTO(UUID.randomUUID(),
            DisasterType.FLOOD,
            "testSource",
            "testLocation",
            Instant.now(),
            DisasterStatus.FAKE);

    private final DisasterDTO disasterDTO = new DisasterDTO(UUID.randomUUID(),
            DisasterType.FLOOD,
            "testSource",
            "testLocation",
            Instant.now(),
            Instant.now().plusSeconds(10),
            DisasterStatus.FAKE,
            null);

    Disaster disaster = new Disaster();

    @Nested
    class PostMethodsTests {
        @Test
        void addDisaster_shouldReturnDisasterDtoWhenDisasterAddDtoIsValid() {

            when(disasterMapper.disasterAddDtoToDisaster(any(DisasterAddDTO.class))).thenReturn(disaster);
            when(disasterRepository.save(any(Disaster.class))).thenReturn(disaster);
            when(disasterMapper.disasterToDto(disaster)).thenReturn(disasterDTO);

            DisasterDTO result = disasterService.addDisaster(disasterAddDTO);

//            Assertions.assertThat(result.id()).isEqualTo(disasterDTO.id());
            Assertions.assertThat(result.location()).isEqualTo(disasterDTO.location());
        }

        @Test
        void addDisaster_shouldThrowExceptionWhenDisasterAddDtoIsNull() {
            Assertions.assertThatThrownBy(() -> disasterService.addDisaster(null)).isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void addDisaster_shouldThrowExceptionWhenDisasterAddDtoIsNotValid() {
            DisasterAddDTO notValidDTO = new DisasterAddDTO(
                    UUID.randomUUID(),
                    DisasterType.FLOOD,
//                    "testSource",
                    null,
                    "testLocation",
                    Instant.now(),
                    DisasterStatus.FAKE);

            Assertions.assertThatThrownBy(() -> disasterService.addDisaster(notValidDTO)).isInstanceOf(ConstraintViolationException.class);
        }

    }
}