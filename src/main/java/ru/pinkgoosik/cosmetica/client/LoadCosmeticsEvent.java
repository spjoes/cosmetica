package ru.pinkgoosik.cosmetica.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class LoadCosmeticsEvent implements ClientTickEvents.EndTick {

    boolean isLoaded = false;

    @Override
    public void onEndTick(MinecraftClient client) {
        if(client.world != null){
            if(!isLoaded){
                CosmeticaClient.initPlayerCapes();
                CosmeticaClient.initPlayerCosmetics();
                isLoaded = true;
            }
        }else isLoaded = false;
    }
}
