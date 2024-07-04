package vladaviedov.gitbm.item

import net.minecraft.entity.EntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundEvents
import net.minecraft.stat.Stats
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.RaycastContext
import net.minecraft.world.World
import vladaviedov.gitbm.Components
import java.util.UUID

open class BucketOf(props: Item.Settings) : Item(props) {
	override fun use(
		world: World,
		player: PlayerEntity,
		hand: Hand,
	): TypedActionResult<ItemStack> {
		val heldItem = player.getStackInHand(hand)
		val trace = raycast(world, player, RaycastContext.FluidHandling.ANY)
		var targetPos = trace.blockPos

		if (!player.canModifyAt(world, targetPos)) {
			return TypedActionResult(ActionResult.PASS, heldItem)
		}
		if (world !is ServerWorld) {
			return TypedActionResult(ActionResult.SUCCESS, heldItem)
		}

		player.incrementStat(Stats.USED.getOrCreateStat(this))
		player.playSound(SoundEvents.ITEM_BUCKET_EMPTY_FISH, 1.0f, 1.0f)

		// Fix position
		val blockState = world.getBlockState(targetPos)
		if (!blockState.getCollisionShape(world, targetPos).isEmpty) {
			targetPos = targetPos.add(trace.side.vector)
		}

		placeEntity(world, heldItem, targetPos, player)
		if (player.isCreative) {
			return TypedActionResult(ActionResult.SUCCESS, heldItem)
		}

		val bucket = ItemStack(Items.BUCKET)
		heldItem.decrement(1)
		if (heldItem.isEmpty) {
			return TypedActionResult(ActionResult.SUCCESS, bucket)
		}

		if (!player.inventory.insertStack(bucket)) {
			player.dropItem(bucket, false)
		}

		return TypedActionResult(ActionResult.SUCCESS, heldItem)
	}

	protected open fun placeEntity(
		world: ServerWorld,
		item: ItemStack,
		pos: BlockPos,
		player: PlayerEntity,
	) {
		val entityData: NbtCompound? = item.get(Components.EntityDataComponent)?.copyNbt()
		if (entityData == null) {
			return
		}

		EntityType.loadEntityWithPassengers(entityData, world) { e ->
			e.setPos(pos.x + 0.5, pos.y + 0.0, pos.z + 0.5)
			while (!world.spawnNewEntityAndPassengers(e)) {
				e.uuid = UUID.randomUUID()
			}
			e
		}
	}
}
