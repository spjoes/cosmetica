package ru.pinkgoosik.cosmetica.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.pinkgoosik.cosmetica.client.render.CosmeticFeatureRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;

import java.io.IOException;

public class CosmeticaClient implements ClientModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("Cosmetica");

	private static PlayerCapes playerCapes;
	private static PlayerCosmetics playerCosmetics;

	@Override
	public void onInitializeClient() {
		ClientTickEvents.END_CLIENT_TICK.register(new LoadCosmeticsEvent());

		LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
			if(entityRenderer instanceof PlayerEntityRenderer playerEntityRenderer){
				registrationHelper.register(new CosmeticFeatureRenderer(playerEntityRenderer));
			}
		});
	}

	public static void initPlayerCosmetics() {
		LOGGER.info("Loading Player Cosmetics...");
		try {
			playerCosmetics = new PlayerCosmetics();
		} catch (IOException e) {
			playerCosmetics = null;
			LOGGER.warn("Failed to load Player Cosmetics due to an exception: " + e);
		} finally {
			if (playerCosmetics != null) LOGGER.info("Player Cosmetics successfully loaded");
		}
	}

	public static void initPlayerCapes() {
		LOGGER.info("Loading Player Capes...");
		try {
			playerCapes = new PlayerCapes();
		} catch (IOException e) {
			playerCapes = null;
			LOGGER.warn("Failed to load Player Capes due to an exception: " + e);
		} finally {
			if (playerCapes != null) LOGGER.info("Player Capes successfully loaded");
		}
	}

	public static PlayerCapes getPlayerCapes() {
		return playerCapes;
	}

	public static PlayerCosmetics getPlayerCosmetics() {
		return playerCosmetics;
	}

}
