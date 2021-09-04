package ru.pinkgoosik.goosiklib.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import ru.pinkgoosik.goosiklib.GoosikLibMod;

public class LoadCosmeticsEvent implements ClientTickEvents.StartTick {

    private boolean itsTried = false;

    @Override
    public void onStartTick(MinecraftClient client) {
        if(client.world != null){
            if (!itsTried){
                GoosikLibMod.initPlayerCosmetics();
                if(MinecraftClient.getInstance().player != null && GoosikLibMod.getPlayerCosmetics() != null){
                    GoosikLibMod.getPlayerCosmetics().getEntries().forEach(playerCosmeticEntry -> MinecraftClient.getInstance().player.sendMessage(new LiteralText("[DEBUG] supporter: " + playerCosmeticEntry.getPlayerName() + ", cosmetic: " + playerCosmeticEntry.getCosmetic()), false));
                }
                itsTried = true;
            }
        }else{
            itsTried = false;
        }
    }
}
