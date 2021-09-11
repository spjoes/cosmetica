package ru.pinkgoosik.goosiklib.client;

import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class GoosikLibClient implements ClientModInitializer {

    public static final Logger LOGGER = LogManager.getLogger("GoosikLib");

    private static PlayerCosmetics playerCosmetics;
    private static PlayerCapes playerCapes;

    @Override
    public void onInitializeClient() {
//        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
//            if(entityRenderer instanceof PlayerEntityRenderer playerEntityRenderer){
//                registrationHelper.register(new CosmeticFeatureRenderer(playerEntityRenderer));
//            }
//        });

//        ClientTickEvents.START_CLIENT_TICK.register(new LoadCosmeticsEvent());
        GoosikLibClient.initPlayerCapes();
    }

    public static void initPlayerCosmetics(){
        try {
            playerCosmetics = new PlayerCosmetics();
        } catch (IOException e) {
            playerCosmetics = null;
            LOGGER.warn("Failed to load Player Cosmetics due to an exception: " + e);
        } finally {
            if(playerCosmetics != null) LOGGER.info("Player Cosmetics successfully loaded");
        }
    }

    public static void initPlayerCapes(){
        LOGGER.info("Loading Player Capes...");
        try {
            playerCapes = new PlayerCapes();
        } catch (IOException e) {
            playerCapes = null;
            LOGGER.warn("Failed to load Player Capes due to an exception: " + e);
        } finally {
            if(playerCapes != null) LOGGER.info("Player Capes successfully loaded");
        }
    }

    public static PlayerCosmetics getPlayerCosmetics(){
        return playerCosmetics;
    }

    public static PlayerCapes getPlayerCapes(){
        return playerCapes;
    }
}
