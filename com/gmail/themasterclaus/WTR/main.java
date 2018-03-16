package com.gmail.themasterclaus.WTR;

import cf.magsoo.magictitles.AppearingTitle;
import cf.magsoo.magictitles.MagicTitle;
import cf.magsoo.magictitles.NormalTitle;
import cf.magsoo.magictitles.TitleSlot;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class main extends JavaPlugin {
    public boolean isnight = false;
    public String messageday = "";
    public String messagenight = "";
    public double chance = 0.0;
    public String messagekeepnight = "";
    public String titlekeepday = "";
    public String subtitlekeepday = "";
    public String titlekeepnight = "";
    public String subtitlekeepnight = "";
    public String titlenonkeep = "";
    public String subtitlenonkeep = "";
    public String keepactionbar = "";
    public String nonkeepactionbar = "";
    public World world = null;
    public double c = 0.0;

    public void onEnable() {
        this.saveDefaultConfig();
        this.world = this.getServer().getWorld(this.getConfig().getString("world"));
        this.messageday = this.getConfig().getString("messageday");
        this.messagenight = this.getConfig().getString("messagenight");
        this.messagekeepnight = this.getConfig().getString("messagekeepnight");
        this.titlekeepday = this.getConfig().getString("titlekeepday");
        this.subtitlekeepday = this.getConfig().getString("subtitlekeepday");
        this.titlekeepnight = this.getConfig().getString("titlekeepnight");
        this.subtitlekeepnight = this.getConfig().getString("subtitlekeepnight");
        this.titlenonkeep = this.getConfig().getString("titlenonkeep");
        this.subtitlenonkeep = this.getConfig().getString("subtitlenonkeep");
        this.keepactionbar = this.getConfig().getString("keepactionbar");
        this.nonkeepactionbar = this.getConfig().getString("nonkeepactionbar");
        this.chance = this.getConfig().getDouble("chance");
        NormalTitle titleskeepday = new NormalTitle(TitleSlot.TITLE_SUBTITLE, this.titlekeepday.replace("&", "\u00a7"), this.subtitlekeepday.replace("&", "\u00a7"), 5, 15, 5);
        AppearingTitle titleskeepnight = new AppearingTitle(TitleSlot.TITLE_SUBTITLE, this.titlekeepnight.replace("&", "\u00a7"), this.subtitlekeepnight.replace("&", "\u00a7"), 20, 5, 30, true);
        MagicTitle titlesnonkeep = new MagicTitle(TitleSlot.TITLE_SUBTITLE, this.titlenonkeep.replace("&", "\u00a7"), this.subtitlenonkeep.replace("&", "\u00a7"), 3, 20, 5, 30, true);
        AppearingTitle actionbarkeep = new AppearingTitle(TitleSlot.ACTIONBAR, this.keepactionbar.replace("&", "\u00a7"), 30);
        AppearingTitle actionbarnonkeep = new AppearingTitle(TitleSlot.ACTIONBAR, this.nonkeepactionbar.replace("&", "\u00a7"), 30);
        if (this.chance > 1.0 || this.chance < 0.0) {
            Bukkit.getConsoleSender().sendMessage("Unknown 'chance' value");
            Bukkit.getConsoleSender().sendMessage("Must be from 0.0 to 1.0");
            Bukkit.getConsoleSender().sendMessage("Setting to 0.5 ...");
            this.getConfig().set("chance", (Object)0.5);
            this.saveConfig();
            this.chance = this.getConfig().getDouble("chance");
        }
        this.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this, new Runnable(){

            @Override
            public void run() {
                block7 : {
                    block6 : {
                        if (main.this.world == null) {
                            return;
                        }
                        if (!main.this.isnight || main.this.world.getTime() < 0 || main.this.world.getTime() >= 13700) break block6;
                        main.this.isnight = false;
                        main.this.world.setGameRuleValue("keepInventory", "true");
                        for (Player p : main.this.world.getPlayers()) {
                            p.sendMessage(main.this.messageday.replace("&", "\u00a7"));
                            titleskeepday.send(p);
                        }
                        break block7;
                    }
                    if (main.this.isnight || main.this.world.getTime() < 13700) break block7;
                    double c = Math.random();
                    if (c <= main.this.chance) {
                        main.this.isnight = true;
                        main.this.world.setGameRuleValue("keepInventory", "false");
                        for (Player p : main.this.world.getPlayers()) {
                            p.sendMessage(main.this.messagenight.replace("&", "\u00a7"));
                            titlesnonkeep.send(p);
                            actionbarnonkeep.send(p);
                        }
                    } else {
                        main.this.isnight = true;
                        main.this.world.setGameRuleValue("keepInventory", "true");
                        for (Player p : main.this.world.getPlayers()) {
                            p.sendMessage(main.this.messagekeepnight.replace("&", "\u00a7"));
                            titleskeepnight.send(p);
                            actionbarkeep.send(p);
                        }
                    }
                }
            }
        }, 20, 20);
    }

    public void onDisable() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        this.reloadConfig();
        this.world = this.getServer().getWorld(this.getConfig().getString("world"));
        this.messageday = this.getConfig().getString("messageday");
        this.messagenight = this.getConfig().getString("messagenight");
        this.messagekeepnight = this.getConfig().getString("messagekeepnight");
        this.titlekeepday = this.getConfig().getString("titlekeepday");
        this.subtitlekeepday = this.getConfig().getString("subtitlekeepday");
        this.titlekeepnight = this.getConfig().getString("titlekeepnight");
        this.subtitlekeepnight = this.getConfig().getString("subtitlekeepnight");
        this.titlenonkeep = this.getConfig().getString("titlenonkeep");
        this.subtitlenonkeep = this.getConfig().getString("subtitlenonkeep");
        this.keepactionbar = this.getConfig().getString("keepactionbar");
        this.nonkeepactionbar = this.getConfig().getString("nonkeepactionbar");
        this.chance = this.getConfig().getDouble("chance");
        if (this.chance > 1.0 || this.chance < 0.0) {
            sender.sendMessage("\u00a7cUnknown 'chance' value");
            sender.sendMessage("\u00a7cMust be from 0.0 to 1.0");
            sender.sendMessage("\u00a7bSetting to 0.5 ...");
            this.getConfig().set("chance", (Object)0.5);
            this.saveConfig();
            this.chance = this.getConfig().getDouble("chance");
        }
        NormalTitle titleskeepday = new NormalTitle(TitleSlot.TITLE_SUBTITLE, this.titlekeepday.replace("&", "\u00a7"), this.subtitlekeepday.replace("&", "\u00a7"), 5, 15, 5);
        AppearingTitle titleskeepnight = new AppearingTitle(TitleSlot.TITLE_SUBTITLE, this.titlekeepnight.replace("&", "\u00a7"), this.subtitlekeepnight.replace("&", "\u00a7"), 20, 5, 30, true);
        MagicTitle titlesnonkeep = new MagicTitle(TitleSlot.TITLE_SUBTITLE, this.titlenonkeep.replace("&", "\u00a7"), this.subtitlenonkeep.replace("&", "\u00a7"), 3, 20, 5, 30, true);
        AppearingTitle actionbarkeep = new AppearingTitle(TitleSlot.ACTIONBAR, this.keepactionbar.replace("&", "\u00a7"), 30);
        AppearingTitle actionbarnonkeep = new AppearingTitle(TitleSlot.ACTIONBAR, this.nonkeepactionbar.replace("&", "\u00a7"), 30);
        sender.sendMessage("\u00a7a\u00a7lReload.");
        return true;
    }

}

