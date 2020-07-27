package red.felnull.otyacraftengine.proxy;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import red.felnull.otyacraftengine.api.DataSendReceiverManager;
import red.felnull.otyacraftengine.api.registries.OERegistries;
import red.felnull.otyacraftengine.data.ReceiveTextureLoder;
import red.felnull.otyacraftengine.data.WorldDataManager;
import red.felnull.otyacraftengine.handler.ServerHandler;
import red.felnull.otyacraftengine.handler.WorldDataHandler;
import red.felnull.otyacraftengine.packet.PacketHandler;

;

public class CommonProxy {
    public void preInit() {
        PacketHandler.init();
        OERegistries.init();
        WorldDataManager.init();
        DataSendReceiverManager.init();
        ReceiveTextureLoder.init();
        MinecraftForge.EVENT_BUS.register(ServerHandler.class);
        MinecraftForge.EVENT_BUS.register(WorldDataHandler.class);
    }

    public void init() {

    }

    public void posInit() {

    }

    public Minecraft getMinecraft() {
        return null;
    }
}
