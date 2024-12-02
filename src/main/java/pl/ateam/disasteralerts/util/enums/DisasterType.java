package pl.ateam.disasteralerts.util.enums;

public enum DisasterType {
    STORM("Burza"),
    FIRE("Pożar"),
    FLOOD("Powódź"),
    HURRICANE("Huragan"),
    BLIZZARD("Śnieżyca"),
    DROUGHT("Susza"),
    HEAT("Upał");

    private final String polishName;

    DisasterType(String polishName) {
        this.polishName = polishName;
    }

    public String getPolishName() {
        return polishName;
    }
}
