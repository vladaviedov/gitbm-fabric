package vladaviedov.gitbm

import net.fabricmc.api.ModInitializer

object Main : ModInitializer {
	override fun onInitialize() {
		ItemList.registerItems()
	}
}
