package red.felnull.otyacraftengine.client;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import red.felnull.otyacraftengine.api.OtyacraftEngineAPI;
import red.felnull.otyacraftengine.api.register.OERegistries;
import red.felnull.otyacraftengine.block.IIkisugibleBlock;
import red.felnull.otyacraftengine.client.handler.ClientHandler;
import red.felnull.otyacraftengine.client.keys.OEKeyMappings;
import red.felnull.otyacraftengine.client.renderer.blockentity.TestRenderer;
import red.felnull.otyacraftengine.client.renderer.item.TestItemRenderer;
import red.felnull.otyacraftengine.client.util.IKSGClientUtil;

public class OtyacraftEngineClient {
    public static void clientInit() {
        OtyacraftEngineAPI api = OtyacraftEngineAPI.getInstance();
        api.isClient = true;
        ClientHandler.init();
        OERegistries.clientInit(api);
        OEKeyMappings.init(api);

        Registry.BLOCK.stream().filter(n -> n instanceof IIkisugibleBlock).filter(n -> ((IIkisugibleBlock) n).isTransparentRenderLayer()).forEach(n -> IKSGClientUtil.setRenderLayer(n, RenderType.cutout()));

        if (api.isTestMode()) {
            test();
        }
    }

    public static void test() {
        TestRenderer.init();
        TestItemRenderer.init();
    }
}
