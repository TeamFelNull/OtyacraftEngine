package dev.felnull.otyacraftengine.client.gui.components.test;

import dev.felnull.otyacraftengine.client.gui.components.FixedListWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TestFixedListWidget extends FixedListWidget<String> {
    public TestFixedListWidget(int x, int y, int width, int height, @NotNull Component message, int entryShowCount, @NotNull List<String> entryList, @Nullable PressEntry<String> onPressEntry, boolean selectable, TestFixedListWidget old) {
        super(x, y, width, height, message, entryShowCount, entryList, TextComponent::new, onPressEntry, selectable, old);
    }
}
