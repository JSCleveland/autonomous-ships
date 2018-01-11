package org.tc.autonomous.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;
import org.tc.autonomous.AutonomousCommandsPlugin;

public abstract class AbstractRetreatCommandHullMod extends BaseHullMod {
	private static final String PREFIX = AutonomousCommandsPlugin.RETREAT_COMMAND_HULL_PREFIX;

	private int id;

	AbstractRetreatCommandHullMod(int id) {
		this.id = id;
	}

	@Override
	public boolean isApplicableToShip(ShipAPI ship) {  
		for (String hullMod : ship.getVariant().getHullMods()) {
			if (hullMod.startsWith(PREFIX) && !hullMod.equals(PREFIX + id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String getUnapplicableReason(ShipAPI ship) {
		return "Retreat (hull) hullmods are mutually exclusive";
	}

	@Override
	public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
		double ratio = AutonomousCommandsPlugin.retreatHullMods.get(PREFIX + id);
		int percent = (int)(ratio * 100.0);
		return Integer.toString(percent);
	}
}
