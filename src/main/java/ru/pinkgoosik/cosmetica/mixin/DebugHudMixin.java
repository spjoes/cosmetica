package ru.pinkgoosik.cosmetica.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

@Mixin(DebugHud.class)
abstract class DebugHudMixin {

    @Shadow
    protected abstract List<String> getLeftText();

    @Inject(method = "getLeftText", at = @At("RETURN"))
    protected void AddData(CallbackInfoReturnable<List<String>> cir) {
        List<String> f3List = cir.getReturnValue();
        f3List.add("Using Cosmetica version: " + FabricLoader.getInstance().getModContainer("cosmetica").get().getMetadata().getVersion().getFriendlyString());
    }
}