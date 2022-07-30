package games.bnogocarft.bnogorpg.RecipeBook

import games.bnogocarft.bnogorpg.Utils.*
import org.bukkit.inventory.*

data class RecipePage(val recipe: Recipe) {
    private val craftingLines = arrayOf(10, 11, 12, 19, 20, 21, 28, 29, 30)
    var pageInventory: Inventory? = null

    init {
        if (recipe is ShapedRecipe) {
            print(recipe.result.itemMeta.displayName)
            val shapeRows = recipe.shape //The default String array of a ShapedRecipe
            val shapeIndividual = ArrayList<Char>()
            val itemStackShape = ArrayList<ItemStack?>()
            val shapeItemStacks = recipe.ingredientMap

            // Converts the 3 strings that represent rows into individual chars stored int shapeIndividual
            for (charRow in shapeRows) {
                print(charRow)
                for (char in charRow.split("")) {
                    shapeIndividual.add(char.first())
                }
            }

            for (recipeLetter in shapeIndividual) {
                itemStackShape.add(shapeItemStacks[recipeLetter])
            }

            // Creates the Inventory

            val gui = GUIFactory.createInventory("Recipe for ${recipe.result.itemMeta.displayName}", 54)
            val backgroundz = ArrayList<BackgroundItem>()
            for (index in 0..53) {
                backgroundz.add(BackgroundItem(StandardBackground, index))
            }
            val backgroundLayer = GUILayer(ArrayList(), backgroundz)

            val buttons = ArrayList<GUIButton>()
            for ((indexofItem, line) in craftingLines.withIndex()) {
                buttons.add(GUIButton(itemStackShape[indexofItem]!!, line, ::openRecipePageFor))
            }

            val buttonLayer = GUILayer(buttons, ArrayList())

            gui.layers.add(backgroundLayer)
            gui.layers.add(buttonLayer)

            pageInventory = GUIFactory.produceInventory(gui)
        }

        if (recipe is ShapelessRecipe) {
            val gui = GUIFactory.createInventory("Recipe for ${recipe.result.itemMeta.displayName}", 54)
            val backgroundz = ArrayList<BackgroundItem>()
            for (index in 0..53) {
                backgroundz.add(BackgroundItem(StandardBackground, index))
            }
            val backgroundLayer = GUILayer(ArrayList(), backgroundz)

            val buttons = ArrayList<GUIButton>()
            for ((indexOfSlot, item) in recipe.ingredientList.withIndex()) {
               buttons.add(GUIButton(item, craftingLines[indexOfSlot], ::openRecipePageFor))
            }

            val buttonLayer = GUILayer(buttons, ArrayList())

            gui.layers.add(backgroundLayer)
            gui.layers.add(buttonLayer)

            pageInventory = GUIFactory.produceInventory(gui)
        }
    }
}