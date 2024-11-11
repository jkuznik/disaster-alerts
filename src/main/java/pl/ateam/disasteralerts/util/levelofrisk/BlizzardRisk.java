package pl.ateam.disasteralerts.util.levelofrisk;

import java.util.HashMap;

class BlizzardRisk {

    private static final HashMap<RiskLevel, String> BLIZZARD_RISKS = new HashMap<>();

    static {
        BLIZZARD_RISKS.put(RiskLevel.HIGH,
                "Intensywna zamieć z porywistym wiatrem i widocznością poniżej 200 metrów, niebezpieczna dla zdrowia i transportu.");
        BLIZZARD_RISKS.put(RiskLevel.MEDIUM,
                "Ciężkie opady śniegu z wiatrem, ograniczona widoczność i utrudnienia komunikacyjne.");
        BLIZZARD_RISKS.put(RiskLevel.LOW,
                "Opady śniegu bez silnych wiatrów, powodujące lekkie utrudnienia drogowe.");
    }

    public static HashMap<RiskLevel, String> getList() {
        return BLIZZARD_RISKS;
    }

}
