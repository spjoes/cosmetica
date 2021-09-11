package ru.pinkgoosik.goosiklib.client;

import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class GoosikLibClient implements ClientModInitializer {

    public static final Logger LOGGER = LogManager.getLogger("GoosikLib");

    private static PlayerCapes playerCapes;

    @Override
    public void onInitializeClient() {
        initPlayerCapes();
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

    public static PlayerCapes getPlayerCapes(){
        return playerCapes;
    }
}
