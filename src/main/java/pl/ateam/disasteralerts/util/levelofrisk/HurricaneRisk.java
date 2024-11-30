package pl.ateam.disasteralerts.util.levelofrisk;

import java.util.HashMap;

class HurricaneRisk {

    private static final HashMap<RiskLevel, String> HURRICANE_RISKS = new HashMap<>();

    static {
        HURRICANE_RISKS.put(RiskLevel.HIGH,
                "Huragan z niszczycielskimi wiatrami i intensywnymi opadami, powodujący poważne zniszczenia.");
        HURRICANE_RISKS.put(RiskLevel.MEDIUM,
                "Huragan o mniejszej intensywności, silne wiatry i deszcze, ryzyko lokalnych uszkodzeń budynków i infrastruktury");
        HURRICANE_RISKS.put(RiskLevel.LOW,
                "Burza lub osłabiony huragan, minimalne ryzyko dla życia i mienia.");
    }

    public static HashMap<RiskLevel, String> getList() {
        return HURRICANE_RISKS;
    }
}
