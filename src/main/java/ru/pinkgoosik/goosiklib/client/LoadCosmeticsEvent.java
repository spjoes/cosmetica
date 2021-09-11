package ru.pinkgoosik.goosiklib.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class LoadCosmeticsEvent implements ClientTickEvents.StartTick {

    private boolean tried = false;

    @Override
    public void onStartTick(MinecraftClient client) {
        if(client.world != null){
            if (!tried){
                GoosikLibClient.initPlayerCapes();
                tried = true;
            }
        } else tried = false;
    }
}