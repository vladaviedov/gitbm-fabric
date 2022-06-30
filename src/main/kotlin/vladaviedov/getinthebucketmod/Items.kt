package vladaviedov.getinthebucketmod

import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.registry.Registry
import net.minecraft.util.Identifier
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import vladaviedov.getinthebucketmod.item.BucketOf
import vladaviedov.getinthebucketmod.item.VanillaBucketOf

object Items {

	private val itemLookup = HashMap<EntityType<in Nothing>, Item>();
	public val generic = BucketOf(FabricItemSettings()
		.maxCount(1));

	public fun registerItems() {
		// Bucket of Entity
		Registry.register(Registry.ITEM, Identifier(Constants.MOD_ID, "bucket_of_entity"), generic);

		// Passive mobs
		makeItem(EntityType.ALLAY, "bucket_of_allay");
		makeItem(EntityType.AXOLOTL, "bucket_of_axolotl");
		makeItem(EntityType.BAT, "bucket_of_bat");
		makeItem(EntityType.CAT, "bucket_of_cat");
		makeItem(EntityType.COD, "bucket_of_cod");
		makeItem(EntityType.CHICKEN, "bucket_of_chicken");
		makeItem(EntityType.COW, "bucket_of_cow");
		makeItem(EntityType.DONKEY, "bucket_of_donkey");
		makeItem(EntityType.FOX, "bucket_of_fox");
		makeItem(EntityType.FROG, "bucket_of_frog");
		makeItem(EntityType.GIANT, "bucket_of_giant");
		makeItem(EntityType.GLOW_SQUID, "bucket_of_glow_squid");
		makeItem(EntityType.HORSE, "bucket_of_horse");
		makeItem(EntityType.MOOSHROOM, "bucket_of_mooshroom");
		makeItem(EntityType.MULE, "bucket_of_mule");
		makeItem(EntityType.OCELOT, "bucket_of_ocelot");
		makeItem(EntityType.PANDA, "bucket_of_panda");
		makeItem(EntityType.PARROT, "bucket_of_parrot");
		makeItem(EntityType.PIG, "bucket_of_pig");
		makeItem(EntityType.RABBIT, "bucket_of_rabbit");
		makeItem(EntityType.SALMON, "bucket_of_salmon");
		makeItem(EntityType.SHEEP, "bucket_of_sheep");
		makeItem(EntityType.SKELETON_HORSE, "bucket_of_skeleton_horse");
		makeItem(EntityType.SNOW_GOLEM, "bucket_of_snow_golem");
		makeItem(EntityType.SQUID, "bucket_of_squid");
		makeItem(EntityType.STRIDER, "bucket_of_strider");
		makeItem(EntityType.TADPOLE, "bucket_of_tadpole");
		makeItem(EntityType.TURTLE, "bucket_of_turtle");
		makeItem(EntityType.TROPICAL_FISH, "bucket_of_tropical_fish");
		makeItem(EntityType.VILLAGER, "bucket_of_villager");
		makeItem(EntityType.ZOMBIE_HORSE, "bucket_of_zombie_horse");

		// Hostile mobs
		makeItem(EntityType.BLAZE, "bucket_of_blaze");
		makeItem(EntityType.CREEPER, "bucket_of_creeper");
		makeItem(EntityType.DROWNED, "bucket_of_drowned");
		makeItem(EntityType.ELDER_GUARDIAN, "bucket_of_elder_guardian");
		makeItem(EntityType.ENDERMITE, "bucket_of_endermite");
		makeItem(EntityType.EVOKER, "bucket_of_evoker");
		makeItem(EntityType.GHAST, "bucket_of_ghast");
		makeItem(EntityType.GUARDIAN, "bucket_of_guardian");
		makeItem(EntityType.HOGLIN, "bucket_of_hoglin");
		makeItem(EntityType.HUSK, "bucket_of_husk");
		makeItem(EntityType.ILLUSIONER, "bucket_of_illusioner");
		makeItem(EntityType.MAGMA_CUBE, "bucket_of_magma_cube");
		makeItem(EntityType.PHANTOM, "bucket_of_phantom");
		makeItem(EntityType.PIGLIN, "bucket_of_piglin");
		makeItem(EntityType.PIGLIN_BRUTE, "bucket_of_piglin_brute");
		makeItem(EntityType.PILLAGER, "bucket_of_pillager");
		makeItem(EntityType.RAVAGER, "bucket_of_ravager");
		makeItem(EntityType.SHULKER, "bucket_of_shulker");
		makeItem(EntityType.SILVERFISH, "bucket_of_silverfish");
		makeItem(EntityType.SKELETON, "bucket_of_skeleton");
		makeItem(EntityType.SLIME, "bucket_of_slime");
		makeItem(EntityType.STRAY, "bucket_of_stray");
		makeItem(EntityType.VEX, "bucket_of_vex");
		makeItem(EntityType.VINDICATOR, "bucket_of_vindicator");
		makeItem(EntityType.WARDEN, "bucket_of_warden");
		makeItem(EntityType.WITCH, "bucket_of_witch");
		makeItem(EntityType.WITHER_SKELETON, "bucket_of_wither_skeleton");
		makeItem(EntityType.ZOGLIN, "bucket_of_zoglin");
		makeItem(EntityType.ZOMBIE, "bucket_of_zombie");
		makeItem(EntityType.ZOMBIE_VILLAGER, "bucket_of_zombie_villager");

		// Neutral mobs
		makeItem(EntityType.BEE, "bucket_of_bee");
		makeItem(EntityType.CAVE_SPIDER, "bucket_of_cave_spider");
		makeItem(EntityType.DOLPHIN, "bucket_of_dolphin");
		makeItem(EntityType.ENDERMAN, "bucket_of_enderman");
		makeItem(EntityType.GOAT, "bucket_of_goat");
		makeItem(EntityType.IRON_GOLEM, "bucket_of_iron_golem");
		makeItem(EntityType.LLAMA, "bucket_of_llama");
		makeItem(EntityType.POLAR_BEAR, "bucket_of_polar_bear");
		makeItem(EntityType.PUFFERFISH, "bucket_of_pufferfish");
		makeItem(EntityType.SPIDER, "bucket_of_spider");
		makeItem(EntityType.TRADER_LLAMA, "bucket_of_trader_llama");
		makeItem(EntityType.WANDERING_TRADER, "bucket_of_wandering_trader");
		makeItem(EntityType.WOLF, "bucket_of_wolf");
		makeItem(EntityType.ZOMBIFIED_PIGLIN, "bucket_of_zombified_piglin");

		// Bosses
		makeItem(EntityType.WITHER, "bucket_of_wither");
		makeItem(EntityType.ENDER_DRAGON, "bucket_of_ender_dragon");
	}

	public fun lookup(type: EntityType<in Nothing>): Item {
		val item: Item? = itemLookup.get(type);
		if (item == null)
		return generic;

		return item;
	}

	private fun makeItem(type: EntityType<in Nothing>, name: String) {
		val item = VanillaBucketOf(FabricItemSettings()
			.group(ItemGroup.MISC)
			.maxCount(1), type);
		Registry.register(Registry.ITEM, Identifier(Constants.MOD_ID, name), item);
		itemLookup.put(type, item);
	}

}
