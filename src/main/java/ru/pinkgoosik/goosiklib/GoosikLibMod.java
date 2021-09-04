package ru.pinkgoosik.goosiklib;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import ru.pinkgoosik.goosiklib.client.PlayerCosmetics;
import ru.pinkgoosik.goosiklib.client.LoadCosmeticsEvent;
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
            if(entityRenderer instanceof PlayerEntityRenderer playerEntityRenderer){
                registrationHelper.register(new CosmeticFeatureRenderer(playerEntityRenderer));
            }
        });

        ClientTickEvents.START_CLIENT_TICK.register(new LoadCosmeticsEvent());
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
