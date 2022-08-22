package dev.felnull.otyacraftengine.client.gui.screen;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public abstract class OEBaseScreen extends Screen implements IOEBaseScreen {
    @Nullable
    private final Screen parentScreen;

    protected OEBaseScreen(Component message, @Nullable Screen parent) {
        super(message);
        this.parentScreen = parent;
    }

    protected OEBaseScreen(Component message) {
        this(message, null);
    }

    public @Nullable
    Screen getParentScreen() {
        return parentScreen;
    }

    @Override
    public void onClose() {
        mc.setScreen(parentScreen);
    }
}