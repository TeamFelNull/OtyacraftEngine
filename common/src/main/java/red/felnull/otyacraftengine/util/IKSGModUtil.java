package red.felnull.otyacraftengine.util;


import me.shedaniel.architectury.platform.Platform;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import org.apache.commons.lang3.StringUtils;
import red.felnull.otyacraftengine.impl.OEExpectPlatform;

import java.util.List;

public class IKSGModUtil {


    public static String getModID(Potion potion) {
        ResourceLocation registryName = Registry.POTION.getKey(potion);
        return registryName.getNamespace();
    }

    public static String getModID(Enchantment enchantment) {
        ResourceLocation registryName = Registry.ENCHANTMENT.getKey(enchantment);
        return registryName.getNamespace();
    }

    public static String getModID(Item item) {
        ResourceLocation registryName = Registry.ITEM.getKey(item);
        return registryName.getNamespace();
    }

    public static String getModName(String modid) {

        if (Platform.getModIds().contains(modid))
            return Platform.getMod(modid).getName();

        return StringUtils.capitalize(modid);
    }

    public static String getModVersion(String modid) {
        if (Platform.getModIds().contains(modid))
            return Platform.getMod(modid).getVersion();
        return "";
    }

    public static <T> List<T> getModEntrypoints(Class<T> type, String key, Class<?> anotation) {
        return OEExpectPlatform.getModEntrypoints(type, key, anotation);
    }

}
