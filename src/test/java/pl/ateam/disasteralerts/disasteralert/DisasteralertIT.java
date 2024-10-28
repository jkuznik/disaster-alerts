package pl.ateam.disasteralerts.disasteralert;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class DisasteralertIT extends PSQLTestConatiner{

    @Autowired
    DisasterRepository disasterRepository;

    @BeforeEach
    public void setUp() {
        bootStrapDB();
    }

    @AfterEach
    public void tearDown() {
        disasterRepository.deleteAll();
    }

    @Test
    void shouldReturnAllDisasters() {
        //given
        List<Disaster> disasters;

        //when
        disasters = disasterRepository.findAll();

        //then
        Assertions.assertThat(disasters.size()).isEqualTo(10);
    }

    private void bootStrapDB() {
        for (int i=0; i<10; i++) {
            Disaster disaster = new Disaster();

            disaster.setDisasterStartTime(LocalDateTime.now()); //TODO: usunąć pole disaster_start_time
            disaster.setType(DisasterType.FLOOD);
            disaster.setUserEmail("testEmail" + i + "@test.test");
            disaster.setDescription("testDescription" + i);
            disaster.setLocation("testLocation" + i);
            disaster.setSource("testSource" + i);
            disaster.setStatus(DisasterStatus.FAKE);

            disasterRepository.save(disaster);
        }
    }
}
