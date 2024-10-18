package pl.ateam.disasteralerts.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.ateam.disasteralerts.disasteralert.DisasterAlertFacade;
import pl.ateam.disasteralerts.disasteralert.DisasterStatus;
import pl.ateam.disasteralerts.disasteralert.DisasterType;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;

@Controller
@RequiredArgsConstructor
@RequestMapping("/disasters")
public class DisasterViewController {

    private final DisasterAlertFacade disasterService;


    @GetMapping("add")
    public String showAddDisasterForm(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("email", userDetails.getUsername());
        model.addAttribute("disasterType", DisasterType.values());
        model.addAttribute("disasterStatus", DisasterStatus.values());
        model.addAttribute("disasterAddDTO", new DisasterAddDTO(null, "", "", "", null, null, ""));
        return "addDisaster";
    }

    @PostMapping("add")
    public String addDisaster(@Valid DisasterAddDTO disasterAddDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "addDisaster";
        }
        disasterService.addDisaster(disasterAddDTO);
        return "redirect:/disasters";
    }
}
