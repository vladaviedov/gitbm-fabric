package vladaviedov.getinthebucketmod

import net.fabricmc.api.ModInitializer

object Main : ModInitializer {

	override fun onInitialize() {
		Items.registerItems()
	}
}
