package red.felnull.otyacraftengine.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.SaveFormat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.IRegistryDelegate;
import red.felnull.otyacraftengine.asm.DeobfNames;
import red.felnull.otyacraftengine.asm.lib.ObfuscationReflectionUtil;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class IKSGClientUtil {
    private static Minecraft mc = Minecraft.getInstance();

    public static int getFps() {
        return ObfuscationReflectionUtil.getPrivateValue(Minecraft.class, DeobfNames.debugFPS);
    }

    public static String getCurrentWorldName() {
        return mc.isSingleplayer() ? ((SaveFormat.LevelSave) ObfuscationReflectionUtil.getPrivateValue(MinecraftServer.class, mc.getIntegratedServer(), DeobfNames.anvilConverterForAnvilFile)).func_237282_a_() : mc.getCurrentServerData().serverIP;
    }

    public static Map<IRegistryDelegate<Item>, IItemColor> getItemColorsColor(ItemColors c) {

        return ObfuscationReflectionUtil.getPrivateValue(ItemColors.class, c, DeobfNames.ItemColors_colors);
    }
}
