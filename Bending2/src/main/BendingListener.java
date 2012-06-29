package main;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.player.PlayerVelocityEvent;

import tools.Abilities;
import tools.AvatarState;
import tools.BendingType;
import tools.Tools;
import waterbending.Freeze;
import waterbending.Melt;
import waterbending.Plantbending;
import waterbending.WalkOnWater;
import waterbending.WaterManipulation;
import waterbending.WaterPassive;
import waterbending.WaterWall;
import waterbending.Wave;
import airbending.AirBlast;
import airbending.AirBubble;
import airbending.AirShield;
import airbending.AirSuction;
import airbending.AirSwipe;
import airbending.Speed;
import airbending.Tornado;
import earthbending.Catapult;
import earthbending.CompactColumn;
import earthbending.EarthBlast;
import earthbending.EarthColumn;
import earthbending.EarthGrab;
import earthbending.EarthPassive;
import earthbending.EarthTunnel;
import earthbending.EarthWall;
import earthbending.PatchTheEarth;
import firebending.ArcOfFire;
import firebending.Extinguish;
import firebending.FireJet;
import firebending.FireStream;
import firebending.Fireball;
import firebending.HeatMelt;
import firebending.RingOfFire;

public class BendingListener implements Listener {

	public BendingListener() {

	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		String append = "";
		if (player.isOp()) {
			append = "[Avatar] ";
		} else if (Tools.isBender(player, BendingType.Air)) {
			append = "[Airbender] ";
		} else if (Tools.isBender(player, BendingType.Earth)) {
			append = "[Earthbender] ";
		} else if (Tools.isBender(player, BendingType.Fire)) {
			append = "[Firebender] ";
		} else if (Tools.isBender(player, BendingType.Water)) {
			append = "[Waterbender] ";
		} else {
			player.sendMessage("Using '/bending choose <element>' to get started!");
		}
		player.setDisplayName(append + player.getName());
	}

	@EventHandler
	public void onPlayerChangeVelocity(PlayerVelocityEvent event) {
		Player player = event.getPlayer();
		if (Tools.isBender(player, BendingType.Water)
				&& Tools.canBendPassive(player, BendingType.Water)) {

			event.setVelocity(WaterPassive.handle(player, event.getVelocity()));
		}
	}

	@EventHandler
	public void onPlayerChat(PlayerChatEvent event) {
		Player player = event.getPlayer();
		ChatColor color = ChatColor.WHITE;

		if (player.isOp()) {
			color = ChatColor.DARK_PURPLE;
		} else if (Tools.isBender(player, BendingType.Air)) {
			color = ChatColor.GRAY;
		} else if (Tools.isBender(player, BendingType.Earth)) {
			color = ChatColor.GREEN;
		} else if (Tools.isBender(player, BendingType.Fire)) {
			color = ChatColor.RED;
		} else if (Tools.isBender(player, BendingType.Water)) {
			color = ChatColor.AQUA;
		}
		event.setFormat("<" + color + player.getDisplayName() + ChatColor.WHITE
				+ "> " + event.getMessage());
	}

	// event.setMessage(append + event.getMessage());
	// }

	@EventHandler
	public void onPlayerSwing(PlayerAnimationEvent event) {

		Player player = event.getPlayer();

		if (Tools.canBend(player, Tools.getBendingAbility(player))) {

			if (Tools.getBendingAbility(player) == Abilities.AirBlast) {
				new AirBlast(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.AirSuction) {
				new AirSuction(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.AirSwipe) {
				new AirSwipe(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.Catapult) {
				new Catapult(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.PatchTheEarth) {
				new PatchTheEarth(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.EarthColumn) {
				new EarthColumn(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.CompactColumn) {
				new CompactColumn(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.EarthGrab) {
				new EarthGrab(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.EarthWall) {
				new EarthWall(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.EarthBlast) {
				EarthBlast.throwEarth(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.Fireball) {
				new Fireball(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.Extinguish) {
				new Extinguish(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.FireStream) {
				new FireStream(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.ArcOfFire) {
				new ArcOfFire(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.RingOfFire) {
				new RingOfFire(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.HeatMelt) {
				new HeatMelt(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.WaterManipulation) {
				WaterManipulation.moveWater(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.Freeze) {
				new Freeze(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.Melt) {
				new Melt(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.WaterWall) {
				new WaterWall(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.Wave) {
				Wave.launch(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.AvatarState) {
				new AvatarState(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.Plantbending) {
				new Plantbending(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.FireJet) {
				new FireJet(player);
			}
		}
	}

	@EventHandler
	public void onPlayerSneak(PlayerToggleSneakEvent event) {

		Player player = event.getPlayer();

		if (!player.isSneaking()
				&& Tools.canBend(player, Tools.getBendingAbility(player))) {

			if (Tools.getBendingAbility(player) == Abilities.AirShield) {
				new AirShield(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.AirBlast) {
				new AirBlast(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.AirSuction) {
				new AirSuction(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.Tornado) {
				new Tornado(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.EarthBlast) {
				new EarthBlast(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.WaterManipulation) {
				new WaterManipulation(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.EarthTunnel) {
				new EarthTunnel(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.WaterWall) {
				WaterWall.form(player);
			}

			if (Tools.getBendingAbility(player) == Abilities.Wave) {
				new Wave(player);
			}

		}

	}

	@EventHandler
	public void onPlayerSprint(PlayerToggleSprintEvent event) {
		Player player = event.getPlayer();

		if (!player.isSprinting() && Tools.isBender(player, BendingType.Air)
				&& Tools.canBendPassive(player, BendingType.Air)) {
			new Speed(player);
		}
	}

	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			// Tools.verbose(event.getCause());
			Player player = (Player) event.getEntity();

			if (Tools.isBender(player, BendingType.Air)
					&& event.getCause() == DamageCause.FALL
					&& Tools.canBendPassive(player, BendingType.Air)) {
				event.setCancelled(true);
			} else if (Tools.isBender(player, BendingType.Earth)
					&& event.getCause() == DamageCause.FALL
					&& Tools.canBendPassive(player, BendingType.Earth)) {
				event.setCancelled(EarthPassive.softenLanding(player));
			}

			if (Tools.isBender(player, BendingType.Fire)
					&& (event.getCause() == DamageCause.FIRE || event
							.getCause() == DamageCause.FIRE_TICK)) {
				event.setCancelled(!Extinguish.canBurn(player));
			}
		}
	}

	// @EventHandler
	// public void onEntityDamage(EntityDamageByBlockEvent event) {
	// Tools.verbose(event.getCause());
	// if (event.getEntity() instanceof LivingEntity) {
	// if (event.getCause() == DamageCause.FIRE
	// && FireStream.ignitedblocks.contains(event.getDamager())) {
	// event.setDamage(0);
	// Tools.damageEntity(
	// FireStream.ignitedblocks.get(event.getDamager()),
	// event.getEntity(), FireStream.firedamage);
	// }
	//
	// if (event.getCause() == DamageCause.FIRE_TICK
	// && FireStream.ignitedblocks.contains(event.getEntity())) {
	// event.setDamage(0);
	// Tools.damageEntity(
	// FireStream.ignitedblocks.get(event.getDamager()),
	// event.getEntity(), FireStream.tickdamage);
	// }
	// }
	//
	// }
	// @EventHandler
	// public void onEntityCombust(EntityCombustByBlockEvent event) {
	// if (FireStream.ignitedblocks.contains(event.getCombuster())) {
	// FireStream.ignitedentities.put((LivingEntity) event.getEntity(),
	// FireStream.ignitedblocks.get(event.getCombuster()));
	// }
	// }

	@EventHandler
	public void onBlockFlowTo(BlockFromToEvent event) {
		Block toblock = event.getToBlock();
		Block fromblock = event.getBlock();
		event.setCancelled(!AirBubble.canFlowTo(toblock));
		if (!event.isCancelled()) {
			event.setCancelled(!WaterManipulation.canFlowFromTo(fromblock,
					toblock));
		}
	}

	@EventHandler
	public void onBlockMeltEvent(BlockFadeEvent event) {
		Block block = event.getBlock();
		event.setCancelled(!WalkOnWater.canThaw(block));
		if (!event.isCancelled()) {
			event.setCancelled(!WaterManipulation.canPhysicsChange(block));
		}
	}

	@EventHandler
	public void onBlockPhysics(BlockPhysicsEvent event) {
		Block block = event.getBlock();
		event.setCancelled(!WaterManipulation.canPhysicsChange(block));

	}

}