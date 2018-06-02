package me.HSGamer.NKNC.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.HSGamer.NKNC.NKNC;
import net.minefs.DeathDropsAPI.PlayerDeathDropEvent;

public class DeathDropsAPIEvent implements Listener {
	
	NKNC a = new NKNC();
	
	@EventHandler(ignoreCancelled=true)
	public void onDrop(PlayerDeathDropEvent e) {
		if (a.isKeep) {
			e.setCancelled(true);
		}
	}

}
