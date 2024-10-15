package pl.ateam.disasteralerts.disasteralert;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    // TODO: wymaganie postawione dla implementacji security
    //  Obiekt Authentication w Credentials, Details lup Principal musi posiadać jedno z pól User będące jako UNIQUE w bazie danych

    @PostMapping(DISASTERS_BASE_URL)
    public ResponseEntity<DisasterDTO> createDisaster(@AuthenticationPrincipal Authentication authentication,
                                                      @RequestParam DisasterType disasterType,
                                                      @RequestParam String description) {

        // w zależności od implementacji security skorzysta się z jednej z trzech metod poniżej
        // roboczo posłużę się metodą getDetails();
        Object credentials = authentication.getCredentials();
        Object details = authentication.getDetails();
        Object principal = authentication.getPrincipal();

        UserDetails userDetails = (UserDetails) details;

        String username = userDetails.getUsername();



        return null;
    }
}
