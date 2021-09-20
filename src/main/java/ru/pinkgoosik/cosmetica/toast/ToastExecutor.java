package ru.pinkgoosik.cosmetica.toast;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.LiteralText;

public class ToastExecutor {
    public static void executeApplyToast(String toastName, String toastText){

        SystemToast toast = SystemToast.create(MinecraftClient.getInstance(), SystemToast.Type.TUTORIAL_HINT,
                new LiteralText(toastName), new LiteralText(toastText));
        MinecraftClient.getInstance().getToastManager().add(toast);
    }
}
