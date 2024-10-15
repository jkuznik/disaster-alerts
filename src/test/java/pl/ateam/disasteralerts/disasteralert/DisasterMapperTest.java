package pl.ateam.disasteralerts.disasteralert;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(classes = DisasterMapperImpl.class)
class DisasterMapperTest {

    @Autowired
    DisasterMapper disasterMapper;

    DisasterAddDTO disasterAddDTO = new DisasterAddDTO(
            UUID.randomUUID(),
            DisasterType.FLOOD,
            "testAdd",
            "testLocation",
            Instant.now(),
            DisasterStatus.FAKE);

    DisasterDTO disasterDTO = new DisasterDTO(UUID.randomUUID(),
            DisasterType.FLOOD,
            "testAdd",
            "testLocation",
            Instant.now(),
            Instant.now().plusSeconds(10),
            DisasterStatus.FAKE,
            null);


    @Test
    void dtoToDisaster() {
        //given

        //when
        Disaster result = disasterMapper.mapDisasterDtoToDisasterEntity(disasterDTO);

        //then
        assertThat(result.getId()).isEqualTo(disasterDTO.id());
    }

    @Test
    void disasterAddDtoToDisaster() {
    }

    @Test
    void mapDisasterToDisasterDto() {
    }
}