package red.felnull.otyacraftengine.api.event;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;
import red.felnull.otyacraftengine.data.SendReceiveLogger;

public class ReceiverEvent extends Event {
    private final String uuid;
    private final ResourceLocation location;
    private final String name;

    public ReceiverEvent(String uuid, ResourceLocation location, String name) {
        this.uuid = uuid;
        this.location = location;
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public ResourceLocation getLocation() {
        return location;
    }

    public static class Client extends ReceiverEvent {
        public Client(String uuid, ResourceLocation location, String name) {
            super(uuid, location, name);
        }

        public static class Pre extends Client {
            public Pre(String uuid, ResourceLocation location, String name) {
                super(uuid, location, name);
            }
        }

        public static class Pos extends Client {
            private final SendReceiveLogger.Result result;

            public Pos(String uuid, ResourceLocation location, String name, SendReceiveLogger.Result result) {
                super(uuid, location, name);
                this.result = result;
            }
        }
    }

    public static class Server extends ReceiverEvent {
        private final ServerPlayerEntity player;

        public Server(ServerPlayerEntity player, String uuid, ResourceLocation location, String name) {
            super(uuid, location, name);
            this.player = player;
        }

        public ServerPlayerEntity getPlayer() {
            return player;
        }

        public static class Pre extends Server {
            public Pre(ServerPlayerEntity player, String uuid, ResourceLocation location, String name) {
                super(player, uuid, location, name);
            }
        }

        public static class Pos extends Server {
            private final SendReceiveLogger.Result result;

            public Pos(ServerPlayerEntity player, String uuid, ResourceLocation location, String name, SendReceiveLogger.Result result) {
                super(player, uuid, location, name);
                this.result = result;
            }
        }
    }
}
