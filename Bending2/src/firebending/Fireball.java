package firebending;

import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.server.EntityFireball;
import net.minecraft.server.EntityLiving;

import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Fireball {

	public static ConcurrentHashMap<EntityFireball, Long> fireballs = new ConcurrentHashMap<EntityFireball, Long>();
	public static final long duration = 5000;

	private static ConcurrentHashMap<Player, Long> timers = new ConcurrentHashMap<Player, Long>();
	static final long soonesttime = 1000;
	private static final double speedfactor = 0.3;

	public Fireball(Player player) {
		if (player.getEyeLocation().getBlock().isLiquid())
			return;
		if (timers.containsKey(player)) {
			if (System.currentTimeMillis() < timers.get(player) + soonesttime) {
				return;
			}
		}

		Location playerLoc = player.getEyeLocation();
		Vector direction = player.getEyeLocation().getDirection().clone()
				.normalize().multiply(speedfactor);
		double dx = direction.getX();
		double dy = direction.getY();
		double dz = direction.getZ();

		CraftPlayer craftPlayer = (CraftPlayer) player;
		EntityLiving playerEntity = craftPlayer.getHandle();
		EntityFireball fireball = new EntityFireball(
				((CraftWorld) player.getWorld()).getHandle(), playerEntity, dx,
				dy, dz);

		double distance = 2;
		Vector aim = direction.clone();
		fireball.locX = playerLoc.getX() + aim.getX() * distance;
		fireball.locY = playerLoc.getY() + aim.getY() * distance;
		fireball.locZ = playerLoc.getZ() + aim.getZ() * distance;

		fireball.dirX = dx;
		fireball.dirY = dy;
		fireball.dirZ = dz;

		((CraftWorld) player.getWorld()).getHandle().addEntity(fireball);
		fireballs.put(fireball, System.currentTimeMillis());
		timers.put(player, System.currentTimeMillis());
		// ((Entity) fireball).setVelocity(aim);
		// fireball.setDirection(dx, dy, dz);
		// fireball.
	}

	public static void removeAllFireballs() {
		for (EntityFireball fireball : fireballs.keySet()) {
			fireball.die();
		}
	}
}