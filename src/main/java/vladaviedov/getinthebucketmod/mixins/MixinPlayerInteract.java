package vladaviedov.getinthebucketmod.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vladaviedov.getinthebucketmod.Constants;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerInteract extends LivingEntity {

	@Shadow
	private PlayerInventory inventory;
	@Shadow
	private PlayerAbilities abilities;
	@Shadow
	private ItemEntity dropItem(ItemStack stack, boolean retainOwnership) {
		return null;
	}

	protected MixinPlayerInteract(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(at = @At("HEAD"), method = "interact", cancellable = true)
	public void onInteract(Entity target, Hand hand, CallbackInfoReturnable<ActionResult> res) {
		ItemStack item = getStackInHand(hand);

		// Ignore
		if (!(item.getItem() == Items.BUCKET) || isSneaking() || !target.isAlive()) {
			return;
		}

		if (!(target instanceof MobEntity)) {
			return;
		}

		// Get item
		Item bucketOf = vladaviedov.getinthebucketmod.Items.INSTANCE.lookup(target.getType());
		prepareEnt(target);
		ItemStack itemStack = serializeEntToItem(bucketOf, target);

		target.playSound(SoundEvents.ITEM_BUCKET_FILL_FISH, 1.0f, 1.0f);

		// Check gamemode
		if (!abilities.creativeMode) {
			item.decrement(1);
			if (item.isEmpty()) {
				setStackInHand(hand, itemStack);
			} else if (!inventory.insertStack(itemStack)) {
				dropItem(itemStack, false);
			}
		}

		// Remove entity
		target.remove(RemovalReason.DISCARDED);
		res.setReturnValue(ActionResult.SUCCESS);
	}

	private void prepareEnt(Entity ent) {
		ent.removeAllPassengers();
		ent.setVelocity(0, 0, 0);
		ent.fallDistance = 0;
	}

	private ItemStack serializeEntToItem(Item item, Entity ent) {
		ItemStack itemStack = new ItemStack(item);
		NbtCompound entityData = new NbtCompound();
		if (ent.saveSelfNbt(entityData)) {
			itemStack.setSubNbt(Constants.DATA_TAG, entityData);
		}
		itemStack.setSubNbt(Constants.UUID_TAG, NbtString.of(ent.getUuidAsString()));

		if (item == vladaviedov.getinthebucketmod.Items.INSTANCE.getGeneric()) {
			NbtCompound display = new NbtCompound();
			NbtList lore = new NbtList();

			NbtString name = NbtString.of("\"" + ent.getName().getString() + "\"");
			lore.add(name);
			display.put("Lore", lore);
			itemStack.setSubNbt("display", display);
		}

		return itemStack;
	}

}
