package dev.felnull.otyacraftengine.client.gui.screen;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;

public class DebugScreen extends Screen {
    public DebugScreen() {
        super(new TextComponent("Debug Screen"));
    }

    @Override
    protected void init() {
        super.init();
    }
}
