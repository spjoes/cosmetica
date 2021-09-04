package ru.pinkgoosik.goosiklib.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import ru.pinkgoosik.goosiklib.GoosikLibMod;

public class PlayerCosmeticsEvent implements ClientTickEvents.StartTick {

    private boolean loaded = false;

    @Override
    public void onStartTick(MinecraftClient client) {
        if(client.world != null){
            if (!loaded){
                GoosikLibMod.initPlayerCosmetics();
                assert MinecraftClient.getInstance().player != null;
                GoosikLibMod.getPlayerCosmetics().getEntries().forEach(playerCosmeticEntry -> MinecraftClient.getInstance().player.sendMessage(new LiteralText("[DEBUG] supporter: " + playerCosmeticEntry.getPlayerName() + ", cosmetic: " + playerCosmeticEntry.getCosmetic()), false));
                loaded = true;
            }
        }else{
            loaded = false;
        }
    }
}
