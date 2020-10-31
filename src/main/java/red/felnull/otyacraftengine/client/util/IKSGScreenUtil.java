package red.felnull.otyacraftengine.client.util;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.client.gui.screen.IOpendScreen;

@OnlyIn(Dist.CLIENT)
public class IKSGScreenUtil {
    //開いてる時にワールドを進めるかどうか
    public static boolean isPauseScreen(Screen screen) {
        return screen.isPauseScreen();
    }

    //見えるかどうかセット
    public static void setVisible(Widget widget, boolean visible) {
        widget.visible = visible;
    }

    //有効かどうかセット
    public static void setActive(Widget widget, boolean active) {
        widget.active = active;
    }

    public static boolean isOpendScreen(Screen screen) {

        if (screen == null)
            return false;

        if (screen instanceof IOpendScreen)
            return ((IOpendScreen) screen).isOpend();

        return OtyacraftEngine.proxy.getMinecraft().currentScreen == screen;
    }
}
