package me.HSGamer.NKNC;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import cf.magsoo.magictitles.AppearingTitle;
import cf.magsoo.magictitles.MagicTitle;
import cf.magsoo.magictitles.NormalTitle;
import cf.magsoo.magictitles.TitleSlot;

public class NKNC extends JavaPlugin {

	private static HashMap<String, Boolean> nightWorld = new HashMap<String, Boolean>();
	private static HashMap<String, Boolean> keepWorld = new HashMap<String, Boolean>();
	
	public BukkitRunnable runnable;
	
	public String messagenight;
	public String messageday;
	public String messagekeepnight;
	
	public double chance;
	
	public NormalTitle day;
	public NormalTitle actionbarday;
	public MagicTitle night;
	public NormalTitle actionbarnight;
	public AppearingTitle keepnight;
	public NormalTitle actionbarkeepnight;
	
	public void onEnable() {
		
		//Load default config.yml
		this.getConfig().options().copyHeader(true);
		saveDefaultConfig();
		
		// Check and register worlds;
		List<String> worldstring = this.getConfig().getStringList("world");
		for (String c : worldstring) {
			if (Bukkit.getWorld(c) != null) {
				if (Bukkit.getWorld(c).getTime() >= 0L && Bukkit.getWorld(c).getTime() < 13700L) {
					nightWorld.put(c, false);
				} else if (Bukkit.getWorld(c).getTime() >= 13700L) {
					nightWorld.put(c, true);
				}
				keepWorld.put(c, true);
				Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + c + " Registered");
			} else {
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + c + " is not a valid world, Ignored");
			}
		}
		
		// Get information from the configuration file
		// - day
		messageday = this.getConfig().getString("day.message");
		day = new NormalTitle(TitleSlot.TITLE_SUBTITLE, this.getConfig().getString("day.title.title").replaceAll("&", "§"), this.getConfig().getString("day.title.subtitle").replaceAll("&", "§"), 20, 40, 20);
		actionbarday = new NormalTitle(TitleSlot.ACTIONBAR, this.getConfig().getString("day.actionbar").replaceAll("&", "§"));
		// - night
		messagenight = this.getConfig().getString("night.message");
		night = new MagicTitle(TitleSlot.TITLE_SUBTITLE, this.getConfig().getString("night.title.title").replaceAll("&", "§"), this.getConfig().getString("night.title.subtitle").replaceAll("&", "§"), 20, 40, 20, 40);
		actionbarnight = new NormalTitle(TitleSlot.ACTIONBAR, this.getConfig().getString("night.actionbar").replaceAll("&", "§"));
		// - keepnight
		messagekeepnight = this.getConfig().getString("keepnight.message");
		keepnight = new AppearingTitle(TitleSlot.TITLE_SUBTITLE, this.getConfig().getString("keepnight.title.title").replaceAll("&", "§"), this.getConfig().getString("keepnight.title.subtitle").replaceAll("&", "§"), 20, 40, 20);
		actionbarkeepnight = new NormalTitle(TitleSlot.ACTIONBAR, this.getConfig().getString("keepnight.actionbar").replaceAll("&", "§"));
		
		// Get "chance" value
		chance = this.getConfig().getDouble("chance");
		
		// Check if "chance" value is invalid
        if (this.chance > 1.0 || this.chance < 0.0) {
            Bukkit.getConsoleSender().sendMessage("§cUnknown 'chance' value");
            Bukkit.getConsoleSender().sendMessage("§cMust be from 0.0 to 1.0");
            Bukkit.getConsoleSender().sendMessage("§bSetting to 0.5 ...");
            this.getConfig().set("chance", 0.5);
            this.saveConfig();
            this.chance = this.getConfig().getDouble("chance");
        }
        
        // Create Runnable
        runnable = new BukkitRunnable() {

			@Override
			public void run() {
				for (String b : nightWorld.keySet()) {
					
					World e = Bukkit.getWorld(b);
					if (isNightWorld(b) && e.getTime() >= 0L && e.getTime() < 13700L) {
						
						e.setGameRuleValue("keepinventory", "true");
						keepWorld.put(b, true);
						nightWorld.put(b, false);
						for (Player c : e.getPlayers()) {
							c.sendMessage(messageday.replaceAll("&", "§"));
							day.send(c);
							actionbarday.send(c);
						}
						Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[" + b + "] " + messageday.replaceAll("&", "§"));
					}
					else if (!isNightWorld(b) && e.getTime() >= 13700L) {
						
						if (Math.random() <= chance) {
							
							e.setGameRuleValue("keepinventory", "false");
							keepWorld.put(b, false);
							for (Player c : e.getPlayers()) {
								c.sendMessage(messagenight.replaceAll("&", "§"));
								night.send(c);
								actionbarnight.send(c);
							}
							Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[" + b + "] " + messagenight.replaceAll("&", "§"));
						} else {
							for (Player c : e.getPlayers()) {
								e.setGameRuleValue("keepinventory", "true");
								c.sendMessage(messagekeepnight.replaceAll("&", "§"));
								keepnight.send(c);
								actionbarkeepnight.send(c);
							}
							Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[" + b + "] " + messagekeepnight.replaceAll("&", "§"));
						}
						
						nightWorld.put(b, true);
					}
				}
			}
			
		};
		
		// Check if DeathDropsAPI is available
		if (this.getServer().getPluginManager().getPlugin("DeathDropsAPI").isEnabled()) {
			Bukkit.getConsoleSender().sendMessage("§eHooking with DeathDropsAPI");
			this.getServer().getPluginManager().registerEvents(new Listeners(), this);
		}
		
		// Run Runnable
		runnable.runTaskTimer(this, 20L, 20L);
		Bukkit.getConsoleSender().sendMessage("§aNonKeepNightChance Enabled");
	}
	
	public void onDisable() {
		
		// Disable All Tasks
		runnable.cancel();
		HandlerList.unregisterAll(this);
	}
	
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command arg1, String cmd, String[] arg) {
		
		this.getConfig().options().copyHeader(true);
		reloadConfig();
		
		nightWorld.clear();
		keepWorld.clear();
		List<String> worldstring = this.getConfig().getStringList("world");
		for (String c : worldstring) {
			if (Bukkit.getWorld(c) != null) {
				if (Bukkit.getWorld(c).getTime() >= 0L && Bukkit.getWorld(c).getTime() < 13700L) {
					nightWorld.put(c, false);
				} else if (Bukkit.getWorld(c).getTime() >= 13700L) {
					nightWorld.put(c, true);
				}
				keepWorld.put(c, false);
				Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + c + " Registered");
			} else {
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + c + " is not a valid world, Ignored");
			}
		}
		
		messageday = this.getConfig().getString("day.message");
		day = new NormalTitle(TitleSlot.TITLE_SUBTITLE, this.getConfig().getString("day.title.title").replaceAll("&", "§"), this.getConfig().getString("day.title.subtitle").replaceAll("&", "§"), 20, 40, 20);
		actionbarday = new NormalTitle(TitleSlot.ACTIONBAR, this.getConfig().getString("day.actionbar").replaceAll("&", "§"));
		messagenight = this.getConfig().getString("night.message");
		night = new MagicTitle(TitleSlot.TITLE_SUBTITLE, this.getConfig().getString("night.title.title").replaceAll("&", "§"), this.getConfig().getString("night.title.subtitle").replaceAll("&", "§"), 20, 40, 20, 40);
		actionbarnight = new NormalTitle(TitleSlot.ACTIONBAR, this.getConfig().getString("night.actionbar").replaceAll("&", "§"));
		messagekeepnight = this.getConfig().getString("keepnight.message");
		keepnight = new AppearingTitle(TitleSlot.TITLE_SUBTITLE, this.getConfig().getString("keepnight.title.title").replaceAll("&", "§"), this.getConfig().getString("keepnight.title.subtitle").replaceAll("&", "§"), 20, 40, 20);
		actionbarkeepnight = new NormalTitle(TitleSlot.ACTIONBAR, this.getConfig().getString("keepnight.actionbar").replaceAll("&", "§"));
		chance = this.getConfig().getDouble("chance");
		
        if (this.chance > 1.0 || this.chance < 0.0) {
            sender.sendMessage("§cUnknown 'chance' value");
            sender.sendMessage("§cMust be from 0.0 to 1.0");
            sender.sendMessage("§bSetting to 0.5 ...");
            this.getConfig().set("chance", 0.5);
            this.saveConfig();
            this.chance = this.getConfig().getDouble("chance");
        }
        
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "NonKeepNightChance Reloaded");
		return true;
	}
	
	public static boolean isNightWorld(String world) {
		if (!nightWorld.containsKey(world)) {
			return false;
		}
		return nightWorld.get(world);
	}
	
	public static boolean isKeepWorld(String world) {
		if (!keepWorld.containsKey(world)) {
			return false;
		}
		return keepWorld.get(world);
	}
}
