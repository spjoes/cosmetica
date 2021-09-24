package ru.pinkgoosik.cosmetica.mixin;

import ca.weblite.objc.Client;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.cosmetica.client.CosmeticaClient;
import ru.pinkgoosik.cosmetica.client.PlayerCosmetics;

@Environment(EnvType.CLIENT)
@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

    @Inject(method = "setupTransforms", at = @At(value = "TAIL"))
    private void dinnerboneEntities(LivingEntity entity, MatrixStack matrices, float _animationProgress, float _bodyYaw, float _tickDelta, CallbackInfo _info) {
        if (CosmeticaClient.getPlayerCosmetics() != null) {
            for (PlayerCosmetics.PlayerCosmeticEntry entry : CosmeticaClient.getPlayerCosmetics().getEntries()) {
                for (String type : entry.cosmetic().split("\\|")) {
                    if (entity instanceof PlayerEntity) {
                        if (type.equals("dinnerbone")) {
                            if (entry.playerUuid().toString().equals(entity.getUuid().toString())) {
                                if ((!(entity instanceof PlayerEntity) || ((PlayerEntity) entity).isPartVisible(PlayerModelPart.CAPE))) {
                                    matrices.translate(0.0D, entity.getHeight() + 0.1F, 0.0D);
                                    matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}