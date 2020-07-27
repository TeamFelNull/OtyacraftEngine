package red.felnull.otyacraftengine.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.SaveFormat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import red.felnull.otyacraftengine.core.DeobfNames;
import red.felnull.otyacraftengine.core.lib.ObfuscationReflectionUtil;

@OnlyIn(Dist.CLIENT)
public class ClientUtil {
    private static Minecraft mc = Minecraft.getInstance();

    public static int getFps() {
        return ObfuscationReflectionUtil.getPrivateValue(Minecraft.class, DeobfNames.debugFPS);
    }

    public static String getCurrentWorldName() {
        return mc.isSingleplayer() ? ((SaveFormat.LevelSave) ObfuscationReflectionUtil.getPrivateValue(MinecraftServer.class, mc.getIntegratedServer(), DeobfNames.anvilConverterForAnvilFile)).func_237282_a_() : mc.getCurrentServerData().serverIP;
    }
}
