package red.felnull.otyacraftengine.client.gui.components;

import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class TestFixedButtonsList extends FixedButtonsList<String> {

    public TestFixedButtonsList(int x, int y, int w, int h, int num, Component name, List<String> list, Function<String, Component> listName, Consumer<PressState<String>> onPress) {
        super(x, y, w, h, FixedButtonsList.WIDGETS_TEXTURE, 0, 42, 256, 256, num, name, list, listName, onPress);
    }
}
