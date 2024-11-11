package pl.ateam.disasteralerts.util.levelofrisk;

import java.util.HashMap;

class FloodRisk {

    private static final HashMap<RiskLevel, String> FLOOD_RISKS = new HashMap<>();

    static {
        FLOOD_RISKS.put(RiskLevel.HIGH,
                "Powódź powodująca szybki wzrost poziomu wody, zalewająca budynki mieszkalne i drogi, wymuszająca ewakuacje");
        FLOOD_RISKS.put(RiskLevel.MEDIUM,
                "Podtopienia, utrudnienia w ruchu drogowym, niewielkie szkody materialne");
        FLOOD_RISKS.put(RiskLevel.LOW,
                "Lokalny wzrost poziomu wody, bez większego wpływu na infrastrukturę");
    }

    public static HashMap<RiskLevel, String> getList() {
        return FLOOD_RISKS;
    }
}
