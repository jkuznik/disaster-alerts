package pl.ateam.disasteralerts.disasteralert;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import pl.ateam.disasteralerts._config.TestSecurityConfig;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;
import pl.ateam.disasteralerts.security.AppUser;
import pl.ateam.disasteralerts.user.dto.UserDTO;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DisasterController.class)
@Import({DisasterMapperImpl.class, TestSecurityConfig.class})
class DisasterControllerTest {

    @MockBean
    DisasterService disasterService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    DisasterMapper mapper;

    @Autowired
    AppUser appUser;

    private final UUID testUserId = UUID.randomUUID();
    private final DisasterAddDTO disasterAddDTO = getDisasterAddDTO();

    @Nested
    class POSTMethodsTest {

        @Test
        @WithUserDetails(userDetailsServiceBeanName = "testUserDetailsService", value = "username")
        void shouldReturnStatus201When_UserIsAuthenticatedAndHasRoleValid_AndRequestParamsAreValid() throws Exception {
            //given
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(appUser, null, appUser.getAuthorities()));
            DisasterDTO disasterDTO = mapper.mapDisasterToDisasterDto(mapper.mapDisasterAddDtoToDisaster(disasterAddDTO));

            //when
            when(disasterService.createDisaster(any(DisasterAddDTO.class), any(String.class))).thenReturn(disasterDTO);

            //then
            mockMvc.perform(post(DisasterController.DISASTERS_BASE_URL)
                            .param("disasterType", DisasterType.FLOOD.toString())
                            .param("description", "testDescription")
                            .param("location", "testLocation"))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", "http://localhost:8081/disasters/"))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(jsonPath("$.type").value("FLOOD"))
                    .andExpect(jsonPath("$.description").value("foo"))
                    .andExpect(jsonPath("$.location").value("bar"))
                    .andExpect(jsonPath("$.userId").value(testUserId.toString()));
        }

        @Test
        @WithMockUser(username = "email", roles = "NOT_VALID")
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

    private UserDTO getUserDTO() {
        return new UserDTO(
                UUID.randomUUID(),
                "username",
                "email@email.emial",
                "password",
                "+481233456789",
                "location",
                "USER"
        );
    }
}