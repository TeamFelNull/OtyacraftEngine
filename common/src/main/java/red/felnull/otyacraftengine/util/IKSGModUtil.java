package red.felnull.otyacraftengine.util;

import me.shedaniel.architectury.platform.fabric.PlatformImpl;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

public class IKSGModUtil {
    public static String getItemAddModID(ItemStack itemStack) {
        ResourceLocation registryName = Registry.ITEM.getKey(itemStack.getItem());
        return registryName.getNamespace();
    }

    public static String getModName(String modid) {

        if (PlatformImpl.getModIds().contains(modid))
            return PlatformImpl.getMod(modid).getName();

        return StringUtils.capitalize(modid);
    }

    public static String getModVersion(String modid) {
        if (PlatformImpl.getModIds().contains(modid))
            return PlatformImpl.getMod(modid).getVersion();

        return "";
    }

}
