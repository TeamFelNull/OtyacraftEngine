package dev.felnull.otyacraftenginetest.client.gui.components;

import dev.felnull.otyacraftengine.client.gui.components.FixedListWidget;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class TestFixedListWidget extends FixedListWidget<TestFixedListWidget.TestListEntry> {

    public TestFixedListWidget(int x, int y, int width, int height, @NotNull Component message, int entryShowCount, @NotNull List<TestListEntry> entryList, @NotNull Function<TestListEntry, Component> entryName, @Nullable PressEntry<TestListEntry> onPressEntry, boolean selectable, @Nullable FixedListWidget<TestListEntry> old) {
        super(x, y, width, height, message, entryShowCount, entryList, entryName, onPressEntry, selectable, old);
    }

    public static record TestListEntry(String text) {
    }
}
