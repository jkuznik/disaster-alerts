package pl.ateam.disasteralerts.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupViewController {
    @GetMapping
    public String signup() {
        return "signup";
    }
}
