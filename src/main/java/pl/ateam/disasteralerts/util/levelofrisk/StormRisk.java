package pl.ateam.disasteralerts.util.levelofrisk;

import java.util.HashMap;

public class StormRisk {

    private static final HashMap<RiskLevel, String> STORM_RISKS = new HashMap<>();
    static {
        STORM_RISKS.put(RiskLevel.HIGH,
                "Silne porywy wiatru, intensywne opady deszczu lub gradu");
        STORM_RISKS.put(RiskLevel.MEDIUM,
                "Burza o umiarkowanej sile");
        STORM_RISKS.put(RiskLevel.LOW,
                "Słaba burza z lekkimi porywami wiatru , przelotnymi opadami deszczu");
    }

    public static HashMap<RiskLevel, String> getList() {
        return STORM_RISKS;
    }
}
