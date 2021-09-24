package ru.pinkgoosik.cosmetica.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;

public class LoadEntriesEvent implements ClientTickEvents.EndTick {

    boolean isLoaded = false;

    @Override
    public void onEndTick(MinecraftClient client) {
        if (CosmeticaClient.RELOAD_CAPES.wasPressed()) isLoaded = false;
        if (client.world != null) {
            if (!isLoaded) {
                CosmeticaClient.loadPlayerEntries();
                isLoaded = true;
            }
        } else isLoaded = false;
    }

}
