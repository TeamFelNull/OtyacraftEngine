package red.felnull.otyacraftengine.api.event;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

public class WorldDataEvent extends Event {
    private final MinecraftServer server;
    private final ServerPlayerEntity player;
    private final boolean isThread;

    public WorldDataEvent(MinecraftServer server, ServerPlayerEntity player, boolean thread) {
        this.server = server;
        this.player = player;
        this.isThread = thread;
    }

    public MinecraftServer getServer() {
        return server;
    }

    public ServerPlayerEntity getPlayer() {
        return player;
    }

    public boolean isThread() {
        return isThread;
    }

    public static class Save extends WorldDataEvent {

        public Save(MinecraftServer server, ServerPlayerEntity player, boolean thread) {
            super(server, player, thread);
        }
    }

    public static class Load extends WorldDataEvent {
        public Load(MinecraftServer server, ServerPlayerEntity player, boolean thread) {
            super(server, player, thread);
        }
    }

    public static class UnLoad extends WorldDataEvent {
        public UnLoad(MinecraftServer server, ServerPlayerEntity player, boolean thread) {
            super(server, player, thread);
        }
    }

    public static void save(MinecraftServer ms, ServerPlayerEntity player, boolean thread) {
        MinecraftForge.EVENT_BUS.post(new WorldDataEvent.Save(ms, player, thread));
    }

    public static void load(MinecraftServer ms, ServerPlayerEntity player, boolean thread) {
        MinecraftForge.EVENT_BUS.post(new WorldDataEvent.Load(ms, player, thread));
    }

    public static void unload(MinecraftServer ms, ServerPlayerEntity player, boolean thread) {
        MinecraftForge.EVENT_BUS.post(new WorldDataEvent.UnLoad(ms, player, thread));
    }

}
