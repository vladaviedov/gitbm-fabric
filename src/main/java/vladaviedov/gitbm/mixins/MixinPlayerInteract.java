package vladaviedov.gitbm.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vladaviedov.gitbm.ItemList;
import vladaviedov.gitbm.Components;

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

	// Neck part name
	private static final String NECK = "neck";

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

		// Handle dragon
		if (target.getType() == EntityType.ENDER_DRAGON) {
			EnderDragonPart part = (EnderDragonPart)  target;

			// Hitting the neck seems to not cause the "ghost" dragon bug
			// This is the best cure I have for now, even though it's still buggy
			if (part.getWorld().isClient && !part.name.equals(NECK)) {
				return;
			}

			EnderDragonEntity dragon = part.owner;
			for (EnderDragonPart p : dragon.getBodyParts()) {
				p.remove(RemovalReason.DISCARDED);
			}
			target = dragon;
		}

		if (!(target instanceof MobEntity)) {
			return;
		}

		// Get item
		Item bucketOf = ItemList.INSTANCE.lookup(target.getType());
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
			itemStack.set(Components.INSTANCE.getEntityDataComponent(), NbtComponent.of(entityData));
		}

		if (item == ItemList.INSTANCE.getGeneric()) {
			itemStack.apply(DataComponentTypes.LORE, LoreComponent.DEFAULT, lore -> lore.with(ent.getName()));
		}

		return itemStack;
	}

}
