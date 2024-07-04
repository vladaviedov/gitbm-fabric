package vladaviedov.gitbm

import net.minecraft.component.ComponentType
import net.minecraft.component.type.NbtComponent

object Components {
	public val EntityDataComponent =
		ComponentType.builder<NbtComponent>()
			.codec(NbtComponent.CODEC)
			.packetCodec(NbtComponent.PACKET_CODEC)
			.build()
}
