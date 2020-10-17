package red.felnull.otyacraftengine.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.SaveFormat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.IRegistryDelegate;
import red.felnull.otyacraftengine.asm.DeobfNames;
import red.felnull.otyacraftengine.util.IKSGReflectionUtil;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class IKSGClientUtil {
    private static Minecraft mc = Minecraft.getInstance();

    public static int getFps() {
        return IKSGReflectionUtil.getPrivateField(Minecraft.class, DeobfNames.debugFPS);
    }

    public static String getCurrentWorldName() {
        return mc.isSingleplayer() ? ((SaveFormat.LevelSave) IKSGReflectionUtil.getPrivateField(MinecraftServer.class, mc.getIntegratedServer(), DeobfNames.anvilConverterForAnvilFile)).func_237282_a_() : mc.getCurrentServerData().serverIP;
    }

    public static Map<IRegistryDelegate<Item>, IItemColor> getItemColorsColor(ItemColors c) {

        return IKSGReflectionUtil.getPrivateField(ItemColors.class, c, DeobfNames.ItemColors_colors);
    }

    public static boolean isKeyInput(String key, boolean nulltrue) {
        return isKeyInput(getKeyBind(key), nulltrue);
    }

    public static boolean isKeyInput(KeyBinding key, boolean nulltrue) {

        if (key == null)
            return nulltrue;

        return InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), key.getKeyBinding().getKey().getKeyCode());
    }

    public static KeyBinding getKeyBind(String name) {
        KeyBinding[] binds = Minecraft.getInstance().gameSettings.keyBindings;

        Optional<KeyBinding> k = Arrays.asList(binds).stream().filter(n -> n.getKeyDescription().equals(name)).findFirst();

        return k.isPresent() ? k.get() : null;
    }


}
