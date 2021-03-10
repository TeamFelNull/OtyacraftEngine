package red.felnull.otyacraftengine.client.impl.fabric;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.resources.model.ModelBakery;

public class OEClientExpectPlatformImpl {
    public static ModelBakery bakery;

    public static boolean isMiddlePressed(MouseHandler mouseHelper) {
        return mouseHelper.isMiddlePressed;
    }

    public static InputConstants.Key getKey(KeyMapping key) {
        return key.key;
    }

    public static ModelBakery getModelBakery() {
        return bakery;
    }
}
