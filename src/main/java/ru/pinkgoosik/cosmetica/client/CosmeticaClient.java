package ru.pinkgoosik.cosmetica.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.KeybindText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import ru.pinkgoosik.cosmetica.client.render.CosmeticFeatureRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;

import java.io.IOException;

public class CosmeticaClient implements ClientModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("Cosmetica");

	private static PlayerEntries playerEntries;

	public static final Gson GSON = new GsonBuilder()
			.setLenient()
			.setPrettyPrinting()
			.create();

	public static final KeyBinding RELOAD_CAPES = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.cosmetica.reload_capes",
			InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, "key.category.cosmetica"));

	@Override
	public void onInitializeClient() {
		ClientTickEvents.END_CLIENT_TICK.register(new LoadEntriesEvent());

		LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
			if (entityRenderer instanceof PlayerEntityRenderer playerEntityRenderer) {
				registrationHelper.register(new CosmeticFeatureRenderer(playerEntityRenderer));
			}
		});
	}

	public static void loadPlayerEntries() {
		LOGGER.info("Loading Player Entries...");
		try {
			playerEntries = new PlayerEntries();
		} catch (IOException e) {
			playerEntries = null;
			LOGGER.warn("Failed to load Player Entries due to an exception: " + e);
		} finally {
			if (playerEntries != null) LOGGER.info("Player Entries successfully loaded");
		}
	}

	public static PlayerEntries getPlayerEntries() {
		return playerEntries;
	}

}
