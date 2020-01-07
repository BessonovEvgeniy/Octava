package octava.model.observation.header.impl;

public enum ObsType {

    L1("G"), L2("G"), L5("G"), C1("G"), P2("G"), G1("R"), G2("R");

    private final String system;

    ObsType(String systemCode) {
        system = systemCode;
    }

    public String getSystem() {
        return system;
    }

    public boolean isSystemRequired(String satName) {
        return satName.contains(system);
    }

    @Override
    public String toString() {
        return this.name() + " system=" + system;
    }

    public static ObsType getTypeByName(String name) {
        for (ObsType type : ObsType.values()) {
            if (type.name().equals(name)) {
                return type;
            }
        }
        return null;
    }
}

