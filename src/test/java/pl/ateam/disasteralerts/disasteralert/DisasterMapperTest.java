package pl.ateam.disasteralerts.disasteralert;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterUpdateDTO;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(classes = DisasterMapperImpl.class)
class DisasterMapperTest {

    @Autowired
    DisasterMapper mapper;

    Disaster disaster = new Disaster();

    DisasterDTO disasterDTO = new DisasterDTO(
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

    DisasterAddDTO disasterAddDTO = new DisasterAddDTO(
            DisasterType.FLOOD,
            "testDescription",
            "testAdd",
            "testLocation",
            Instant.now(),
            DisasterStatus.FAKE,
            "testUserEmail");

    DisasterUpdateDTO disasterUpdateDTO = new DisasterUpdateDTO(
            UUID.randomUUID(),
            Optional.of(DisasterType.FLOOD),
            Optional.of("testDescription"),
            Optional.of("testAdd"),
            Optional.of("testLocation"),
            Optional.of(Instant.now().plusSeconds(10)),
            Optional.of(DisasterStatus.FAKE));

    @Test
    void mapDisasterDtoToDisasterEntity() {
        //when
        Disaster result = mapper.mapDisasterDtoToDisasterEntity(disasterDTO);

        //then
        assertThat(result.getType()).isEqualTo(disasterDTO.type());
        assertThat(result.getDescription()).isEqualTo(disasterDTO.description());
        assertThat(result.getSource()).isEqualTo(disasterDTO.source());
        assertThat(result.getLocation()).isEqualTo(disasterDTO.location());
        assertThat(result.getDisasterStartTime()).isEqualTo(disasterDTO.disasterStartTime());
        assertThat(result.getStatus()).isEqualTo(disasterDTO.status());
        assertThat(result.getUserEmail()).isEqualTo(disasterDTO.userEmail());
    }

    @Test
    void mapDisasterAddDtoToDisaster() {
        //when
        Disaster result = mapper.mapDisasterAddDtoToDisaster(disasterAddDTO);

        //then
        assertThat(result.getType()).isEqualTo(disasterAddDTO.type());
        assertThat(result.getDescription()).isEqualTo(disasterAddDTO.description());
        assertThat(result.getSource()).isEqualTo(disasterAddDTO.source());
        assertThat(result.getLocation()).isEqualTo(disasterAddDTO.location());
        assertThat(result.getDisasterStartTime()).isEqualTo(disasterAddDTO.disasterStartTime());
        assertThat(result.getStatus()).isEqualTo(disasterAddDTO.status());
        assertThat(result.getUserEmail()).isEqualTo(disasterAddDTO.userEmail());
    }

    @Test
    void mapDisasterToDisasterDto() {
        //given
        disaster.setType(DisasterType.FLOOD);
        disaster.setDescription(disasterAddDTO.description());
        disaster.setSource(disasterAddDTO.source());
        disaster.setLocation(disasterAddDTO.location());
        disaster.setDisasterStartTime(disasterAddDTO.disasterStartTime());
        disaster.setDisasterEndTime(Instant.now().plusSeconds(10));
        disaster.setStatus(DisasterStatus.FAKE);
        disaster.setUserEmail(disasterAddDTO.userEmail());

        //when
        DisasterDTO result = mapper.mapDisasterToDisasterDto(disaster);

        //then
        assertThat(result.id()).isEqualTo(disaster.getId());
        assertThat(result.type()).isEqualTo(disaster.getType());
        assertThat(result.description()).isEqualTo(disaster.getDescription());
        assertThat(result.source()).isEqualTo(disaster.getSource());
        assertThat(result.location()).isEqualTo(disaster.getLocation());
        assertThat(result.disasterStartTime()).isEqualTo(disaster.getDisasterStartTime());
        assertThat(result.disasterEndTime()).isEqualTo(disaster.getDisasterEndTime());
        assertThat(result.status()).isEqualTo(disaster.getStatus());
        assertThat(result.userEmail()).isEqualTo(disaster.getUserEmail());
    }
}