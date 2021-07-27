package red.felnull.otyacraftengine.client.gui.tooltip;

import red.felnull.otyacraftengine.client.util.IKSGClientUtil;
import red.felnull.otyacraftengine.item.tooltip.TestTooltipComponent;

public class OETooltips {
    public static void init() {
        IKSGClientUtil.registerClientToolTip(TestTooltipComponent.class, n -> new TestClientTooltipComponent());
    }
}
