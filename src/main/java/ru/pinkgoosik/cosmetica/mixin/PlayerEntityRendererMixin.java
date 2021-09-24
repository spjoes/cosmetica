package ru.pinkgoosik.cosmetica.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.cosmetica.client.CosmeticaClient;
import ru.pinkgoosik.cosmetica.client.PlayerEntries;
import ru.pinkgoosik.cosmetica.client.util.DyeUtils;

import java.util.UUID;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

	public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
		super(ctx, model, shadowRadius);
	}

	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	public void render(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
		if (CosmeticaClient.getPlayerEntries().entries() != null) {
			for (PlayerEntries.Entry.Entries entries : CosmeticaClient.getPlayerEntries().entries().entries) {
				UUID actualUUID = UUID.fromString(entries.playerInformation.uuid);
				if (entries.cloakInformation != null && (abstractClientPlayerEntity.getGameProfile().getId().equals(actualUUID) || abstractClientPlayerEntity.getGameProfile().getName().equals(entries.playerInformation.name))) {
					matrixStack.push();
					for (String type : entries.cloakInformation.type.split("\\|")) {
						if (type.equals("jeb")) {
							float[] color = DyeUtils.createJebColorTransition(abstractClientPlayerEntity, g);

							MinecraftClient minecraftClient = MinecraftClient.getInstance();
							boolean bl = this.isVisible(abstractClientPlayerEntity);
							boolean bl2 = !bl && !abstractClientPlayerEntity.isInvisibleTo(minecraftClient.player);
							boolean bl3 = minecraftClient.hasOutline(abstractClientPlayerEntity);
							RenderLayer renderLayer = this.getRenderLayer(abstractClientPlayerEntity, bl, bl2, bl3);
							VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(renderLayer);
							int r = getOverlay(abstractClientPlayerEntity, this.getAnimationCounter(abstractClientPlayerEntity, g));
							this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, color[0], color[1], color[2], 1.0F);
						}
						if (type.equals("glint")) {
							MinecraftClient minecraftClient = MinecraftClient.getInstance();
							boolean bl = this.isVisible(abstractClientPlayerEntity);
							boolean bl2 = !bl && !abstractClientPlayerEntity.isInvisibleTo(minecraftClient.player);
							boolean bl3 = minecraftClient.hasOutline(abstractClientPlayerEntity);
							RenderLayer renderLayer = this.getRenderLayer(abstractClientPlayerEntity, bl, bl2, bl3);
							VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityGlint());
							int r = getOverlay(abstractClientPlayerEntity, this.getAnimationCounter(abstractClientPlayerEntity, g));
							this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
						}
						if (type.equals("dinnerbone")) {
							MinecraftClient minecraftClient = MinecraftClient.getInstance();
							boolean bl = this.isVisible(abstractClientPlayerEntity);
							boolean bl2 = !bl && !abstractClientPlayerEntity.isInvisibleTo(minecraftClient.player);
							boolean bl3 = minecraftClient.hasOutline(abstractClientPlayerEntity);
							RenderLayer renderLayer = this.getRenderLayer(abstractClientPlayerEntity, bl, bl2, bl3);
							VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityGlint());
							int r = getOverlay(abstractClientPlayerEntity, this.getAnimationCounter(abstractClientPlayerEntity, g));
							matrixStack.translate(0.0D, abstractClientPlayerEntity.getHeight() + 0.1F, 0.0D);
							matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
							this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
						}
					}
					matrixStack.pop();
					ci.cancel();
				}
			}
		}
	}

}
