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
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.cosmetica.client.PlayerCosmetics;

import java.io.IOException;

@Environment(EnvType.CLIENT)
@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

    int SelectedUser = 2;

    /*
    0 = PinkGoosik
    1 = OliviaTheVampire
    2 = spjoes
     */


    PlayerCosmetics playerCosmetics = new PlayerCosmetics();

    // This is causing the increased load times. But getting rid of it makes Lien 43 complain like a baby.
    public LivingEntityRendererMixin() throws IOException {
    }


    @Inject(method = "setupTransforms", at = @At(value = "TAIL"))
    private void dinnerboneEntities(LivingEntity entity, MatrixStack matrices, float _animationProgress, float _bodyYaw, float _tickDelta, CallbackInfo _info) {

        String string = Formatting.strip(entity.getName().getString());
                // If the Selected User (2)                           is the user logged in.
            if ((playerCosmetics.getEntries().get(SelectedUser).playerName().equals(string)) && (!(entity instanceof PlayerEntity) || ((PlayerEntity) entity).isPartVisible(PlayerModelPart.CAPE))) {

                // But if the user doesn't want to be dinnerboned. Dont force him to be.
                if (playerCosmetics.getEntries().get(SelectedUser).playerDinnerBoned().toString().equals("false")) {
                    matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(0.0F));
                // But if the user has dinnerboned enabled. Flip em'
                } else if (playerCosmetics.getEntries().get(2).playerDinnerBoned().toString().equals("true")){
                    matrices.translate(0.0D, entity.getHeight() + 0.1F, 0.0D);
                    matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
                }
            }
    }
}