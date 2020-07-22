package me.KaasPlakje.MinetopiaBrandweer;

import me.KaasPlakje.MinetopiaBrandweer.StickEvent;
import me.KaasPlakje.MinetopiaBrandweer.Utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class StickEvent
  implements Listener {
	
  private Main plugin;
  
  public StickEvent(Main plugin) {
		this.plugin = plugin;
  }

  @EventHandler
  public void onStickEvent(PlayerInteractEvent e) {
    Player p = e.getPlayer();

    
    if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
      
      if (e.getClickedBlock() != null && isOnFire(e.getClickedBlock())) {
        if (e.getPlayer().getInventory().getItemInMainHand() != null && e.getPlayer().getInventory().getItemInMainHand().getType() == Material.STICK && ChatColor.stripColor(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName()).toLowerCase().contains("brandweer")) {
          
          p.sendMessage(Utils.chat(plugin.getConfig().getString("meerdere_geblust")));
          e.setCancelled(true);
          p.getLocation().getWorld().playEffect(p.getLocation(), Effect.valueOf("particle"), 5);
          blusVuur(e.getClickedBlock());
          
          return;
        } 
      if (!p.hasPermission("brandweer.lid")) {
        p.sendMessage(Utils.chat(plugin.getConfig().getString("vuur_heet")));
        p.damage(1.0D);
        p.sendMessage(Utils.chat(plugin.getConfig().getString("bel_brandweer")));
        e.setCancelled(true);
        return;
        } else if (p.hasPermission("brandweer.lid"))
        p.sendMessage(Utils.chat(plugin.getConfig().getString("vuur_heet")));
        p.damage(1.0D);
        p.sendMessage(Utils.chat(plugin.getConfig().getString("gebruik_slang")));
        e.setCancelled(true);
        	return;
      		}  
        return;
      	  }

    if (e.getAction() == Action.LEFT_CLICK_AIR) {
      if (e.getPlayer().getInventory().getItemInMainHand() != null && e.getPlayer().getInventory().getItemInMainHand().getType() == Material.STICK && ChatColor.stripColor(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName()).toLowerCase().contains("brandweer")) {
        
        Snowball sb = (Snowball)p.launchProjectile(Snowball.class);
        sb.setCustomName("bwwater");
        sb.getLocation().getWorld().playEffect(sb.getLocation(), Effect.valueOf(plugin.getConfig().getString("particle")), 5);
        this.plugin.addSnowball(sb);
        return;
      } 
      return;
    		}
    	  }


  
  @EventHandler
  public void onSnowballCrash(ProjectileHitEvent e) {
    if (e.getEntity() instanceof Snowball) {
      Snowball sb = (Snowball)e.getEntity();
      if (sb.getCustomName().equalsIgnoreCase("bwwater") && 
        isOnFire(sb.getLocation().getBlock())) {
        blusVuur(sb.getLocation().getBlock());
        if (sb.getShooter() != null && sb.getShooter() instanceof Player) {
          Player p = (Player)sb.getShooter();
          p.sendMessage(Utils.chat(plugin.getConfig().getString("enkele_geblust")));
        } 
      } 
    } 
  } public boolean isOnFire(Block b) {
    byte b1;
    int i;
    BlockFace[] arrayOfBlockFace;
    for (i = (arrayOfBlockFace = BlockFace.values()).length, b1 = 0; b1 < i; ) { BlockFace face = arrayOfBlockFace[b1];
      
      Block rel = b.getRelative(face);
      if (rel.getType() == Material.FIRE)
      {
        return true; } 
      b1++; }
    
    return false;
  }
  public void blusVuur(Block b) {
    Location loc = b.getLocation();
    loc.setY(loc.getY() + 1.0D);
    playParticles(loc); byte b1; int i; BlockFace[] arrayOfBlockFace;
    for (i = (arrayOfBlockFace = BlockFace.values()).length, b1 = 0; b1 < i; ) { BlockFace bf = arrayOfBlockFace[b1];
      Block rel = b.getRelative(bf);
      if (rel.getType() == Material.FIRE)
      {
        rel.setType(Material.AIR); } 
      b1++; }
  
  }
  
  public void playParticles(Location x) {
	    x.getWorld().playEffect(x, Effect.valueOf(plugin.getConfig().getString("particle")), 0, 50);
	    
	    for (int i = 0; i < 5; i++) {
	      x.setY(x.getY() - 0.25D);
	      x.getWorld().playEffect(x, Effect.valueOf(plugin.getConfig().getString("particle")), 0, 50);
	      x.setY(x.getY() + 0.5D);
	      x.getWorld().playEffect(x, Effect.valueOf(plugin.getConfig().getString("particle")), 0, 50);
	      x.setY(x.getY() - 0.25D);
	   
	      x.setZ(x.getZ() - 0.25D);
	      x.getWorld().playEffect(x, Effect.valueOf(plugin.getConfig().getString("particle")), 0, 50);
	      x.setZ(x.getZ() + 0.5D);
	      x.getWorld().playEffect(x, Effect.valueOf(plugin.getConfig().getString("particle")), 0, 50);
	      x.setZ(x.getZ() - 0.25D);
	      
	      x.setX(x.getX() - 0.25D);
	      x.getWorld().playEffect(x, Effect.valueOf(plugin.getConfig().getString("particle")), 0, 50);
	      x.setX(x.getX() + 0.5D);
	      x.getWorld().playEffect(x, Effect.valueOf(plugin.getConfig().getString("particle")), 0, 50);
	      x.setX(x.getX() - 0.25D);
	    } 
	  }
	}