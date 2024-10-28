package pl.ateam.disasteralerts.disasteralert;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;
import pl.ateam.disasteralerts.security.AppUser;

@RestController
@RequestMapping(DisasterController.DISASTERS_BASE_URL)
@RequiredArgsConstructor
class DisasterController {

    public static final String DISASTERS_BASE_URL = "/disasters";

    private final DisasterService disasterService;

    @PostMapping()
    public ResponseEntity<DisasterDTO> createDisaster(@AuthenticationPrincipal AppUser appUser,
                                                      @RequestParam DisasterType disasterType,
                                                      @RequestParam String description,
                                                      @RequestParam String location) {

        DisasterAddDTO disasterAddDTO = new DisasterAddDTO(
                disasterType,
                description,
                location,
                appUser.getUserDTO().id()
        );

        DisasterDTO disasterDTO = disasterService.createDisaster(disasterAddDTO);

        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString("http://localhost:8081" + DISASTERS_BASE_URL + "/{id}")
                .buildAndExpand(disasterDTO.id());

        return ResponseEntity.created(uriComponents.toUri()).body(disasterDTO);
    }
}
