package vladaviedov.gitbm

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.component.ComponentType
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object Register {
	public fun newItem(
		name: String,
		item: Item,
	) {
		val id = Identifier.of(Constants.MOD_ID, name)
		Registry.register(Registries.ITEM, id, item)
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
			.register({ group -> group.add(item) })
	}

	public fun newComponent(
		name: String,
		component: ComponentType<in Nothing>,
	) {
		val id = Identifier.of(Constants.MOD_ID, name)
		Registry.register(Registries.DATA_COMPONENT_TYPE, id, component)
	}
}
