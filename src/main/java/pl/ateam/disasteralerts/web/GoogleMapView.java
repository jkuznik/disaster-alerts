package pl.ateam.disasteralerts.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class GoogleMapView {

    @Value("${google.maps.api.key}")
    private String googleApiKey;

    @GetMapping("/maps")
    public String showMap(Model model) {
        model.addAttribute("googleApiKey", googleApiKey);
        return "googleMaps";
    }
}
