package ru.pinkgoosik.goosiklib;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.EntityType;
import ru.pinkgoosik.goosiklib.client.PlayerCosmetics;
import ru.pinkgoosik.goosiklib.client.PlayerCosmeticsEvent;
import ru.pinkgoosik.goosiklib.client.render.CosmeticFeatureRenderer;

import java.io.IOException;

public class GoosikLibMod implements ModInitializer, ClientModInitializer {

    private static PlayerCosmetics playerCosmetics;

    @Override
    public void onInitialize() {

    }

    @Override
    public void onInitializeClient() {
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if(entityType.equals(EntityType.PLAYER))registrationHelper.register(new CosmeticFeatureRenderer((PlayerEntityRenderer)entityRenderer));
        });

        ClientTickEvents.START_CLIENT_TICK.register(new PlayerCosmeticsEvent());
    }

    public static void initPlayerCosmetics(){
        try {
            playerCosmetics = new PlayerCosmetics();
        } catch (IOException e) {
            playerCosmetics = null;
            e.printStackTrace();
        }
    }

    public static PlayerCosmetics getPlayerCosmetics(){
        return playerCosmetics;
    }
}
