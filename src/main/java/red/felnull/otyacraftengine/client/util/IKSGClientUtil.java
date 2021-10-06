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
import org.lwjgl.glfw.GLFW;
import red.felnull.otyacraftengine.asm.DeobfNames;
import red.felnull.otyacraftengine.client.handler.ClientHandler;
import red.felnull.otyacraftengine.util.IKSGReflectionUtil;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public class IKSGClientUtil {
    private static final Minecraft mc = Minecraft.getInstance();

    public static int getFps() {
        return IKSGReflectionUtil.getPrivateField(Minecraft.class, DeobfNames.debugFPS);
    }

    public static String getCurrentWorldName() {
        return mc.isSingleplayer() ? ((SaveFormat.LevelSave) IKSGReflectionUtil.getPrivateField(MinecraftServer.class, mc.getIntegratedServer(), DeobfNames.anvilConverterForAnvilFile)).getSaveName() : mc.getCurrentServerData().serverIP;
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
        int code = key.getKeyBinding().getKey().getKeyCode();
        if (GLFW.GLFW_MOUSE_BUTTON_8 >= code)
            return nulltrue;
        return InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), code);
    }

    public static KeyBinding getKeyBind(String name) {
        KeyBinding[] binds = Minecraft.getInstance().gameSettings.keyBindings;

        Optional<KeyBinding> k = Arrays.stream(binds).filter(n -> n.getKeyDescription().equals(name)).findFirst();

        return k.orElse(null);
    }

    public static UUID getCurrentWorldUUID() {
        return ClientHandler.CURRENTWORLDUUID;
    }


}
