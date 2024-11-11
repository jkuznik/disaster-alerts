package pl.ateam.disasteralerts.util.levelofrisk;

import java.util.HashMap;

class HeatRisk {

    private static final HashMap<RiskLevel, String> HEAT_RISKS = new HashMap<>();

    static {
        HEAT_RISKS.put(RiskLevel.HIGH,
                "Ekstremalna fala upałów (>40°C), zwiększone ryzyko dla zdrowia (udar cieplny) i problemy z infrastrukturą.");
        HEAT_RISKS.put(RiskLevel.MEDIUM,
                "Długotrwałe wysokie temperatury, niewielkie ryzyko dla zdrowia przy ograniczeniu aktywności fizycznej.");
        HEAT_RISKS.put(RiskLevel.LOW,
                "Gorąca pogoda, ale bez ekstremalnych temperatur, nieznaczny wpływ na życie codzienne.");
    }

    public static HashMap<RiskLevel, String> getList() {
        return HEAT_RISKS;
    }
}
