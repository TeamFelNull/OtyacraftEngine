package red.felnull.otyacraftengine.proxy;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import red.felnull.otyacraftengine.api.registries.OERegistries;
import red.felnull.otyacraftengine.data.ServerDataBuffer;
import red.felnull.otyacraftengine.handler.ServerHandler;
import red.felnull.otyacraftengine.packet.PacketHandler;

;

public class CommonProxy {
    public void preInit() {
        PacketHandler.init();
    }

    public void init() {
        OERegistries.init();
        ServerDataBuffer.init();
        MinecraftForge.EVENT_BUS.register(ServerHandler.class);

    }

    public void posInit() {

    }

    public Minecraft getMinecraft() {
        return null;
    }
}
