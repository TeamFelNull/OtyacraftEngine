package dev.felnull.otyacraftengine.client.gui.screen;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TextComponent;

public class DebugScreen extends OEBaseScreen {
    public DebugScreen() {
        super(new TextComponent("Debug Screen"));
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(new Button(0, 0, 100, 20, new TextComponent("Test Screen"), n -> mc.setScreen(new TestScreen(this))));
    }
}
