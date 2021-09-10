package ru.pinkgoosik.goosiklib.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import ru.pinkgoosik.goosiklib.GoosikLibMod;

import java.io.IOException;

public class GoosikLibClient implements ClientModInitializer {

    private static PlayerCosmetics playerCosmetics;
    private static PlayerCapes playerCapes;

    @Override
    public void onInitializeClient() {
//        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
//            if(entityRenderer instanceof PlayerEntityRenderer playerEntityRenderer){
//                registrationHelper.register(new CosmeticFeatureRenderer(playerEntityRenderer));
//            }
//        });

        ClientTickEvents.START_CLIENT_TICK.register(new LoadCosmeticsEvent());
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

    public static void initPlayerCapes(){
        try {
            playerCapes = new PlayerCapes();
        } catch (IOException e) {
            playerCapes = null;
            GoosikLibMod.LOGGER.warn("Failed to load Player Capes due to an exception: " + e);
        } finally {
            if(playerCapes != null) GoosikLibMod.LOGGER.info("Player Capes successfully loaded");
        }
    }

    public static PlayerCosmetics getPlayerCosmetics(){
        return playerCosmetics;
    }

    public static PlayerCapes getPlayerCapes(){
        return playerCapes;
    }
}
