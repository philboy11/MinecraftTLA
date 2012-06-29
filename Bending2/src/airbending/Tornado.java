package airbending;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import tools.Abilities;
import tools.Tools;

public class Tornado {

	public static ConcurrentHashMap<Integer, Tornado> instances = new ConcurrentHashMap<Integer, Tornado>();

	private static double radius = 10;
	private static double height = 25;
	private static double range = 25;
	private static int numberOfStreams = 7;
	private static double NPCpushfactor = 1;
	private static double PCpushfactor = 1;
	// private static double speed = .75;

	// private static double speedfactor = 1000 * speed
	// * (Bending.time_step / 1000.);
	private static double speedfactor = 1;

	private ConcurrentHashMap<Integer, Integer> angles = new ConcurrentHashMap<Integer, Integer>();
	private Location origin;
	private Player player;

	public Tornado(Player player) {
		this.player = player;
		origin = player.getTargetBlock(null, (int) range).getLocation();
		origin.setY(origin.getY() - 1. / 10. * height);

		int angle = 0;
		for (int i = 0; i <= height; i += (int) height / numberOfStreams) {
			angles.put(i, angle);
			angle += 90;
			if (angle == 360)
				angle = 0;
		}

		instances.put(player.getEntityId(), this);

	}

	public boolean progress() {
		if (!Tools.canBend(player, Abilities.Tornado)
				|| player.getEyeLocation().getBlock().isLiquid()) {
			instances.remove(player.getEntityId());
			return false;
		}
		if ((Tools.getBendingAbility(player) != Abilities.Tornado)
				|| (!player.isSneaking())) {
			instances.remove(player.getEntityId());
			return false;
		}
		rotateTornado();
		return true;
	}

	private void rotateTornado() {
		origin = player.getTargetBlock(null, (int) range).getLocation();
		if (origin.getBlock().getType() != Material.AIR) {
			origin.setY(origin.getY() - 1. / 10. * height);

			for (Entity entity : Tools.getEntitiesAroundPoint(origin, height)) {
				double y = entity.getLocation().getY();
				double factor;
				if (y > origin.getY() && y < origin.getY() + height) {
					factor = (y - origin.getY()) / height;
					Location testloc = new Location(origin.getWorld(),
							origin.getX(), y, origin.getZ());
					if (testloc.distance(entity.getLocation()) < radius
							* factor) {
						double x, z, vx, vz, mag;
						double angle = 100;
						double vy = 0.7 * NPCpushfactor;
						angle = Math.toRadians(angle);

						x = entity.getLocation().getX() - origin.getX();
						z = entity.getLocation().getZ() - origin.getZ();

						mag = Math.sqrt(x * x + z * z);

						vx = (x * Math.cos(angle) - z * Math.sin(angle)) / mag;
						vz = (x * Math.sin(angle) + z * Math.cos(angle)) / mag;

						if (entity instanceof Player) {
							vy = 0.05 * PCpushfactor;
						}

						if (entity.getEntityId() == player.getEntityId()) {
							Vector direction = player.getEyeLocation()
									.getDirection().clone().normalize();
							vx = direction.getX();
							vz = direction.getZ();
							vy = .6;
						}

						Vector velocity = entity.getVelocity();
						velocity.setX(vx);
						velocity.setZ(vz);
						velocity.setY(vy);
						entity.setVelocity(velocity);
						entity.setFallDistance(0);
					}
				}
			}

			for (int i : angles.keySet()) {
				double x, y, z;
				double angle = (double) angles.get(i);
				angle = Math.toRadians(angle);
				double factor;

				y = origin.getY() + (double) i;
				factor = (double) i / height;

				x = origin.getX() + factor * radius * Math.cos(angle);
				z = origin.getZ() + factor * radius * Math.sin(angle);

				Location effect = new Location(origin.getWorld(), x, y, z);
				origin.getWorld().playEffect(effect, Effect.SMOKE, 1);

				angles.put(i, angles.get(i) + 25 * (int) speedfactor);
			}
		}

	}

	public static boolean progress(int ID) {
		return instances.get(ID).progress();
	}

}