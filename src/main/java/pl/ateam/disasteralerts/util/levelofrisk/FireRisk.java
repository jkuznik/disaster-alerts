package pl.ateam.disasteralerts.util.levelofrisk;

import java.util.HashMap;

class FireRisk {

    private static final HashMap<RiskLevel, String> FIRE_RISKS = new HashMap<>();

    static {
        FIRE_RISKS.put(RiskLevel.HIGH,
                "Rozprzestrzeniający się pożar lasu lub dużego budynku, zagrażający bezpośrednio życiu ludzi");
        FIRE_RISKS.put(RiskLevel.MEDIUM,
                "Pożar ograniczony do jednego budynku lub małego obszaru, z ryzykiem rozprzestrzeniania się");
        FIRE_RISKS.put(RiskLevel.LOW,
                "Mały pożar pod kontrolą, bez ryzyka rozprzestrzenienia się");
    }

    public static HashMap<RiskLevel, String> getList() {
        return FIRE_RISKS;
    }
}
