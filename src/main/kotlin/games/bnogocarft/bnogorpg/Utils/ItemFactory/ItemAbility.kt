package games.bnogocarft.bnogorpg.Utils.ItemFactory

import games.bnogocarft.bnogorpg.Utils.Abilities.ItemAbility.AbilityTrigger

enum class ItemAbility {
    CactusSet,
    LapisSet,

    QuickDash,
    BloodLust,
    DivineRetribution,
    NONE;

    val CactusDesc = arrayListOf(
        "Upon being hit by an enemy, 30%",
        "of the incoming damage will be",
        "reflected back to the attacker"
    )
    val LapisDesc = arrayListOf(
        "Upon obtaining this setbonus,",
        "the user gains +30% of their",
        "current max mana"
    )
    val QuickDashDesc = arrayListOf(
        "The user of this sword will",
        "instantly teleport 6 blocks",
        "towards the direction they",
        "are facing"
    )

    val DivineDesc = arrayListOf(
        "When an enemy is hit with this",
        "weapon, they will have a 45%",
        "chance to be smited"
    )

    companion object {
        fun init() {
            nameMap[CactusSet] = "Spikey Spikes"
            nameMap[LapisSet] = "Enhanced Magic"
            nameMap[QuickDash] = "Quick Dash"
            nameMap[DivineRetribution] = "Divine Retribution"

            revNameMap["Spikey Spikes"] = CactusSet
            revNameMap["Enhanced Magic"] = LapisSet
            revNameMap["Quick Dash"] = QuickDash
            revNameMap["Divine Retribution"] = DivineRetribution
        }
        val nameMap = HashMap<ItemAbility, String>()
        val revNameMap = HashMap<String, ItemAbility>()
    }

    fun getDescription(): List<String> {
        return when (this) {
            CactusSet -> CactusDesc
            LapisSet -> LapisDesc
            QuickDash -> QuickDashDesc
            DivineRetribution -> DivineDesc
            else -> {
                arrayListOf("")
            }
        }
    }

    fun getType(): AbilityTrigger {
        return when (this) {
            CactusSet -> AbilityTrigger.SET_BONUS
            LapisSet -> AbilityTrigger.SET_BONUS
            QuickDash -> AbilityTrigger.RIGHT_AIR
            DivineRetribution -> AbilityTrigger.ATTACK
            else -> {
                AbilityTrigger.NONE
            }
        }
    }
    fun getTypeString(): String {
        return when (this) {
            CactusSet -> "Set Bonus"
            LapisSet -> "Set Bonus"
            QuickDash -> "Right Click"
            DivineRetribution -> "On-Hit"
            else -> {
                "NONE"
            }
        }
    }
}