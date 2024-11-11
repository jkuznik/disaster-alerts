package pl.ateam.disasteralerts.util.levelofrisk;

import java.util.HashMap;

class DroughtRisk {

    private static final HashMap<RiskLevel, String> DROUGHT_RISKS = new HashMap<>();

    static {
        DROUGHT_RISKS.put(RiskLevel.HIGH,
                "Długotrwała susza z niedoborem wody, skutkująca stratami w rolnictwie i problemami z zaopatrzeniem w wodę.");
        DROUGHT_RISKS.put(RiskLevel.MEDIUM,
                "Krótkotrwała susza ograniczająca dostępność wody.");
        DROUGHT_RISKS.put(RiskLevel.LOW,
                "Przejściowy brak opadów, niewielki wpływ na środowisko i gospodarkę.");
    }

    public static HashMap<RiskLevel, String> getList() {
        return DROUGHT_RISKS;
    }
}
