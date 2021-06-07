package red.felnull.otyacraftengine.api.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class SimpleMessageEvent extends OEEvent {
    private final ResourceLocation location;
    private final int id;
    private final CompoundTag data;

    public SimpleMessageEvent(ResourceLocation location, int id, CompoundTag data) {
        this.location = location;
        this.id = id;
        this.data = data;
    }

    public CompoundTag getData() {
        return data;
    }

    public int getId() {
        return id;
    }

    public ResourceLocation getLocation() {
        return location;
    }

    public static class Client extends SimpleMessageEvent {

        public Client(ResourceLocation location, int id, CompoundTag data) {
            super(location, id, data);
        }
    }

    public static class Server extends SimpleMessageEvent {
        private final ServerPlayer player;

        public Server(ServerPlayer player, ResourceLocation location, int id, CompoundTag data) {
            super(location, id, data);
            this.player = player;
        }

        public ServerPlayer getPlayer() {
            return player;
        }
    }
}
