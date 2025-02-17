package games.bnogocarft.bnogorpg.Listeners

import games.bnogocarft.bnogorpg.Main
import games.bnogocarft.bnogorpg.Planes.PlaneEntity
import games.bnogocarft.bnogorpg.Planes.planeEntitites
import games.bnogocarft.bnogorpg.Utils.BItemStack.BItems.BArmor
import games.bnogocarft.bnogorpg.Utils.BItemStack.BItems.BItemUtils
import games.bnogocarft.bnogorpg.Utils.BItemStack.BItems.BWeapon
import games.bnogocarft.bnogorpg.Utils.BPlayer.BPlayer
import games.bnogocarft.bnogorpg.Utils.BPlayer.BPlayers
import games.bnogocarft.bnogorpg.Utils.BPlayer.StatRegenTimer
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinEvent : Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun playerJoinEvent(e: PlayerJoinEvent) {
        val bPlayer = BPlayer(e.player)
        val playerBar = bPlayer.bar
        playerBar.text =
            Main.serverIp
        playerBar.health = 100
        playerBar.display()
        BPlayers[e.player] = bPlayer
        Bukkit.getServer().consoleSender.sendMessage("${e.player.displayName}'s data files have been instanced and mapped at")
        Bukkit.getServer().consoleSender.sendMessage(bPlayer.playerFile.path)
        for (item in e.player.inventory.contents) {
            if (item != null) {
                if (BItemUtils.getBType(item).equals("weapon")) {
                    BItemUtils.addBWeapon(item, BWeapon(item))
                }

                if (BItemUtils.getBType(item).equals("armor")) {
                    BItemUtils.addBArmor(item, BArmor(item))
                }

                if (item.itemMeta.displayName == "Plane Key") {
                    planeEntitites[item] = PlaneEntity(item)
                }
            }
        }
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, StatRegenTimer(bPlayer), 0, 20)
    }
}