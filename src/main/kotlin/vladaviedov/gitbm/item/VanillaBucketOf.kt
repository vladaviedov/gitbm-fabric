package vladaviedov.gitbm.item

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnReason
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import vladaviedov.gitbm.Constants

class VanillaBucketOf(props: FabricItemSettings, private val entType: EntityType<in Nothing>) : BucketOf(props) {
	override fun placeEntity(world: ServerWorld, item: ItemStack, pos: BlockPos, player: PlayerEntity) {
		if (item.getSubNbt(Constants.DATA_TAG) != null) {
			super.placeEntity(world, item, pos, player)
		} else {
			entType.spawnFromItemStack(world, item, player, pos, SpawnReason.BUCKET, false, false)
		}
	}
}
