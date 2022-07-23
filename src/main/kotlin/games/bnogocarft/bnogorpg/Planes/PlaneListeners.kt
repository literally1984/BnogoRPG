package games.bnogocarft.bnogorpg.Planes

import org.bukkit.Material
import org.bukkit.entity.Minecart
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class PlaneListeners : Listener {

    // Handler for when a plane gets damaged with the EntityDamageEvent
    @EventHandler (priority = EventPriority.HIGHEST)
    fun onPlaneDamage(event: EntityDamageEvent) {
        if (event.entity is Minecart) {
            if (planes.containsKey(event.entity)) {
                event.isCancelled = true
                val keyItem = planes[event.entity]!!
                val planeEntity = planes[event.entity]!!
                planeEntity.stats.currentHealth = planeEntity.stats.currentHealth - event.damage
                if (planeEntity.stats.currentHealth <= 0) {
                    planeEntity.isDestroyed = true
                }
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    fun onPlaneInteract(e: PlayerInteractEntityEvent) {
        if (e.rightClicked is Minecart) {
            if (e.player.isSneaking) {
                if (planes.containsKey(e.rightClicked)) {
                    val plane = planes[e.rightClicked]!!
                    val coal = ItemStack(Material.COAL)

                    when (e.player.itemInHand) {
                        PlaneKeyItem.key -> {
                            plane.collect()
                            e.player.sendMessage("You collected your plane!")
                        }
                        coal -> {
                            plane.stats.currentFuel += 10
                            e.player.sendMessage("You added 10 fuel to your plane!")
                        }
                    }
                }
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    // Hanndlers for plane spawning and starting
    fun onPlaneRightClick(e: PlayerInteractEvent) {
        if (e.player.isInsideVehicle) {
            if (e.action == Action.RIGHT_CLICK_BLOCK || e.action == Action.RIGHT_CLICK_AIR) {
                print("passed containsKey check")
                if (planes.containsKey(e.player.vehicle)) {
                    println("passed checks")
                    val plane = planes[e.player.vehicle]!!
                    print("passed spawn check")
                    if (!plane.isRunning) plane.start(e.player)
                    if (plane.isRunning) {
                        plane.stop()
                        e.player.sendMessage("You stopped your plane!")
                    }
                }
            }
        }

        // For testing purposes
        if (e.action.equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.player.itemInHand == PlaneKeyItem.key) {
                val spawnedPlane = PlaneEntity(e.player.itemInHand)
                val spawned = spawnedPlane.spawn(e.player.location)
                if (spawned) {
                    e.player.sendMessage("You spawned in a plane!")
                } else e.player.sendMessage("You already have a plane spawned in!")
                planes[spawnedPlane.plane] = spawnedPlane
            }
        }
    }
}