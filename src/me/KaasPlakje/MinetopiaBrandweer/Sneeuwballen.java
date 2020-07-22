package me.KaasPlakje.MinetopiaBrandweer;

import java.util.ArrayList;
import java.util.List;
import me.KaasPlakje.MinetopiaBrandweer.Sneeuwballen;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Snowball;
import org.bukkit.scheduler.BukkitRunnable;

public class Sneeuwballen
  extends BukkitRunnable {
	
	private Main plugin;
	
	  public Sneeuwballen(Main plugin) {
			this.plugin = plugin;
	  }
	
  private List<Snowball> entitiesEffects = new ArrayList<>();
  
  public void run() {
    List<Snowball> list = this.entitiesEffects;
    if (list.size() > 0) {
      for (int i = 0; i < list.size(); i++) {
        Snowball sb = list.get(i);
        if (sb != null) {
          playParticles(sb);
          if (sb.isDead()) {
            removeSnowball(sb);
            i--;
          } 
        } 
      } 
    }
  }

  
  public List<Snowball> getBalls() { return this.entitiesEffects; }


  
  public void addSnowball(Snowball b) { getBalls().add(b); }

  
  public void removeSnowball(Snowball b) {
    if (getBalls().contains(b))
      getBalls().remove(b); 
  }
  
  public void playParticles(Snowball sb) {
    Location x = sb.getLocation();
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
