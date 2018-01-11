package org.tc.autonomous.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;

public abstract class AbstractRetreatCrBelowHullMod extends BaseHullMod {
	private static final String PREFIX = "autonomous_retreat_cr_below_";

	private int threshold;

	AbstractRetreatCrBelowHullMod(int threshold) {
		this.threshold = threshold;
	}

	public boolean isApplicableToShip(ShipAPI ship) {  
		for (String hullMod : ship.getVariant().getHullMods()) {
			if (hullMod.startsWith(PREFIX) && !hullMod.equals(PREFIX + threshold)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String getUnapplicableReason(ShipAPI ship) {
		return "Retreat (CR) hullmods are mutually exclusive";
	}

	@Override
	public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
		return Integer.toString(threshold);
	}
}
