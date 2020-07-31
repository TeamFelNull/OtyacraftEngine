package red.felnull.otyacraftengine.handler;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.event.common.ResponseEvent;
import red.felnull.otyacraftengine.api.event.server.WorldDataEvent;
import red.felnull.otyacraftengine.data.ReceiveTextureLoder;
import red.felnull.otyacraftengine.data.ServerDataSendReservation;
import red.felnull.otyacraftengine.data.ServerDataSender;
import red.felnull.otyacraftengine.util.PlayerHelper;

public class ServerHandler {
    private static final ResourceLocation SERVER_RESPONSE = new ResourceLocation(OtyacraftEngine.MODID, "server_response");

    @SubscribeEvent
    public static void onServetTick(TickEvent.ServerTickEvent e) {
        //   WorldDataManager.instance().sync(ServerHelper.getMinecraftServer());
        ServerDataSendReservation.tick();
    }

    @SubscribeEvent
    public static void onServerStarting(FMLServerStartingEvent e) {
        WorldDataEvent.load(e.getServer(), null, false);
        ServerDataSender.srlogsGziping();
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

    @SubscribeEvent
    public static void onClientResponse(ResponseEvent.Client e) {
        if (e.getLocation().equals(SERVER_RESPONSE)) {
            if (e.getId() == 0) {
                ServerDataSender.response(PlayerHelper.getUUID(e.getPlayer()), e.getMessage());
            }
        } else if (e.getLocation().equals(new ResourceLocation(OtyacraftEngine.MODID, "textuerrequest"))) {
            if (e.getId() == 0) {
                ReceiveTextureLoder.instance().requestedTextuerSendServer(e.getData().getString("index"), e.getPlayer(), new ResourceLocation(e.getData().getString("location")), e.getMessage());
            }
        }
    }
}
