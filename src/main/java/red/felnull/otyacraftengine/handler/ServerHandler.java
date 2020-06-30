package red.felnull.otyacraftengine.handler;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import red.felnull.otyacraftengine.data.ServerDataBuffer;

public class ServerHandler {
    @SubscribeEvent
    public static void onServetTick(TickEvent.ServerTickEvent e) {
        ServerDataBuffer.instance().tick();
    }
}
