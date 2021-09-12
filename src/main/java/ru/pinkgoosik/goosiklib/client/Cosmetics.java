package ru.pinkgoosik.goosiklib.client;

import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.util.ModelIdentifier;

public class Cosmetics {

    public static void initCosmetics(){
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
            out.accept(new ModelIdentifier("goosiklib:cosmetic#inventory"));
        });
    }
}