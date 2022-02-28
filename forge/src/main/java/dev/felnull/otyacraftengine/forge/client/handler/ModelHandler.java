package dev.felnull.otyacraftengine.forge.client.handler;

import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.entrypoint.OEClientEntryPointManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModelHandler {

    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent e) {
        OEClientEntryPointManager.getInstance().call().onModelRegistry(location -> ForgeModelBakery.addSpecialModel(new ModelResourceLocation(location, OtyacraftEngine.MODID + "_default")));
    }

    //   @SubscribeEvent
    //   public static void onModelBake(ModelBakeEvent e) {
    /*    System.out.println("ｳｧｧ!!ｵﾚﾓｲｯﾁｬｳｩｩｩ!!!ｳｳｳｳｳｳｳｳｳｩｩｩｩｩｩｩｩｳｳｳｳｳｳｳｳ!ｲｨｨｲｨｨｨｲｲｲｨｲｲｲｲ");
        e.getModelRegistry().forEach((n, m) -> {
            if (n.getNamespace().equals(OtyacraftEngine.MODID)) {
                if (!n.getPath().equals("test_model")) return;
                //   UnbakedModel model = e.getModelLoader().getModelOrMissing(new ResourceLocation(OtyacraftEngine.MODID, "block/test_model"));
                //   SpecialModelLoader.TEST_MODEL = model.bake(e.getModelLoader(), ForgeModelBakery.defaultTextureGetter(), BlockModelRotation.X0_Y0, n);
            }
        });*/
    // }
}
