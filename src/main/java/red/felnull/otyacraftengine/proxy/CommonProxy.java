package red.felnull.otyacraftengine.proxy;

import net.minecraft.client.Minecraft;
import red.felnull.otyacraftengine.packet.PacketHandler;;

public class CommonProxy {
    public void preInit() {
        PacketHandler.init();
    }

    public void init() {

    }

    public void posInit() {

    }

    public Minecraft getMinecraft() {
        return null;
    }
}
