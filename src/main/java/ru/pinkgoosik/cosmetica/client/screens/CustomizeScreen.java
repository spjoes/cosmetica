package ru.pinkgoosik.cosmetica.client.screens;

import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.SpruceTexts;
import dev.lambdaurora.spruceui.Tooltip;
import dev.lambdaurora.spruceui.screen.SpruceScreen;
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget;
import dev.lambdaurora.spruceui.widget.text.SpruceNamedTextFieldWidget;
import dev.lambdaurora.spruceui.widget.text.SpruceTextFieldWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import org.jetbrains.annotations.Nullable;
import ru.pinkgoosik.cosmetica.client.CosmeticaClient;
import ru.pinkgoosik.cosmetica.toast.ToastExecutor;

/**
 * Represents a screen to navigate to the different SpruceUI test screens.
 *
 * @author LambdAurora
 */
public class CustomizeScreen extends SpruceScreen {
    private final Screen parent;

    public CustomizeScreen(@Nullable Screen parent) {
        super(new LiteralText("Cosmetica Cosmetics"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        int startY = this.height / 4 + 48;
        // Add done button.
        this.addDrawableChild(new SpruceButtonWidget(Position.of(this, this.width / 2 - 75, this.height - 29), 75, 20,new LiteralText("Cancel"),
                btn -> this.client.setScreen(this.parent)));
        this.addDrawableChild(new SpruceButtonWidget(Position.of(this, this.width / 2, this.height - 29), 75, 20,new LiteralText("Apply"),
                btn -> this.ApplyCosmetics(client, 0)));
    }

    @Override
    public void renderTitle(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 8, 16777215);
    }

    public void ApplyCosmetics(MinecraftClient client, int changes) {
        ToastExecutor.executeApplyToast("Applied!", "Applied " + changes + " changes!");
        this.client.setScreen(this.parent);
    }
}