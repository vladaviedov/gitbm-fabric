package vladaviedov.gitbm.item

import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnReason
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import vladaviedov.gitbm.Components

class VanillaBucketOf(props: Item.Settings, private val entType: EntityType<in Nothing>) : BucketOf(props) {
	override fun placeEntity(
		world: ServerWorld,
		item: ItemStack,
		pos: BlockPos,
		player: PlayerEntity,
	) {
		if (item.get(Components.EntityDataComponent)?.copyNbt() != null) {
			super.placeEntity(world, item, pos, player)
		} else {
			entType.spawnFromItemStack(world, item, player, pos, SpawnReason.BUCKET, false, false)
		}
	}
}
