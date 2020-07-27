package red.felnull.otyacraftengine.proxy;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import red.felnull.otyacraftengine.client.handler.ClientHandler;
import red.felnull.otyacraftengine.client.handler.RenderHandler;
import red.felnull.otyacraftengine.client.keys.OEKeyBindings;
import red.felnull.otyacraftengine.data.ReceiveTextureLoder;

public class ClientProxy extends CommonProxy {
    public static void clientInit() {
        OEKeyBindings.init();
    }

    @Override
    public void preInit() {
        super.preInit();
        MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, ItemTooltipEvent.class, new ClientHandler()::onToolTip);
        MinecraftForge.EVENT_BUS.register(ClientHandler.class);
        MinecraftForge.EVENT_BUS.register(RenderHandler.class);
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void posInit() {
        super.posInit();
        ReceiveTextureLoder.instance().deleteUnnecessaryCash();
    }

    @Override
    public Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }
}
