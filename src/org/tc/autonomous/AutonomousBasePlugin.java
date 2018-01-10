package org.tc.autonomous;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.PluginPick;
import com.fs.starfarer.api.campaign.CampaignPlugin;
import com.fs.starfarer.api.combat.ShipAIConfig;
import com.fs.starfarer.api.combat.ShipAIPlugin;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import org.apache.log4j.Logger;

public class AutonomousBasePlugin extends BaseModPlugin {
	private static Logger LOG = Global.getLogger(AutonomousBasePlugin.class);

	@Override
	public PluginPick<ShipAIPlugin> pickShipAI(FleetMemberAPI member, ShipAPI ship) {
		final String PREFIX = "autonomous_personality_";
		ShipAIConfig config = new ShipAIConfig();
		String personality = "steady";

		boolean hasHullMod = false;
		if (ship.getCaptain() != null && ship.getCaptain().isDefault()) {
			for (String hullMod : ship.getVariant().getHullMods()) {
				if (hullMod.startsWith(PREFIX)) {
					hasHullMod = true;
					personality = hullMod.split("_")[2];
					config.personalityOverride = personality;
					break;
				}
			}
		}
		if (!hasHullMod) {
			return null;
		}

		LOG.info("Applying personality [" + personality + "] to ship [" + ship.getName() + "]");
		return new PluginPick<ShipAIPlugin>(Global.getSettings().createDefaultShipAI(ship, config), CampaignPlugin.PickPriority.MOD_SPECIFIC);
	}
}
