package red.felnull.otyacraftengine.proxy;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import red.felnull.otyacraftengine.client.handler.RenderHandler;
import red.felnull.otyacraftengine.handler.ServerHandler;
import red.felnull.otyacraftengine.packet.PacketHandler;
import red.felnull.otyacraftengine.registries.OERegistries;;

public class CommonProxy {
    public void preInit() {
        PacketHandler.init();
    }

    public void init() {
        OERegistries.init();
        MinecraftForge.EVENT_BUS.register(ServerHandler.class);
    }

    public void posInit() {

    }

    public Minecraft getMinecraft() {
        return null;
    }
}
