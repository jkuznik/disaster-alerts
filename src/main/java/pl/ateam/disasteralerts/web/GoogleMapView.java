package pl.ateam.disasteralerts.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GoogleMapView {

    @Value("${google.maps.api.key}")
    private String googleApiKey;

    @GetMapping("/maps")
    public String showMap(Model model) {
        model.addAttribute("googleApiKey", googleApiKey);
        return "map";
    }
}
