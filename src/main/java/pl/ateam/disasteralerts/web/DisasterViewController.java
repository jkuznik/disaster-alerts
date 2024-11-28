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
import pl.ateam.disasteralerts.ai.chat.OpenAiService;
import pl.ateam.disasteralerts.ai.chat.dto.ConversationDTO;
import pl.ateam.disasteralerts.disasteralert.DisasterAlertFacade;
import pl.ateam.disasteralerts.disasteralert.DisasterStatus;
import pl.ateam.disasteralerts.disasteralert.DisasterType;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;
import pl.ateam.disasteralerts.security.AppUser;
import pl.ateam.disasteralerts.util.CitiesInPoland;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/disasters")
public class DisasterViewController {

    @Value("${google.maps.api.key}")
    private String googleApiKey;

    private final DisasterAlertFacade disasterAlertFacade;
    private final String USER_AS_DISASTER_SOURCE = "user";
    private final OpenAiService openAiService;


    @GetMapping("add")
    public String showAddDisasterForm(Model model, @AuthenticationPrincipal AppUser appUser) {

        if (!model.containsAttribute("conversation")) {
            ConversationDTO conversation = openAiService.getResponse("Jestem " + appUser.getUsername() + " i chcę zgłosić zagrożenie.");
            model.addAttribute("conversation", conversation);
        }

        baseModel(model, appUser);
        if (!model.containsAttribute("disasterAddDTO")) {
            model.addAttribute("disasterTypSelected", null);
            model.addAttribute("disasterAddDTO", new DisasterAddDTO(null, null, null, null));
        }
        model.addAttribute("selectedLocation", appUser.getUserDTO().location());
        model.addAttribute("googleApiKey", googleApiKey);
        model.addAttribute("disasterTypes", DisasterType.values());

        return "addDisaster";
    }

    @PostMapping("add")
    public String createDisaster(Model model, @AuthenticationPrincipal AppUser userDetails,
                                 @Valid @ModelAttribute DisasterAddDTO disasterAddDTO,
                                 BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            baseModel(model, userDetails);
            model.addAttribute("disasterTypSelected", disasterAddDTO.type());
            model.addAttribute("disasterAddDTO", disasterAddDTO);
            model.addAttribute("selectedLocation", disasterAddDTO.location());
            return "addDisaster";
        }

        disasterAlertFacade.createDisaster(disasterAddDTO, USER_AS_DISASTER_SOURCE);
        redirectAttributes.addFlashAttribute("message", "Dodano zdarzenie");
        return "redirect:/disasters/add";
    }

    @PostMapping("chat")
    String sendMessages(Model model, @RequestParam String selectedAnswer,
                        @AuthenticationPrincipal AppUser userDetails,
                        RedirectAttributes redirectAttributes) {

        ConversationDTO conversation = openAiService.getResponse(selectedAnswer);

        if (conversation.answers().isEmpty()) {
            DisasterAddDTO disasterAdd = openAiService.getDisasterAdd(userDetails.getUserDTO().id());
            redirectAttributes.addFlashAttribute("disasterAddDTO", disasterAdd);
            redirectAttributes.addFlashAttribute("disasterTypSelected", disasterAdd.type());
            redirectAttributes.addFlashAttribute("description", disasterAdd.description());
            redirectAttributes.addFlashAttribute("conversation", null);
            return "redirect:/disasters/add";
        }
        redirectAttributes.addFlashAttribute("conversation", conversation);

        baseModel(model, userDetails);
        return "redirect:/disasters/add";
    }

    @GetMapping("list")
    public String showDisasterList(Model model, @AuthenticationPrincipal AppUser userDetails) {
        baseModel(model, userDetails);

        if (!model.containsAttribute("list")) {
            model.addAttribute("list", getDisasterDTOS("Wszystkie", "Wszystkie"));
        }

        model.addAttribute("googleApiKey", googleApiKey);
        model.addAttribute("inLocationDisasterAmount", inLocationDisastersAmount());
        model.addAttribute("disasterTypes", DisasterType.values());
        return "listDisasters";
    }

    @PostMapping("list")
    public String filterList(@RequestParam(name = "disasterType") String disasterType,
                             @RequestParam(name = "city") String city,
                             RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("list", getDisasterDTOS(disasterType, city));
        return "redirect:/disasters/list";
    }

    private Map<String, Integer> inLocationDisastersAmount() {
        return disasterAlertFacade.inLocationDisastersAmount();
    }

    private List<DisasterDTO> getDisasterDTOS(String disasterType, String city) {
        Optional<DisasterType> type;
        if (disasterType.isEmpty() || disasterType.equals("Wszystkie")) {
            type = Optional.empty();
        } else {
            type = Optional.of(DisasterType.valueOf(disasterType));
        }

        Optional<String> location;
        if (city.isEmpty() || city.equals("Wszystkie")) {
            location = Optional.empty();
        } else {
            location = Optional.of(city);
        }

        return disasterAlertFacade.interestingDisasters(type, location);
    }

    private void baseModel(Model model, AppUser userDetails) {
        model.addAttribute("userId", userDetails.getUserDTO().id());
        model.addAttribute("disasterType", DisasterType.values());
        model.addAttribute("disasterStatus", DisasterStatus.values());
        model.addAttribute("cities", CitiesInPoland.getList());
    }
}
