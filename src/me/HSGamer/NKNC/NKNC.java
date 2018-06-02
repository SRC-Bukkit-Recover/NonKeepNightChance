package me.HSGamer.NKNC;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.minefs.DeathDropsAPI.PlayerDeathDropEvent;

public class NKNC extends JavaPlugin {

	public List<String> world;
	public boolean isNight;
	public boolean isKeep;
	public BukkitRunnable runnable;
	public String messagenight;
	public String messageday;
	public String messagekeepnight;
	public double chance;
	
	public void onEnable() {
		
		saveDefaultConfig();
		
		world = this.getConfig().getStringList("world");
		messageday = this.getConfig().getString("messageday");
		messagenight = this.getConfig().getString("messagenight");
		messagekeepnight = this.getConfig().getString("messagekeepnight");
		chance = this.getConfig().getDouble("chance");
		
        if (this.chance > 1.0 || this.chance < 0.0) {
            Bukkit.getConsoleSender().sendMessage("§cUnknown 'chance' value");
            Bukkit.getConsoleSender().sendMessage("§cMust be from 0.0 to 1.0");
            Bukkit.getConsoleSender().sendMessage("§bSetting to 0.5 ...");
            this.getConfig().set("chance", 0.5);
            this.saveConfig();
            this.chance = this.getConfig().getDouble("chance");
        }
        
		if (this.getServer().getPluginManager().getPlugin("DeathDropsAPI").isEnabled()) {
			Bukkit.getConsoleSender().sendMessage("§eHooking with DeathDropsAPI");
			runnable = new BukkitRunnable() {

				@Override
				public void run() {
					for (String b : world) {
						
						World e = Bukkit.getWorld(b);
						if (isNight && e.getTime() >= 0L && e.getTime() < 13700L) {
							
							isKeep = true;
							isNight = false;
						}
						else if (!isNight && e.getTime() >= 13700L) {
							
							if (Math.random() <= chance) {
								isKeep = false;
								getServer().getConsoleSender().sendMessage(messagenight.replaceAll("&", "§"));
							} else {
								getServer().getConsoleSender().sendMessage(messagekeepnight.replaceAll("&", "§"));
							}
							
							isNight = true;
						}
					}
				}
				
			};
			this.getServer().getPluginManager().registerEvents(new Listener( ) {
				@EventHandler(ignoreCancelled=true)
				public void onDrop(PlayerDeathDropEvent e) {
					if (isKeep) {
						e.setCancelled(true);
					}
				}
			}, this);
		}
		else {
			runnable = new BukkitRunnable() {

				@Override
				public void run() {
					for (String b : world) {
						World e = Bukkit.getWorld(b);
						if (isNight && e.getTime() >= 0L && e.getTime() < 13700L) {
							
							e.setGameRuleValue("keepinventory", "true");
							getServer().getConsoleSender().sendMessage(messageday.replaceAll("&", "§"));
							isNight = false;
						}
						else if (!isNight && e.getTime() >= 13700L) {
							
							if (Math.random() <= chance) {
								e.setGameRuleValue("keepinventory", "false");
								getServer().getConsoleSender().sendMessage(messagenight.replaceAll("&", "§"));
							} else {
								getServer().getConsoleSender().sendMessage(messagekeepnight.replaceAll("&", "§"));
							}
							
							isNight = true;
						}
					}
				}
				
			};
		}
		
		runnable.runTaskTimer(this, 20L, 20L);
		Bukkit.getConsoleSender().sendMessage("§aNonKeepNightChance Enabled");
	}
	
	public void onDisable() {
		runnable.cancel();
		HandlerList.unregisterAll(this);
	}
	
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command arg1, String cmd, String[] arg) {
		
		world = this.getConfig().getStringList("world");
		messageday = this.getConfig().getString("messageday");
		messagenight = this.getConfig().getString("messagenight");
		messagekeepnight = this.getConfig().getString("messagekeepnight");
		chance = this.getConfig().getDouble("chance");
		
        if (this.chance > 1.0 || this.chance < 0.0) {
            sender.sendMessage("§cUnknown 'chance' value");
            sender.sendMessage("§cMust be from 0.0 to 1.0");
            sender.sendMessage("§bSetting to 0.5 ...");
            this.getConfig().set("chance", 0.5);
            this.saveConfig();
            this.chance = this.getConfig().getDouble("chance");
        }
        
		return true;
	}
	
}
