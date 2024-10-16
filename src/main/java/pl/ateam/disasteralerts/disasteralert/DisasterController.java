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

    // TODO: wymaganie postawione dla implementacji security
    //  Obiekt Authentication w Credentials, Details lup Principal musi posiadać jedno z pól User będące jako UNIQUE w bazie danych

    @PostMapping()
    public ResponseEntity<DisasterDTO> createDisaster(@AuthenticationPrincipal Authentication authentication,
                                                      @RequestParam DisasterType disasterType,
                                                      @RequestParam String description) {

        DisasterAddDTO disasterAddDTO = new DisasterAddDTO(
                getCurrentUserId(authentication),
                disasterType,
                description,
                "user",
                "lokalizacja - konieczne ustalić logikę przekazywania lokalizacji", // czy pobieramy z lokalizacji użytkownika czy user może sam wprowadzić
                Instant.now(),
                DisasterStatus.ACTIVE
        );

        DisasterDTO disasterDTO = disasterService.addDisaster(disasterAddDTO);

        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString("http://localhost:8081" + DISASTERS_BASE_URL + "/{id}")
                .buildAndExpand(disasterDTO.id());

        return ResponseEntity.created(uriComponents.toUri()).body(disasterDTO);
    }

    private UUID getCurrentUserId(Authentication authentication) {
        // w zależności od implementacji security skorzysta się z jednej z trzech metod poniżej
        // roboczo posłużę się metodą getDetails();
//        Object credentials = authentication.getCredentials();
        Object details = authentication.getDetails();
//        Object principal = authentication.getPrincipal();

        UserDetails userDetails = (UserDetails) details;

        String username = userDetails.getUsername();

        //TODO: wymaganie dla modułu User
        // implementacja UserFacade musi wystawic metodę do pobrania UserDto na podstawie obiektu otrzymanego z security context

        UserDTO userDtoByUsername;

        try {
            userDtoByUsername = userFacadeWydmuszka.getUserDtoByUsername(username);
        } catch (RuntimeException e) {

        }

        //TODO: wymagania dla modułu User
        // UserDTO musi posiadać pole id albo inne które jest UNIQUE w bazie danych - w obecnej wersji DisasterDTO
        // oczekuje id użytkownika
//        return userDtoByUsername.getId()

        return UUID.randomUUID();   // robocza implementacja na potrzeby kompilatora
    }
}
