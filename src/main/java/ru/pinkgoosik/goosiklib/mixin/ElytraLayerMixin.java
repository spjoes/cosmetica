package ru.pinkgoosik.goosiklib.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.goosiklib.client.ColorUtil;
import ru.pinkgoosik.goosiklib.client.DyeUtils;
import ru.pinkgoosik.goosiklib.client.GoosikLibClient;
import ru.pinkgoosik.goosiklib.client.PlayerCapes;

@Mixin(ElytraFeatureRenderer.class)
public abstract class ElytraLayerMixin<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
	@Shadow
	@Final
	private ElytraEntityModel<T> elytra;

	public ElytraLayerMixin(FeatureRendererContext<T, M> renderLayerParent) {
		super(renderLayerParent);
	}

	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	public void render(MatrixStack poseStack, VertexConsumerProvider multiBufferSource, int light, T livingEntity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch, CallbackInfo info) {
		if (livingEntity instanceof AbstractClientPlayerEntity player) {
			for (PlayerCapes.PlayerCapeEntry entry : GoosikLibClient.getPlayerCapes().getEntries()) {
				if (entry.getPlayerUuid().equals(player.getGameProfile().getId().toString())) {
					info.cancel();
					ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.CHEST);
					if (itemStack.getItem() instanceof ElytraItem) {
						poseStack.push();
						poseStack.translate(0.0, 0.0, 0.125);
						this.getContextModel().copyStateTo(this.elytra);
						this.elytra.setAngles(livingEntity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);

						if (player.canRenderCapeTexture() && player.getCapeTexture() != null && player.isPartVisible(PlayerModelPart.CAPE)) {
							for (String type : entry.getType().split("\\|")) {
								if (type.equals("jeb")) {
									float[] color = DyeUtils.idk(player, tickDelta);
									this.elytra.render(poseStack, multiBufferSource.getBuffer(RenderLayer.getArmorCutoutNoCull(new Identifier("goosiklib:textures/cape/cape_layer1.png"))), light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
									this.elytra.render(poseStack, multiBufferSource.getBuffer(RenderLayer.getArmorCutoutNoCull(new Identifier("goosiklib:textures/cape/cape_layer2.png"))), light, OverlayTexture.DEFAULT_UV, color[0], color[1], color[2], 1.0F);
								}
								if (type.equals("enchanted")) {
									this.elytra.render(poseStack, ItemRenderer.getArmorGlintConsumer(multiBufferSource, RenderLayer.getArmorEntityGlint(), false, true), light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
								}
								if (type.equals("cosmic")) {
									this.elytra.render(poseStack, multiBufferSource.getBuffer(RenderLayer.getEndGateway()), light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
								}
								if (type.equals("swirly")) {
									float f = player.age + tickDelta;
									this.elytra.render(poseStack, multiBufferSource.getBuffer(RenderLayer.getEnergySwirl(new Identifier("textures/entity/creeper/creeper_armor.png"), f * 0.01F % 1.0F, f * 0.01F % 1.0F)), light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
								}
								if (type.equals("glowing")) {
									float[] color = ColorUtil.toFloatArray(ColorUtil.color(entry.getColor().replace("0x", "")));
									this.elytra.render(poseStack, multiBufferSource.getBuffer(RenderLayer.getLightning()), light, OverlayTexture.DEFAULT_UV, color[0], color[1], color[2], color[3]);
								}
								if (type.equals("normal")) {
									this.elytra.render(poseStack, multiBufferSource.getBuffer(RenderLayer.getArmorCutoutNoCull(player.getCapeTexture())), light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
								}
							}
						}
					}
				}
			}
			poseStack.pop();
		}
	}
}
