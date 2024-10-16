package pl.ateam.disasteralerts.disasteralert;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;
import pl.ateam.disasteralerts.disasteralert.roboczeKlasyWydmuszki.UserFacadeWydmuszka;
import pl.ateam.disasteralerts.user.UserDTO;
import pl.ateam.disasteralerts.user.UserService;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping(DisasterController.DISASTERS_BASE_URL)
@RequiredArgsConstructor
class DisasterController {

    public static final String DISASTERS_BASE_URL = "/disasters";

    private final DisasterService disasterService;
    private final UserFacadeWydmuszka userFacadeWydmuszka;

    @PostMapping()
    public ResponseEntity<DisasterDTO> createDisaster(@AuthenticationPrincipal Authentication authentication,
                                                      @RequestParam DisasterType disasterType,
                                                      @RequestParam String description) {

        DisasterAddDTO disasterAddDTO = new DisasterAddDTO(
                disasterType,
                description,
                "user",
                "lokalizacja - konieczne ustalić logikę przekazywania lokalizacji", // czy pobieramy z lokalizacji użytkownika czy user może sam wprowadzić
                Instant.now(),
                DisasterStatus.ACTIVE,
                getUserEmail(authentication)
        );

        DisasterDTO disasterDTO = disasterService.addDisaster(disasterAddDTO);

        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString("http://localhost:8081" + DISASTERS_BASE_URL + "/{id}")
                .buildAndExpand(disasterDTO.id());

        return ResponseEntity.created(uriComponents.toUri()).body(disasterDTO);
    }

    private String getUserEmail(Authentication authentication) {
        Object details = authentication.getDetails();
        UserDetails userDetails = (UserDetails) details;
        return userDetails.getUsername();
    }
}
