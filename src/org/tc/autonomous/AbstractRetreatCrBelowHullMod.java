package org.tc.autonomous;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;

public abstract class AbstractRetreatCrBelowHullMod extends BaseHullMod {
	private static final String PREFIX = "autonomous_retreat_cr_below_";

	private int threshold;

	protected AbstractRetreatCrBelowHullMod(int threshold) {
		this.threshold = threshold;
	}

	public boolean isApplicableToShip(ShipAPI ship) {  
		for (String hullMod : ship.getVariant().getHullMods()) {
			if (hullMod.startsWith(PREFIX)) {
				return false;
			}
		}
		return true;
	}

	public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
		return Integer.toString(threshold);
	}
}
