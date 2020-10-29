package red.felnull.otyacraftengine.client.gui.screen;

import net.minecraft.util.text.StringTextComponent;
import red.felnull.otyacraftengine.client.gui.widget.IkisugiWidget;
import red.felnull.otyacraftengine.client.gui.widget.ScrollBarSlider;
import red.felnull.otyacraftengine.util.ClockTimer;

public class TestScreen extends IkisugiScreen {
    protected ClockTimer timer;
    private ScrollBarSlider sc;

    public TestScreen() {
        super(new StringTextComponent("test"));
    }

    @Override
    public void initByIKSG() {
        super.initByIKSG();
        this.timer = new ClockTimer(m -> isOpend());
        sc = this.addWidgetByIKSG(new ScrollBarSlider(200, 10, 100, 10, 0, 0, 82, 67, IkisugiWidget.OE_WIDGET));
        timer.addTask("ikisugi", new ClockTimer.ITask() {
            @Override
            public boolean isStop(ClockTimer timer) {
                return false;
            }

            @Override
            public void run(ClockTimer timer) {
                System.out.println("test");
            }

            @Override
            public long time(ClockTimer timer) {
                return 10;
            }
        });
    }

    @Override
    public void tickByIKSG() {

    }
}
