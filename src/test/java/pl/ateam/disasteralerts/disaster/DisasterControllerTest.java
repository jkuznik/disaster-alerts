package pl.ateam.disasteralerts.disaster;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.ateam.disasteralerts._config.TestProfileConfig;
import pl.ateam.disasteralerts.disaster.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disaster.dto.DisasterDTO;
import pl.ateam.disasteralerts.security.AppUser;
import pl.ateam.disasteralerts.user.dto.UserDTO;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DisasterController.class)
@Import({DisasterMapperImpl.class, TestProfileConfig.class})
class DisasterControllerTest {

    @MockBean
    DisasterService disasterService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    DisasterMapper mapper;

    AppUser appUser = getTestAppUser();

    private final UUID testUserId = UUID.randomUUID();
    private final DisasterAddDTO disasterAddDTO = getDisasterAddDTO();

    @Nested
    class POSTMethodsTest {

        @Test
        void shouldReturnStatus201_whenUserIsAuthenticatedAndHasRoleValid_AndRequestParamsAreValid() throws Exception {
            //given
            DisasterDTO disasterDTO = mapper.mapDisasterToDisasterDto(mapper.mapDisasterAddDtoToDisaster(disasterAddDTO));

            //when
            when(disasterService.createDisaster(any(DisasterAddDTO.class), any(String.class))).thenReturn(disasterDTO);

            //then
            mockMvc.perform(post(DisasterController.DISASTERS_BASE_URL)
                            .with(user(appUser))
                            .param("disasterType", disasterDTO.type().name())
                            .param("description", disasterDTO.description())
                            .param("location", disasterDTO.location()))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", "http://localhost:8081/disasters/"))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(jsonPath("$.type").value("FLOOD"))
                    .andExpect(jsonPath("$.description").value("foo"))
                    .andExpect(jsonPath("$.location").value("bar"))
                    .andExpect(jsonPath("$.userId").value(testUserId.toString()));
        }

        @Test
        @WithMockUser(username = "username", roles = "NOT_VALID")
        void shouldReturnStatus403When_UserIsUnAuthenticatedAndHasRoleNotValid_AndRequestParamsAreValid() throws Exception {
            //given
            DisasterDTO disasterDTO = mapper.mapDisasterToDisasterDto(mapper.mapDisasterAddDtoToDisaster(disasterAddDTO));

            //when
            when(disasterService.createDisaster(any(DisasterAddDTO.class), any(String.class))).thenReturn(disasterDTO);

            //then
            mockMvc.perform(post(DisasterController.DISASTERS_BASE_URL)
                            .param("disasterType", DisasterType.FLOOD.toString())
                            .param("description", "test")
                            .param("location", "testLocation"))
                    .andExpect(status().isForbidden());
        }
    }

    private DisasterAddDTO getDisasterAddDTO() {
        return new DisasterAddDTO(
                DisasterType.FLOOD,
                "foo",
                "bar",
                testUserId);
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