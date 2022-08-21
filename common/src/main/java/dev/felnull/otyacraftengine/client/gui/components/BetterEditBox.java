package dev.felnull.otyacraftengine.client.gui.components;

import dev.felnull.otyacraftengine.client.gui.components.base.IOEBaseComponent;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BetterEditBox extends EditBox implements IOEBaseComponent {
    public BetterEditBox(int x, int y, int w, int h, @NotNull Component component) {
        super(mc.font, x, y, w, h, component);
    }

    public BetterEditBox(int x, int y, int w, int h, @Nullable BetterEditBox editBox, @NotNull Component component) {
        super(mc.font, x, y, w, h, editBox, component);
    }
}
