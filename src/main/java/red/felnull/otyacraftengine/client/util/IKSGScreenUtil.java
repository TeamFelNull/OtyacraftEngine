package red.felnull.otyacraftengine.client.util;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IKSGScreenUtil {
    //開いてる時にワールドを進めるかどうか
    public static boolean isPauseScreen(Screen screen) {
        return screen.func_231177_au__();
    }

    public static void setVisible(Widget widget, boolean visible) {
        widget.field_230694_p_ = visible;
    }

    public static void setActive(Widget widget, boolean active) {
        widget.field_230693_o_ = active;
    }

}
