package red.felnull.otyacraftengine.asm.lib;

import cpw.mods.modlauncher.Launcher;

public class FMLDeobfuscatingRemapper {
    public static boolean isMapping() {
        return Launcher.INSTANCE.environment().findNameMapping("srg").isPresent();
    }
}