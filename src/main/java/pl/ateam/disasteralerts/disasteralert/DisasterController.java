package pl.ateam.disasteralerts.disasteralert;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;

@RestController
@RequestMapping(DisasterController.DISASTERS_BASE_URL)
@RequiredArgsConstructor
class DisasterController {

    public static final String DISASTERS_BASE_URL = "/disasters";

    private final DisasterService disasterService;

    @PostMapping(DISASTERS_BASE_URL)
    public ResponseEntity<DisasterDTO> createDisaster(@RequestParam DisasterType disasterType,
                                                      @RequestParam String description) {

        return null;
    }
}
