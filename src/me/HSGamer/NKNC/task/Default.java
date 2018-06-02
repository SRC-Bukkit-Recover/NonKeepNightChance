package me.HSGamer.NKNC.task;

import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import me.HSGamer.NKNC.NKNC;

public class Default extends BukkitRunnable {

	NKNC a = new NKNC();
	
	@Override
	public void run() {
		
		for (World e : a.world) {
			if (a.isNight && e.getTime() >= 0L && e.getTime() < 13700L) {
				
				e.setGameRuleValue("keepinventory", "true");
				a.getServer().getConsoleSender().sendMessage(a.messageday.replaceAll("&", "§"));
				a.isNight = false;
			}
			else if (!a.isNight && e.getTime() >= 13700L) {
				
				if (Math.random() <= a.chance) {
					e.setGameRuleValue("keepinventory", "false");
					a.getServer().getConsoleSender().sendMessage(a.messagenight.replaceAll("&", "§"));
				} else {
					a.getServer().getConsoleSender().sendMessage(a.messagekeepnight.replaceAll("&", "§"));
				}
				
				a.isNight = true;
			}
		}
		
	}

}
