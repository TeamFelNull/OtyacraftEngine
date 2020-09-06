package red.felnull.otyacraftengine.client.gui.screen;

import net.minecraft.util.text.StringTextComponent;
import red.felnull.otyacraftengine.client.gui.widget.Checkbox;

public class TestScreen extends IkisugiScreen {
    private Checkbox sc;

    public TestScreen() {
        super(new StringTextComponent("test"));
    }

    @Override
    public void initByIKSG() {
        sc = this.addWidgetByIKSG(new Checkbox(200, 10));
    }

}
