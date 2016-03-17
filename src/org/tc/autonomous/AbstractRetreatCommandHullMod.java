package org.tc.autonomous;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;

public abstract class AbstractRetreatCommandHullMod extends BaseHullMod {
	private int id;

	protected AbstractRetreatCommandHullMod(int id) {
		this.id = id;
	}

	public boolean isApplicableToShip(ShipAPI ship) {  
		for (String hullMod : AutonomousCommandsPlugin.retreatHullMods.keySet()) {
			if (ship.getVariant().hasHullMod(hullMod)) {
				return false;
			}
		}
		return true;
	}

	public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
		double ratio = AutonomousCommandsPlugin.retreatHullMods.get(AutonomousCommandsPlugin.RETREAT_COMMAND_PREFIX + id);
		int percent = (int)(ratio * 100.0);
		return Integer.toString(percent);
	}
}
