package me.HSGamer.NKNC;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import net.minefs.DeathDropsAPI.PlayerDeathDropEvent;

public class DeathDropAPIListeners implements Listener {
	
	@EventHandler
	public void onDrop(PlayerDeathDropEvent e) {
		if (NKNC.isKeepWorld(e.getPlayer().getWorld().getName()) || e.getPlayer().getWorld().getGameRuleValue("keepInventory") == "true") {
			e.setCancelled(true);
		}
	}
	
}
