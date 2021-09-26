package ru.pinkgoosik.cosmetica.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
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
				if (entries.attributes != null && (abstractClientPlayerEntity.getGameProfile().getId().equals(actualUUID) || abstractClientPlayerEntity.getGameProfile().getName().equals(entries.playerInformation.name))) {
					matrixStack.push();
					for (String type : entries.attributes.split("\\|")) {

						//TODO
						// - Fix player movement/player head movement in f5/Third-person. (will elaborate more on call with Olivia)
						// - Make Glint work correctly (I think this needs to be fixed but idk. Dont have time for testing)
						// - Add a attribute/cosmetic that will make the player have the charged creeper thing around them
						// - Sleep. Im tired so imma pull request this to Olivia's fork and make them deal with it!

						//Needed so clients can render multiple players without crashing on servers and multiplayer worlds
						OtherClientPlayerEntity otherClientPlayerEntity = new OtherClientPlayerEntity(abstractClientPlayerEntity.clientWorld, abstractClientPlayerEntity.getGameProfile());

						//Basic fields for models
						float p = MathHelper.lerp(g, abstractClientPlayerEntity.lastLimbDistance, abstractClientPlayerEntity.limbDistance);
						float q = abstractClientPlayerEntity.limbAngle - abstractClientPlayerEntity.limbDistance * (1.0F - g);
						float h = MathHelper.lerpAngleDegrees(g, abstractClientPlayerEntity.prevBodyYaw, abstractClientPlayerEntity.bodyYaw);
						float j = MathHelper.lerpAngleDegrees(g, abstractClientPlayerEntity.prevHeadYaw, abstractClientPlayerEntity.headYaw);
						float o = this.getAnimationProgress(abstractClientPlayerEntity, g);
						float m = MathHelper.lerp(g, abstractClientPlayerEntity.prevPitch, abstractClientPlayerEntity.getPitch());

						//Different fields
						MinecraftClient minecraftClient = MinecraftClient.getInstance();
						boolean bl = this.isVisible(abstractClientPlayerEntity);
						boolean bl2 = !bl && !abstractClientPlayerEntity.isInvisibleTo(minecraftClient.player);
						boolean bl3 = minecraftClient.hasOutline(abstractClientPlayerEntity);
						RenderLayer renderLayer = this.getRenderLayer(abstractClientPlayerEntity, bl, bl2, bl3);
						VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(renderLayer);
						int r = getOverlay(abstractClientPlayerEntity, this.getAnimationCounter(abstractClientPlayerEntity, g));

						//Basic
						this.setupTransforms(abstractClientPlayerEntity, matrixStack, o,h, g);
						matrixStack.scale(-1.0F, -1.0F, 1.0F);
						this.scale(abstractClientPlayerEntity, matrixStack, g);
						matrixStack.translate(0.0D, -1.5010000467300415D, 0.0D);
						this.model.animateModel(abstractClientPlayerEntity, abstractClientPlayerEntity.limbAngle -
								abstractClientPlayerEntity.limbDistance * (1.0F - g), p, g);
						this.model.setAngles(abstractClientPlayerEntity, q, p, o, j - h, m);

						//Must be added so multiplayer can be played
						matrixStack.scale(-1.0F, -1.0F, 1.0F);
						this.scale(otherClientPlayerEntity, matrixStack, g);
						matrixStack.translate(0.0D, -1.5010000467300415D, 0.0D);

						//Different model variables
						boolean child = false;
						float[] color = {1.0F, 1.0F, 1.0F};

						//Custom attributes
						if (type.equals("jeb")) color = DyeUtils.createJebColorTransition(abstractClientPlayerEntity, g);
						if (type.equals("glint")) vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityGlint());
						if (type.equals("baby")) child = true;

						//Makes model upside-down if they have dinnerbone attribute
						if (type.equals("dinnerbone")) {
							//This sets the dinnerbone model translates for you and other players
							matrixStack.translate(0.0D, (abstractClientPlayerEntity.getHeight() - 1.6F), 0.0D);
							matrixStack.translate(0.0D, (otherClientPlayerEntity.getHeight() - 1.6F), 0.0D);
						}

						//Rendering and optionally setting model to child
						this.model.child = child;
						this.model.render(matrixStack, vertexConsumer, i, r, color[0], color[1], color[2], 1.0F);
					}
					matrixStack.pop();
					ci.cancel();
				}
			}
		}
	}

}
