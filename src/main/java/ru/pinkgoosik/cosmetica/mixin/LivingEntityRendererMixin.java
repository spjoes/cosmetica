package ru.pinkgoosik.cosmetica.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.cosmetica.client.CosmeticaClient;
import ru.pinkgoosik.cosmetica.client.Cosmetics;
import ru.pinkgoosik.cosmetica.client.PlayerCapes;
import ru.pinkgoosik.cosmetica.client.PlayerCosmetics;

@Environment(EnvType.CLIENT)
@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

    @Inject(method = "setupTransforms", at = @At(value = "TAIL"))
    private void dinnerboneEntities(LivingEntity entity, MatrixStack matrices, float _animationProgress, float _bodyYaw, float _tickDelta, CallbackInfo _info) {
        String string = Formatting.strip(entity.getName().getString());
//        if (CosmeticaClient.getPlayerCosmetics().getEntries().get(3).playerDinnerBoned().equals("true")) {
//            if(!(entity instanceof PlayerEntity)) {
//                System.out.println("true");
//                matrices.translate(0.0D, entity.getHeight() + 0.1F, 0.0D);
//                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
//            } if(!(entity instanceof MobEntity)) {
//                matrices.translate(0.0D, entity.getHeight() + 0.1F, 0.0D);
//                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(0F));
//            }
//        } else {
            if (("spjoes".equals(string)) && (!(entity instanceof PlayerEntity) || ((PlayerEntity) entity).isPartVisible(PlayerModelPart.CAPE))) {
                System.out.println("false");
                matrices.translate(0.0D, entity.getHeight() + 0.1F, 0.0D);
                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
            }
        //}
    }
}