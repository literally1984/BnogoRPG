package games.bnogocarft.bnogorpg.Utils.BItemStack.BItems

import games.bnogocarft.bnogorpg.Utils.BItemStack.Rarity.RarityUtils
import games.bnogocarft.bnogorpg.Utils.EnchantUtils.BEnchantment
import org.bukkit.ChatColor
import org.bukkit.inventory.ItemStack

open class BItem(item: ItemStack) {
    val Enchants = ArrayList<BEnchantment>()
    var bMaterial: BMaterial
    /*var lastUpdate: Update =
        when (decode(item.itemMeta.lore[item.itemMeta.lore.size - 1].split(" ")[1])) {
            "002" -> Update.zerozerotwo
            else -> Update.zerozerotwo
        }*/
    var rarity = RarityUtils.getRarity(item.itemMeta.lore[item.itemMeta.lore.size - 1].split(" ")[0])

    private val lore = item.itemMeta.lore

    init {
        for (clore in lore) {
            if (clore.contains("${ChatColor.BLUE}Enchantments:")) { // Gets the line that marks the start of enchantments
                var index = item.itemMeta.lore.indexOf(clore) + 1 // Gets the index of the line after the marker above
                while (!(lore[index].equals(""))) { // Loops through the enchants until it finds "" which is the seperator
                    index++
                    val enchantsInLine = clore.split(", ")
                    for (enchant in enchantsInLine) {
                        //Enchants.add(EnchantUtils.parseEnchant(enchant))
                    }
                }
                continue
            }
        }

        bMaterial = if (this !is BGear) {
            BMaterial.valueOf(item.itemMeta.displayName.replace(" ", "_").uppercase())
        } else {
            val name = item.itemMeta.displayName
            try {
                BMaterial.valueOf(name.replace(" ", "_").uppercase())
            } catch (e: IllegalArgumentException) {
                val newNmae = name.split(" ").toMutableList()
                newNmae.removeAt(0)
                val newName = newNmae.joinToString("_").uppercase()
                BMaterial.valueOf(newName)
            }
        }
    }
}