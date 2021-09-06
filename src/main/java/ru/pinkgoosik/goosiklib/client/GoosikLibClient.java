package ru.pinkgoosik.goosiklib.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import ru.pinkgoosik.goosiklib.GoosikLibMod;
import ru.pinkgoosik.goosiklib.client.model.Cosmetics;
import ru.pinkgoosik.goosiklib.client.render.CosmeticFeatureRenderer;

import java.io.IOException;

public class GoosikLibClient implements ClientModInitializer {

    private static PlayerCosmetics playerCosmetics;

    @Override
    public void onInitializeClient() {
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if(entityRenderer instanceof PlayerEntityRenderer playerEntityRenderer){
                registrationHelper.register(new CosmeticFeatureRenderer(playerEntityRenderer));
            }
        });

        ClientTickEvents.START_CLIENT_TICK.register(new LoadCosmeticsEvent());
        Cosmetics.initCosmetics();
    }

    public static void initPlayerCosmetics(){
        try {
            playerCosmetics = new PlayerCosmetics();
        } catch (IOException e) {
            playerCosmetics = null;
            GoosikLibMod.LOGGER.warn("Failed to load Player Cosmetics due to an exception: " + e);
        } finally {
            if(playerCosmetics != null) GoosikLibMod.LOGGER.info("Player Cosmetics successfully loaded");
        }
    }

    public static PlayerCosmetics getPlayerCosmetics(){
        return playerCosmetics;
    }
}
