package me.KaasPlakje.MinetopiaBrandweer;
import java.util.ArrayList;
import java.util.List;
import me.KaasPlakje.MinetopiaBrandweer.Main;
import me.KaasPlakje.MinetopiaBrandweer.Utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements CommandExecutor {
    private PluginManager pm;

    public void onEnable() {
        saveDefaultConfig();
        this.s = new Sneeuwballen(this);
        this.s.runTaskTimerAsynchronously((Plugin) this, 0L, 5L);
        this.pm = Bukkit.getPluginManager();
        this.pm.registerEvents((Listener) new StickEvent(this), (Plugin) this);
        //REGISTER COMMANDS
        getCommand("brandweer").setExecutor(this);
    }

    private Sneeuwballen s;

    public Sneeuwballen getS() {
        return this.s;
    }


    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("brandweer")) {
            if (args != null) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("permissies") &&
                        sender instanceof Player) {
                        if (p.hasPermission("brandweer.permhulp"))
                            sendPermissies(p);
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("melding") &&
                        sender instanceof Player) {
                        performMelding(p);
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("help") &&
                        sender instanceof Player) {
                        sendHelp(sender);
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("slang") &&
                        sender instanceof Player) {
                        if (sender.hasPermission("brandweer.slang")) {
                            ItemStack is = new ItemStack(Material.STICK);
                            ItemMeta im = is.getItemMeta();
                            im.setDisplayName(Utils.chat(this.getConfig().getString("slang_naam")));
                            List<String> lore = new ArrayList<>();
                            lore.add(Utils.chat(this.getConfig().getString("slang_lore1")));
                            lore.add(Utils.chat(this.getConfig().getString("slang_lore2")));
                            im.setLore(lore);
                            is.setItemMeta(im);
                            p.getInventory().addItem(new ItemStack[] {
                                is
                            });
                            p.sendMessage(Utils.chat(this.getConfig().getString("slang_gekregen")));
                        } else if (!sender.hasPermission("brandweer.slang")) {
                            sender.sendMessage(Utils.chat(this.getConfig().getString("slang_perm_bericht")));
                        }
                    }
                }
            }
        }
        sendHelp(sender);
        return true;
    }

    public void sendPermissies(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_RED + "§m----§4(§cBrandweer§4)§m----");
        sender.sendMessage(ChatColor.RED + "- brandweer.melding.krijg - /bw melding");
        sender.sendMessage(ChatColor.RED + "- brandweer.slang - /bw slang");
        sender.sendMessage(ChatColor.RED + " ");
        sender.sendMessage(ChatColor.DARK_RED + "§4MTBrandweer §cplugin §cgemaakt door §4KaasPlakje§c.");
        sender.sendMessage(ChatColor.DARK_RED + "§m----§4(§cBrandweer§4)§m----");
    }

    public void sendHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_RED + "§m----§4(§cBrandweer§4)§m----");
        sender.sendMessage(ChatColor.RED + "- /bw help - Verstuurt deze hulppagina naar jou toe. -");
        sender.sendMessage(ChatColor.RED + "- /bw melding - Stuurt de brandweer jouw coördinaten met een noodoproep. -");
        sender.sendMessage(ChatColor.RED + "- /bw slang - Krijg de brandweerslang -");
        sender.sendMessage(ChatColor.RED + "- /bw permissies - Verstuurt alle permissies naar jou toe. -");
        sender.sendMessage(ChatColor.RED + " ");
        sender.sendMessage(ChatColor.DARK_RED + "§4MTBrandweer §cplugin §cgemaakt door §4KaasPlakje§c.");
        sender.sendMessage(ChatColor.DARK_RED + "§oVersie " + getDescription().getVersion());
        sender.sendMessage(ChatColor.DARK_RED + "§m----§4(§cBrandweer§4)§m----");
    }

    public void performMelding(Player x) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (x.getUniqueId().equals(x.getUniqueId()) || p.hasPermission("brandweer.melding.krijg")) {
                stuurMeldingBericht(p, x);
            } else if (!(p.hasPermission("brandweer.melding.krijg")));
            return;
        }
    }

    private void stuurMeldingBericht(Player p, Player melding) {
        p.sendMessage(ChatColor.DARK_RED + "§m----§4(§cBrandweer Oproep§4)§m----");
        p.sendMessage(ChatColor.RED + "Stad: " + melding.getLocation().getWorld().getName());
        p.sendMessage(ChatColor.RED + "Speler: " + melding.getName());
        p.sendMessage(ChatColor.DARK_RED + "§m----§4(§cCoördinaten§4)§m----");
        p.sendMessage(ChatColor.RED + "X: " + melding.getLocation().getX());
        p.sendMessage(ChatColor.RED + "Y: " + melding.getLocation().getY());
        p.sendMessage(ChatColor.RED + "Z: " + melding.getLocation().getZ());
    }


    public void addSnowball(Snowball b) {
        this.s.addSnowball(b);
    }

    public void removeSnowball(Snowball b) {
        this.s.removeSnowball(b);
    }

    public String format(String text) {
        return text.replaceAll("&", "§");
    }
}
