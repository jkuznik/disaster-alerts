package pl.ateam.disasteralerts.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.ateam.disasteralerts.disasteralert.DisasterAlertFacade;
import pl.ateam.disasteralerts.disasteralert.DisasterStatus;
import pl.ateam.disasteralerts.disasteralert.DisasterType;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddWebDTO;
import pl.ateam.disasteralerts.security.AppUser;
import pl.ateam.disasteralerts.util.CitiesInPoland;

@Controller
@RequiredArgsConstructor
@RequestMapping("/disasters")
public class DisasterViewController {

    private final DisasterAlertFacade disasterService;


    @GetMapping("add")
    public String showAddDisasterForm(Model model, @AuthenticationPrincipal AppUser appUser) {

        baseModel(model, appUser);
        model.addAttribute("disasterTypSelected", null);
        model.addAttribute("disasterAddDTO", new DisasterAddWebDTO(null, null, null, null));
        model.addAttribute("selectedLocation", appUser.getUserDTO().location());

        return "addDisaster";
    }

    @PostMapping("add")
    public String addDisaster(Model model, @AuthenticationPrincipal AppUser userDetails,
                              @Valid @ModelAttribute DisasterAddWebDTO disasterAddWebDTO,
                              BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            baseModel(model, userDetails);
            model.addAttribute("disasterTypSelected", disasterAddWebDTO.type());
            model.addAttribute("disasterAddDTO", disasterAddWebDTO);
            model.addAttribute("selectedLocation", disasterAddWebDTO.location());
            return "addDisaster";
        }

        disasterService.addDisasterFromWeb(disasterAddWebDTO);
        redirectAttributes.addFlashAttribute("message", "Dodano zdarzenie");
        return "redirect:/disasters/add";
    }

    private void baseModel(Model model, AppUser userDetails) {
        model.addAttribute("email", userDetails.getUsername());
        model.addAttribute("disasterType", DisasterType.values());
        model.addAttribute("disasterStatus", DisasterStatus.values());
        model.addAttribute("cities", CitiesInPoland.getList());
    }
}
