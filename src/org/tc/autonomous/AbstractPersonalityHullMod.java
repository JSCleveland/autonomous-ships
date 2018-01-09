package org.tc.autonomous;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;

public abstract class AbstractPersonalityHullMod extends BaseHullMod {
    private static final String PREFIX = "autonomous_personality_";

    private String personality;

    AbstractPersonalityHullMod(String personality) {
        this.personality = personality;
    }

    public boolean isApplicableToShip(ShipAPI ship) {
        for (String hullMod : ship.getVariant().getHullMods()) {
            if (hullMod.startsWith(PREFIX) && !hullMod.equals(PREFIX + personality)) {
                return false;
            }
        }
        return true;
    }
}
