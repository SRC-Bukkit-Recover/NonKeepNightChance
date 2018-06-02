package com.gmail.themasterclaus.WTR;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import java.util.Iterator;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import cf.magsoo.magictitles.MagicTitle;
import cf.magsoo.magictitles.AppearingTitle;
import cf.magsoo.magictitles.NormalTitle;
import cf.magsoo.magictitles.TitleSlot;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin
{
    public boolean isnight;
    public String messageday;
    public String messagenight;
    public double chance;
    public String messagekeepnight;
    public String titlekeepday;
    public String subtitlekeepday;
    public String titlekeepnight;
    public String subtitlekeepnight;
    public String titlenonkeep;
    public String subtitlenonkeep;
    public String keepactionbar;
    public String nonkeepactionbar;
    public World world;
    public double c;
    
    public main() {
        this.isnight = false;
        this.messageday = "";
        this.messagenight = "";
        this.chance = 0.0;
        this.messagekeepnight = "";
        this.titlekeepday = "";
        this.subtitlekeepday = "";
        this.titlekeepnight = "";
        this.subtitlekeepnight = "";
        this.titlenonkeep = "";
        this.subtitlenonkeep = "";
        this.keepactionbar = "";
        this.nonkeepactionbar = "";
        this.world = null;
        this.c = 0.0;
    }
    
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

        NormalTitle titleskeepday = new NormalTitle(TitleSlot.TITLE_SUBTITLE, this.titlekeepday.replace("&", "§"), this.subtitlekeepday.replace("&", "§"), 5, 15, 5);
        AppearingTitle titleskeepnight = new AppearingTitle(TitleSlot.TITLE_SUBTITLE, this.titlekeepnight.replace("&", "§"), this.subtitlekeepnight.replace("&", "§"), 20, 5, 30, true);
        MagicTitle titlesnonkeep = new MagicTitle(TitleSlot.TITLE_SUBTITLE, this.titlenonkeep.replace("&", "§"), this.subtitlenonkeep.replace("&", "§"), 3, 20, 5, 30, true);
        AppearingTitle actionbarkeep = new AppearingTitle(TitleSlot.ACTIONBAR, this.keepactionbar.replace("&", "§"), 30);
        AppearingTitle actionbarnonkeep = new AppearingTitle(TitleSlot.ACTIONBAR, this.nonkeepactionbar.replace("&", "§"), 30);

        if (this.chance > 1.0 || this.chance < 0.0) {
            Bukkit.getConsoleSender().sendMessage("Unknown 'chance' value");
            Bukkit.getConsoleSender().sendMessage("Must be from 0.0 to 1.0");
            Bukkit.getConsoleSender().sendMessage("Setting to 0.5 ...");
            this.getConfig().set("chance", (Object)0.5);
            this.saveConfig();
            this.chance = this.getConfig().getDouble("chance");
        }

        this.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this, (Runnable)new Runnable() {
            @Override
            public void run() {
                if (main.this.world == null) {
                    return;
                }
                if (main.this.isnight && main.this.world.getTime() >= 0L && main.this.world.getTime() < 13700L) {
                    main.this.isnight = false;
                    main.this.world.setGameRuleValue("keepInventory", "true");
                    for (final Player p : main.this.world.getPlayers()) {
                        Bukkit.getConsoleSender().sendMessage(main.this.messageday.replace("&", "§"));
                        p.sendMessage(main.this.messageday.replace("&", "§"));
                        titleskeepday.send(p);
                    }
                }
                else if (!main.this.isnight && main.this.world.getTime() >= 13700L) {
                    double c = Math.random();
                    if (c <= main.this.chance) {
                        main.this.isnight = true;
                        main.this.world.setGameRuleValue("keepInventory", "false");
                        for (final Player p2 : main.this.world.getPlayers()) {
                            Bukkit.getConsoleSender().sendMessage(main.this.messagenight.replace("&", "§"));
                            p2.sendMessage(main.this.messagenight.replace("&", "§"));
                            titlesnonkeep.send(p2);
                            actionbarnonkeep.send(p2);
                        }
                    }
                    else {
                        main.this.isnight = true;
                        main.this.world.setGameRuleValue("keepInventory", "true");
                        for (final Player p2 : main.this.world.getPlayers()) {
                            Bukkit.getConsoleSender().sendMessage(main.this.messagekeepnight.replace("&", "§"));
                            p2.sendMessage(main.this.messagekeepnight.replace("&", "§"));
                            titleskeepnight.send(p2);
                            actionbarkeep.send(p2);
                        }
                    }
                }
            }
        }, 20L, 20L);
    }
    
    public void onDisable() {
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
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
            sender.sendMessage("§cUnknown 'chance' value");
            sender.sendMessage("§cMust be from 0.0 to 1.0");
            sender.sendMessage("§bSetting to 0.5 ...");
            this.getConfig().set("chance", (Object)0.5);
            this.saveConfig();
            this.chance = this.getConfig().getDouble("chance");
        }

        NormalTitle titleskeepday = new NormalTitle(TitleSlot.TITLE_SUBTITLE, this.titlekeepday.replace("&", "§"), this.subtitlekeepday.replace("&", "§"), 5, 15, 5);
        AppearingTitle titleskeepnight = new AppearingTitle(TitleSlot.TITLE_SUBTITLE, this.titlekeepnight.replace("&", "§"), this.subtitlekeepnight.replace("&", "§"), 20, 5, 30, true);
        MagicTitle titlesnonkeep = new MagicTitle(TitleSlot.TITLE_SUBTITLE, this.titlenonkeep.replace("&", "§"), this.subtitlenonkeep.replace("&", "§"), 3, 20, 5, 30, true);
        AppearingTitle actionbarkeep = new AppearingTitle(TitleSlot.ACTIONBAR, this.keepactionbar.replace("&", "§"), 30);
        AppearingTitle actionbarnonkeep = new AppearingTitle(TitleSlot.ACTIONBAR, this.nonkeepactionbar.replace("&", "§"), 30);

        sender.sendMessage("§a§lReload.");

        return true;
    }
}
