package pl.ateam.disasteralerts.disaster;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.ateam.disasteralerts.API.WeatherMonitoringFacade;
import pl.ateam.disasteralerts.util.enums.DisasterStatus;
import pl.ateam.disasteralerts.security.AppUser;
import pl.ateam.disasteralerts.user.UserFacade;
import pl.ateam.disasteralerts.user.dto.UserDTO;
import pl.ateam.disasteralerts.util.enums.DisasterType;

import java.util.List;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DisasteralertIT extends PSQLTestConatiner {

    @Autowired
    DisasterRepository disasterRepository;

    @Autowired
    UserFacade userFacade;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    WeatherMonitoringFacade weatherMonitoringFacade;

    AppUser appUser = getTestAppUser();

    @BeforeEach
    public void setUp() {
        disasterRepository.deleteAll();
        bootStrapDB();
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

    @Test
    void shouldReturnDisasterByIdFromDb_afterCreateDisasterByHttpPostMethod() throws Exception {
        //given
        List<Disaster> before = disasterRepository.findAll();

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/disasters")
                .with(user(appUser))
                .param("disasterType", DisasterType.FLOOD.name())
                .param("description", "test")
                .param("location", "test"))
                .andExpect(status().isCreated());

        //then
        List<Disaster> after = disasterRepository.findAll();

        Assertions.assertThat(after.size()).isEqualTo((before.size() + 1));

    }

    private void bootStrapDB() {
        for (int i=0; i<10; i++) {
            Disaster disaster = new Disaster();

            disaster.setType(DisasterType.FLOOD);
            disaster.setUserId(UUID.randomUUID());
            disaster.setDescription("testDescription" + i);
            disaster.setLocation("testLocation" + i);
            disaster.setSource("testSource" + i);
            disaster.setStatus(DisasterStatus.FAKE);

            disasterRepository.save(disaster);
        }
    }

    private AppUser getTestAppUser() {
        return AppUser.builder()
                .userDTO(testUserDTO())
                .build();
    }

    private UserDTO testUserDTO() {
        return new UserDTO(
                UUID.randomUUID(),
                "email@email.emial",
                "password",
                "+481233456789",
                "location",
                "ROLE_USER"
        );
    }
}
