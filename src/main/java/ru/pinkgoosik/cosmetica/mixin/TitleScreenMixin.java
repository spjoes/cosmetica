package ru.pinkgoosik.cosmetica.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.*;
import net.minecraft.text.*;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.cosmetica.client.screens.*;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

    protected TitleScreenMixin(Text text) {
        super(text);
    }

    @Inject(at = @At("RETURN"), method = "initWidgetsNormal")
    private void addCustomButton(int y, int spacingY, CallbackInfo ci) {
        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
            this.addDrawableChild(new ButtonWidget(this.width / 2 - 100 + 205, y, 50, 20, new LiteralText("Customize"), (button) -> {
                MinecraftClient.getInstance().setScreen(new CustomizeScreen(this));
            }));
        } else if(!FabricLoader.getInstance().isDevelopmentEnvironment()) {

        }
    }

}
