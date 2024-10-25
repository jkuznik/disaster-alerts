package pl.ateam.disasteralerts.util;

import java.util.Arrays;
import java.util.List;

public class CitiesInPoland {

    private static final List<String> CITIES = Arrays.asList(
        "Białystok",
        "Bielsko-Biała",
        "Bydgoszcz",
        "Częstochowa",
        "Elbląg",
        "Gdańsk",
        "Gdynia",
        "Gliwice",
        "Gorzów Wielkopolski",
        "Jelenia Góra",
        "Kalisz",
        "Katowice",
        "Kielce",
        "Koszalin",
        "Kraków",
        "Legnica",
        "Lublin",
        "Łódź",
        "Olsztyn",
        "Opole",
        "Płock",
        "Poznań",
        "Radom",
        "Rzeszów",
        "Siedlce",
        "Sosnowiec",
        "Szczecin",
        "Toruń",
        "Wałbrzych",
        "Warszawa",
        "Wrocław",
        "Zabrze",
        "Zielona Góra"
    );

    public static List<String> getList() {
        return CITIES;
    }

}
