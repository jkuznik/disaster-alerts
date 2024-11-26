package pl.ateam.disasteralerts.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.ateam.disasteralerts.security.AppUser;
import pl.ateam.disasteralerts.user.UserFacade;
import pl.ateam.disasteralerts.user.dto.UserUpdateDTO;
import pl.ateam.disasteralerts.util.CitiesInPoland;

@Controller
@RequestMapping("users")
@RequiredArgsConstructor
public class UserUpdateViewController {

    @Value("${google.maps.api.key}")
    private String googleApiKey;

    private final UserFacade userFacade;

    @GetMapping("edit")
    public String getUserForUpdate(@AuthenticationPrincipal AppUser appUser, Model model) {
        UserUpdateDTO userUpdateDto = userFacade.getUserForUpdate(appUser.getUserDTO().id());
        model.addAttribute("userUpdateDto", userUpdateDto);
        model.addAttribute("googleApiKey", googleApiKey);
        baseModel(model, userUpdateDto);
        return "updateUser";
    }

    @PostMapping("update")
    public String updateUser(@AuthenticationPrincipal AppUser appUser,
                             @Valid @ModelAttribute("userUpdateDto") UserUpdateDTO userUpdateDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        if (bindingResult.hasErrors()) {
            baseModel(model, userUpdateDto);
            return "updateUser";
        }

        userFacade.updateUser(userUpdateDto, appUser.getUserDTO().id());
        redirectAttributes.addFlashAttribute("message", "Dane użytkownika zostały pomyślnie zaktualizowane.");
        return "redirect:/disasters/add";
    }

    @GetMapping("removePhoneNumber")
    public String removePhoneNumber(@AuthenticationPrincipal AppUser appUser,
                                    RedirectAttributes redirectAttributes) {

        userFacade.removePhoneNumber(appUser.getUsername());
        redirectAttributes.addFlashAttribute("message", "Numer telefonu został usunięty");
        return "redirect:/users/edit";
    }

    private void baseModel(Model model, UserUpdateDTO userUpdateDto) {
        model.addAttribute("selectedLocation", userUpdateDto.location());
        model.addAttribute("cities", CitiesInPoland.getList());
    }
}
