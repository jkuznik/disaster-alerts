package pl.ateam.disasteralerts.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.ateam.disasteralerts.user.UserService;

@Controller
@RequestMapping()
@RequiredArgsConstructor
@Slf4j
public class LoginViewController {

    private final UserService userService;

    @GetMapping("/login")
    public String lginView() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getPrincipal().toString());
        return "login";
    }
}
