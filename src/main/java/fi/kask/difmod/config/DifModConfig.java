package fi.kask.difmod.config;

import java.util.HashMap;
import java.util.Map;

public class DifModConfig {
    public Map<String, PlayerDamage> PlayerDamages;

    public DifModConfig() {
        PlayerDamages = new HashMap<>();
    }
}
