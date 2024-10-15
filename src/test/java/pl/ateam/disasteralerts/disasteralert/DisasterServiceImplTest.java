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

import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = {DisasterServiceImpl.class, DisasterMapperImpl.class, MethodValidationPostProcessor.class})
class DisasterServiceImplTest {

    @Autowired
    DisasterService disasterService;

    @MockBean
    DisasterRepository disasterRepository;

    @MockBean
    DisasterMapper disasterMapper;

    private final DisasterAddDTO disasterAddDTO = getDisasterAddDTO();

    private final DisasterDTO disasterDTO = getDisasterDTO();

    Disaster disaster = new Disaster();

    @Nested
    class PostMethodsTests {
        @Test
        void addDisaster_shouldReturnDisasterDtoWhenDisasterAddDtoIsValid() {
            //given

            //when
            when(disasterMapper.mapDisasterAddDtoToDisaster(any(DisasterAddDTO.class))).thenReturn(disaster);
            when(disasterRepository.save(any(Disaster.class))).thenReturn(disaster);
            when(disasterMapper.mapDisasterToDisasterDto(disaster)).thenReturn(disasterDTO);

            //then
            DisasterDTO result = disasterService.addDisaster(disasterAddDTO);

            Assertions.assertThat(result.id()).isEqualTo(disasterDTO.id());
            Assertions.assertThat(result.location()).isEqualTo(disasterDTO.location());
        }

        @Test
        void addDisaster_shouldThrowExceptionWhenDisasterAddDtoIsNull() {
            //given

            //when

            //then
            Assertions.assertThatThrownBy(() -> disasterService.addDisaster(null)).isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void addDisaster_shouldThrowExceptionWhenDisasterAddDtoIsNotValid() {
            //given
            DisasterAddDTO notValidDTO = new DisasterAddDTO(
                    UUID.randomUUID(),
                    DisasterType.FLOOD,
//                    "testSource",     this field is required as NotNull and NotBlank
                    null,
                    "testLocation",
                    Instant.now(),
                    DisasterStatus.FAKE);

            //when

            //then
            Assertions.assertThatThrownBy(() -> disasterService.addDisaster(notValidDTO)).isInstanceOf(ConstraintViolationException.class);
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