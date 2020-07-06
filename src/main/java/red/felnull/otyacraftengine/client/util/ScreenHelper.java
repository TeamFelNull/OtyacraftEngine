package red.felnull.otyacraftengine.client.util;

import net.minecraft.client.gui.screen.Screen;

public class ScreenHelper {
    //開いてる時にワールドを進めるかどうか
    public static boolean isPauseScreen(Screen screen) {
        return screen.func_231177_au__();
    }
}
