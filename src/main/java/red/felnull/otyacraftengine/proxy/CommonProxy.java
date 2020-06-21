package red.felnull.otyacraftengine.proxy;

import net.minecraft.client.Minecraft;
import red.felnull.otyacraftengine.packet.PacketHandler;
import red.felnull.otyacraftengine.registries.OERegistries;;

public class CommonProxy {
    public void preInit() {
        PacketHandler.init();
    }

    public void init() {
        OERegistries.init();
    }

    public void posInit() {

    }

    public Minecraft getMinecraft() {
        return null;
    }
}
