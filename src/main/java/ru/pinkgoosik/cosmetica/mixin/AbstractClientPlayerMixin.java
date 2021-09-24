package ru.pinkgoosik.cosmetica.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.pinkgoosik.cosmetica.client.CosmeticaClient;
import ru.pinkgoosik.cosmetica.client.PlayerEntries;

import java.util.UUID;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerMixin extends PlayerEntity {

	public AbstractClientPlayerMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
		super(world, pos, yaw, profile);
	}

	@Inject(method = "getCapeTexture", at = @At("HEAD"), cancellable = true)
	void getCapeTexture(CallbackInfoReturnable<Identifier> cir) {
		PlayerEntries playerEntries = CosmeticaClient.getPlayerEntries();
		String capeId = "cosmetica:textures/cloak/type.png";

		if (CosmeticaClient.getPlayerEntries().entries() != null) {
			for (PlayerEntries.Entry.Entries entries : CosmeticaClient.getPlayerEntries().entries().entries) {
				UUID actualUUID = UUID.fromString(entries.playerInformation.uuid);
				if (entries.cloakInformation != null && (this.getUuid().equals(actualUUID) || this.getName().asString().equals(entries.playerInformation.name))) {
					if (entries.cloakInformation.type.equals("per_dimension")) {
						cir.setReturnValue(new Identifier(getPerDimensionCape()));
					} else {
						for (String color : playerEntries.availabilities().cloaks.colors) {
							if (color.equals(entries.cloakInformation.cloakColor)) {
								cir.setReturnValue(new Identifier(capeId.replaceAll("type", entries.cloakInformation.cloakColor)));
							}
						}
					}
				}
			}
		}
	}

	@Unique
	private String getPerDimensionCape() {
		String capeId = "cosmetica:textures/cloak/type.png";
		RegistryKey<World> worldKey = this.world.getRegistryKey();
		if (worldKey.equals(World.OVERWORLD)) capeId = capeId.replaceAll("type", "turtle");
		else if (worldKey.equals(World.NETHER)) capeId = capeId.replaceAll("type", "crimson");
		else if (worldKey.equals(World.END)) capeId = capeId.replaceAll("type", "mystical");
		else capeId = capeId.replaceAll("type", "turtle");
		return capeId;
	}
}
