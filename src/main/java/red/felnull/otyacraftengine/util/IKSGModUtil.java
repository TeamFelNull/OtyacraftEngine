package red.felnull.otyacraftengine.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.ModList;
import org.apache.commons.lang3.StringUtils;
import red.felnull.otyacraftengine.api.registries.OERegistries;

import java.util.Random;

public class IKSGModUtil {
    public static String getModVersion(String modid) {

        try {
            return ModList.get().getModContainerById(modid)
                    .map(modContainer -> modContainer.getModInfo().getVersion()).orElse(null).toString();
        } catch (Exception e) {
            return "Error!!";
        }

    }

    public static String getModName(String modid) {
        try {
            return ModList.get().getModContainerById(modid)
                    .map(modContainer -> modContainer.getModInfo().getDisplayName())
                    .orElse(StringUtils.capitalize(modid));
        } catch (Exception e) {
            return "Error!!";
        }
    }

    public static TextFormatting getModColor(String modid) {
        if (OERegistries.MOD_COLOR.containsKey(modid)) {
            return IKSGColorUtil.convertTextFormattingFromColorCode(OERegistries.MOD_COLOR.get(modid));
        } else {
            Random r = new Random(IKSGMath.convertStringToInteger(modid));
            return IKSGColorUtil.convertTextFormattingFromColorCode(r.nextInt(16777215));
        }
    }

    public static String getModID(ItemStack stack) {
        return stack.getItem().getCreatorModId(stack);
    }
}
