package me.HSGamer.NKNC;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.HSGamer.NKNC.listeners.DeathDropsAPIEvent;
import me.HSGamer.NKNC.task.DeathDropsAPIHook;
import me.HSGamer.NKNC.task.Default;

public class NKNC extends JavaPlugin {

	public List<World> world;
	public boolean isNight;
	public boolean isKeep;
	public BukkitRunnable runnable;
	public String messagenight;
	public String messageday;
	public String messagekeepnight;
	public double chance;
	
	public void onEnable() {
		
		saveDefaultConfig();
		
		for (String a : this.getConfig().getStringList("world")) {
			world.add(this.getServer().getWorld(a));
		}
		
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
			runnable = new DeathDropsAPIHook();
			this.getServer().getPluginManager().registerEvents(new DeathDropsAPIEvent(), this);
		}
		else {
			runnable = new Default();
		}
		
		runnable.runTaskTimer(this, 20L, 20L);
		Bukkit.getConsoleSender().sendMessage("§aNonKeepNightChance Enabled");
	}
	
	public void onDisable() {
		runnable.cancel();
		HandlerList.unregisterAll(this);
	}
	
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command arg1, String cmd, String[] arg) {
		
		this.world.clear();
		for (String e : this.getConfig().getStringList("world")) {
			world.add(this.getServer().getWorld(e));
		}
		
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
