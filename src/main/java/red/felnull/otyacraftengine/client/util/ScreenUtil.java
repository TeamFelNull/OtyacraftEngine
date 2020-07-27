package red.felnull.otyacraftengine.client.util;

import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenUtil {
    //開いてる時にワールドを進めるかどうか
    public static boolean isPauseScreen(Screen screen) {
        return screen.func_231177_au__();
    }
}
