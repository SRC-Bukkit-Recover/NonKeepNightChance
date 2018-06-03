package me.HSGamer.NKNC;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import net.minefs.DeathDropsAPI.PlayerDeathDropEvent;

public class Listeners implements Listener {
	
	@EventHandler(ignoreCancelled=true)
	public void onDrop(PlayerDeathDropEvent e) {
		if (NKNC.isKeepWorld(e.getPlayer().getWorld().getName()) || e.getPlayer().getWorld().getGameRuleValue("keepinventory") == "true") {
			e.setCancelled(true);
		}
	}
	
}
