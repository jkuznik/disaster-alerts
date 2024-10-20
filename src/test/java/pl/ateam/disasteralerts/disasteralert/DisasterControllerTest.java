package pl.ateam.disasteralerts.disasteralert;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.ateam.disasteralerts._config.TestSecurityConfig;
import pl.ateam.disasteralerts.disasteralert.enums.DisasterStatus;
import pl.ateam.disasteralerts.disasteralert.enums.DisasterType;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DisasterController.class)
@Import({DisasterMapperImpl.class, TestSecurityConfig.class})
class DisasterControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    DisasterMapper mapper;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    DisasterService disasterService;

    @MockBean
    Authentication authentication;

    private final DisasterAddDTO disasterAddDTO = new DisasterAddDTO(
            DisasterType.FLOOD,
            "foo",
            "bar",
            "bazz",
            Instant.now(),
            DisasterStatus.ACTIVE,
            "testEmail");

    @Nested
    class POSTMethodsTest{

//        @Test
//        @WithMockUser(username = "email", roles = "USER")
//        void shouldReturnStatus201When_UserIsAuthenticatedAndHasRoleValid_AndRequestParamsAreValid() throws Exception {
//            //given
//            authentication.setAuthenticated(true);
//            DisasterDTO disasterDTO = mapper.mapDisasterToDisasterDto(mapper.mapDisasterAddDtoToDisaster(disasterAddDTO));
//
//            //when
//            when(authentication.getDetails()).thenReturn("testEmail");
//            when(disasterService.addDisaster(any(DisasterAddDTO.class))).thenReturn(disasterDTO);
//
//            //then
//            mockMvc.perform(post(DisasterController.DISASTERS_BASE_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(objectMapper.writeValueAsString(disasterAddDTO))
//                            .param("disasterType", DisasterType.FLOOD.toString())
//                            .param("description", "test"))
//                        .andExpect(status().isCreated());
//        }

        @Test
        @WithMockUser(username = "email", roles = "NOT_VALID")
        void shouldReturnStatus403When_UserIsUnAuthenticatedAndHasRoleNotValid_AndRequestParamsAreValid() throws Exception {
            //given
            authentication.setAuthenticated(true);
            DisasterDTO disasterDTO = mapper.mapDisasterToDisasterDto(mapper.mapDisasterAddDtoToDisaster(disasterAddDTO));

            //when
            when(authentication.getDetails()).thenReturn("testEmail");
            when(disasterService.addDisaster(any(DisasterAddDTO.class))).thenReturn(disasterDTO);

            //then
            mockMvc.perform(post(DisasterController.DISASTERS_BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(disasterAddDTO))
                            .param("disasterType", DisasterType.FLOOD.toString())
                            .param("description", "test"))
                    .andExpect(status().isForbidden());
        }

//        @Test
//        @WithMockUser(username = "email", roles = "USER")
//        void shouldReturnStatus201When_UserIsAuthenticatedAndHasRoleValid_AndRequestParamsAreValidAndDescriptionIsBlank() throws Exception {
//            //given
//            authentication.setAuthenticated(true);
//            DisasterDTO disasterDTO = mapper.mapDisasterToDisasterDto(mapper.mapDisasterAddDtoToDisaster(disasterAddDTO));
//
//            //when
//            when(authentication.getDetails()).thenReturn("testEmail");
//            when(disasterService.addDisaster(any(DisasterAddDTO.class))).thenReturn(disasterDTO);
//
//            //then
//            mockMvc.perform(post(DisasterController.DISASTERS_BASE_URL)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(objectMapper.writeValueAsString(disasterAddDTO))
//                            .param("disasterType", DisasterType.FLOOD.toString())
//                            .param("description", ""))
//                    .andExpect(status().isCreated());
//        }
    }
}