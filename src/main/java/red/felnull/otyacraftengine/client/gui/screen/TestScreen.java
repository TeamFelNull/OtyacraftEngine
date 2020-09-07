package red.felnull.otyacraftengine.client.gui.screen;

import net.minecraft.util.text.StringTextComponent;
import red.felnull.otyacraftengine.client.gui.widget.IkisugiWidget;
import red.felnull.otyacraftengine.client.gui.widget.ScrollBarSlider;

public class TestScreen extends IkisugiScreen {
    private ScrollBarSlider sc;

    public TestScreen() {
        super(new StringTextComponent("test"));
    }

    @Override
    public void initByIKSG() {
        sc = this.addWidgetByIKSG(new ScrollBarSlider(200, 10, 100, 10, 0, 0, 82, 67, IkisugiWidget.OE_WIDGET));
    }

}
