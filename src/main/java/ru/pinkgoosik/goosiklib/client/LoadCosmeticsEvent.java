package ru.pinkgoosik.goosiklib.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;

public class LoadCosmeticsEvent implements ClientTickEvents.StartTick {

    private boolean tried = false;

    @Override
    public void onStartTick(MinecraftClient client) {
        if(client.world != null){
            if (!tried){
//                GoosikLibClient.initPlayerCosmetics();
                GoosikLibClient.initPlayerCapes();
//                sendDebugMessage();
                tried = true;
            }
        }else tried = false;
    }

    public void sendDebugMessage(){
        if(FabricLoader.INSTANCE.isDevelopmentEnvironment()){
            if(MinecraftClient.getInstance().player != null && GoosikLibClient.getPlayerCosmetics() != null){
                GoosikLibClient.getPlayerCosmetics().getEntries().forEach(playerCosmeticEntry -> MinecraftClient.getInstance().player.sendMessage(new LiteralText("[DEBUG] supporter: " + playerCosmeticEntry.getPlayerName() + ", cosmetic: " + playerCosmeticEntry.getCosmetic()), false));
            }
            if(MinecraftClient.getInstance().player != null && GoosikLibClient.getPlayerCapes() != null){
                GoosikLibClient.getPlayerCapes().getEntries().forEach(playerCapeEntry -> {
                    MinecraftClient.getInstance().player.sendMessage(new LiteralText("[GoosikLib] player: " + playerCapeEntry.getPlayerName()), false);
                    MinecraftClient.getInstance().player.sendMessage(new LiteralText("uuid: " + playerCapeEntry.getPlayerUuid()), false);
                    MinecraftClient.getInstance().player.sendMessage(new LiteralText("cape: " + playerCapeEntry.getCape()), false);
                });
            }
        }
    }
}
