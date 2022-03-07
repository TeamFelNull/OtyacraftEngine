package dev.felnull.otyacraftengine.client.gui.components.base;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class OEBaseWidget extends AbstractWidget implements IOEBaseComponent {
    @Nullable
    private final String widgetTypeName;

    public OEBaseWidget(int x, int y, int width, int height, @NotNull Component message) {
        this(x, y, width, height, null, message);
    }

    public OEBaseWidget(int x, int y, int width, int height, @Nullable String widgetTypeName, @NotNull Component message) {
        super(x, y, width, height, message);
        this.widgetTypeName = widgetTypeName;
    }

    @Override
    public void updateNarration(@NotNull NarrationElementOutput narrationElementOutput) {
        this.defaultButtonNarrationText(narrationElementOutput);
    }

    @Override
    protected void defaultButtonNarrationText(NarrationElementOutput narrationElementOutput) {
        narrationElementOutput.add(NarratedElementType.TITLE, this.createNarrationMessage());
        if (this.widgetTypeName != null && this.active) {
            if (this.isFocused()) {
                narrationElementOutput.add(NarratedElementType.USAGE, new TranslatableComponent("narration." + widgetTypeName + ".usage.focused"));
            } else {
                narrationElementOutput.add(NarratedElementType.USAGE, new TranslatableComponent("narration." + widgetTypeName + ".usage.hovered"));
            }
        }
    }

    @Override
    protected MutableComponent createNarrationMessage() {
        if (widgetTypeName == null)
            return new TranslatableComponent("gui.narrate.widget", getMessage());
        return new TranslatableComponent("gui.narrate." + widgetTypeName, getMessage());
    }

    @Override
    public void onClick(double d, double e) {
        this.onPress();
    }

    @Override
    public boolean keyPressed(int i, int j, int k) {
        if (this.active && this.visible) {
            if (i != 257 && i != 32 && i != 335) return false;
            this.playDownSound(Minecraft.getInstance().getSoundManager());
            this.onPress();
            return true;
        }
        return false;
    }

    public abstract void onPress();

    public @Nullable String getWidgetTypeName() {
        return widgetTypeName;
    }
}
