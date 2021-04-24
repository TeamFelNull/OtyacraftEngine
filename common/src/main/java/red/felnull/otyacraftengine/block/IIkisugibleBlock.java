package red.felnull.otyacraftengine.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;

public interface IIkisugibleBlock {
     ResourceLocation getRegistryName();

     @Environment(EnvType.CLIENT)
     default boolean isTransparentRenderLayer() {
          return false;
     }
}
