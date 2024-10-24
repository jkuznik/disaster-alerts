package pl.ateam.disasteralerts.disasteralert;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;

import java.time.Instant;
import java.time.LocalDateTime;

@RestController
@RequestMapping(DisasterController.DISASTERS_BASE_URL)
@RequiredArgsConstructor
class DisasterController {

    public static final String DISASTERS_BASE_URL = "/disasters";

    private final DisasterService disasterService;

    @PostMapping()
    public ResponseEntity<DisasterDTO> createDisaster(@AuthenticationPrincipal UserDetails userDetails,
                                                      @RequestParam DisasterType disasterType,
                                                      @RequestParam String description) {

        DisasterAddDTO disasterAddDTO = new DisasterAddDTO(
                disasterType,
                description,
                "user",
                "lokalizacja - konieczne ustalić logikę przekazywania lokalizacji", // czy pobieramy z lokalizacji użytkownika czy user może sam wprowadzić
                LocalDateTime.now(),
                DisasterStatus.ACTIVE,
                userDetails.getUsername()
        );

        DisasterDTO disasterDTO = disasterService.addDisaster(disasterAddDTO);

        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString("http://localhost:8081" + DISASTERS_BASE_URL + "/{id}")
                .buildAndExpand(disasterDTO.id());

        return ResponseEntity.created(uriComponents.toUri()).body(disasterDTO);
    }
}
