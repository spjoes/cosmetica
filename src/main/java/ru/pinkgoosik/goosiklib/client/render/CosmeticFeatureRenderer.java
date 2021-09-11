package ru.pinkgoosik.goosiklib.client.render;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;

public class CosmeticFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    public CosmeticFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
//        if(GoosikLibClient.getPlayerCosmetics() != null && !player.isInvisible()){
//            GoosikLibClient.getPlayerCosmetics().getEntries().forEach(entry -> {
//                if(entry.getPlayerName().equals(player.getName().asString())){
//                    ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
//
//                    matrices.push();
//
//                    matrices.translate(0D, -1D, 0D);
//
//                    matrices.scale(0.5F, 0.5F, 0.5F);
//                    translateToFace(matrices, this.getContextModel(), player, headYaw, headPitch);
//                    matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180.0F));
//
//                    String modelId = "goosiklib:flower_wreath#inventory";
//
//                    itemRenderer.renderItem(Items.DIAMOND.getDefaultStack(), ModelTransformation.Mode.FIXED, false, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV,
//                            itemRenderer.getModels().getModelManager().getModel(new ModelIdentifier(modelId)));
//                    matrices.pop();
//                }
//            });
//        }
    }

    static void translateToFace(MatrixStack matrices, PlayerEntityModel<AbstractClientPlayerEntity> model,
                                AbstractClientPlayerEntity player, float headYaw, float headPitch) {

        if (player.isInSwimmingPose() || player.isFallFlying()) {
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(model.head.roll));
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(headYaw));
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-45.0F));
        } else {

            if (player.isInSneakingPose() && !model.riding) {
                matrices.translate(0.0F, 0.25F, 0.0F);
            }
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(headYaw));
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(headPitch));
        }
        matrices.translate(0.0F, -0.25F, -0.3F);
    }
}
