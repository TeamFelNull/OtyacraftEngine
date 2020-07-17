package red.felnull.otyacraftengine.handler;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import red.felnull.otyacraftengine.api.event.WorldDataEvent;
import red.felnull.otyacraftengine.data.ServerDataBuffer;
import red.felnull.otyacraftengine.data.WorldDataManager;

public class ServerHandler {
    @SubscribeEvent
    public static void onServetTick(TickEvent.ServerTickEvent e) {
        ServerDataBuffer.instance().tick();
        WorldDataManager.instance().sync(LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER));
    }

    @SubscribeEvent
    public static void onServerStarting(FMLServerStartingEvent e) {
        WorldDataEvent.load(e.getServer(), null, false);
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent e) {
        if (!e.getPlayer().world.isRemote)
            WorldDataEvent.load(e.getPlayer().getServer(), (ServerPlayerEntity) e.getPlayer(), true);
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent e) {
        if (!e.getPlayer().world.isRemote) {
            WorldDataEvent.save(e.getPlayer().getServer(), (ServerPlayerEntity) e.getPlayer(), true);
            WorldDataEvent.unload(e.getPlayer().getServer(), (ServerPlayerEntity) e.getPlayer(), true);
        }
    }

    @SubscribeEvent
    public static void onServerStopping(FMLServerStoppingEvent e) {
        WorldDataEvent.save(e.getServer(), null, false);
        WorldDataEvent.unload(e.getServer(), null, false);
    }

    @SubscribeEvent
    public static void onWorldSave(WorldEvent.Save e) {
        WorldDataEvent.save(e.getWorld().getWorld().getServer(), null, true);
    }

}
