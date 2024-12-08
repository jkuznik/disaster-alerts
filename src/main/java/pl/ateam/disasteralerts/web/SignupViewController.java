package pl.ateam.disasteralerts.web;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.ateam.disasteralerts.message.ToastMessageFacade;
import pl.ateam.disasteralerts.message.ToastMessageType;
import pl.ateam.disasteralerts.user.UserService;
import pl.ateam.disasteralerts.user.dto.UserRegisterDTO;
import pl.ateam.disasteralerts.util.CitiesInPoland;

@Controller
@RequestMapping("/signup")
public class SignupViewController {

    private final ToastMessageFacade toastMessageFacade;

    @Value("${google.maps.api.key}")
    private String googleApiKey;

    private final UserService userService;

    public SignupViewController(UserService userService, ToastMessageFacade toastMessageFacade) {
        this.userService = userService;
        this.toastMessageFacade = toastMessageFacade;
    }

    @GetMapping
    public String signupView(Model model) {
        model.addAttribute("userDto",
                new UserRegisterDTO(null, null, null, null, null));
        citiesList(model);
        model.addAttribute("googleApiKey", googleApiKey);

        return "signup";
    }

    @PostMapping
    public String signupView(@RequestParam(value = "confirmPassword") String confirmPassword,
                             @Valid @ModelAttribute("userDto") UserRegisterDTO userDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Model model) {

        citiesList(model);
        model.addAttribute("selectedLocation", userDto.location());

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        if (!confirmPassword.contains(userDto.password())) {
            model.addAttribute("confirmPasswordError", "Hasła są różne");
            return "signup";
        }

        userService.save(userDto);

        redirectAttributes.addFlashAttribute("message", toastMessageFacade.buildMessage(
                ToastMessageType.SUCCESS,
                "Konto utworzone",
                "Konto zostało utworzone. Zaloguj się."));
        return "redirect:/";
    }

    private void citiesList(Model model) {
        model.addAttribute("cities", CitiesInPoland.getList());
    }
}
