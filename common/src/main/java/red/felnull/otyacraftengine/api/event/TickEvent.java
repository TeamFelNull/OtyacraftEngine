package red.felnull.otyacraftengine.api.event;

import net.fabricmc.api.EnvType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class TickEvent extends OEEvent {
    public enum Type {
        WORLD, PLAYER, CLIENT, SERVER, RENDER;
    }

    public enum Phase {
        START, END;
    }

    public final Type type;
    public final EnvType side;
    public final Phase phase;

    public TickEvent(Type type, EnvType side, Phase phase) {
        this.type = type;
        this.side = side;
        this.phase = phase;
    }

    public static class ServerTickEvent extends TickEvent {
        public ServerTickEvent(Phase phase) {
            super(Type.SERVER, EnvType.SERVER, phase);
        }
    }

    public static class ClientTickEvent extends TickEvent {
        public ClientTickEvent(Phase phase) {
            super(Type.CLIENT, EnvType.CLIENT, phase);
        }
    }

    public static class WorldTickEvent extends TickEvent {
        public final Level level;

        public WorldTickEvent(EnvType side, Phase phase, Level level) {
            super(Type.WORLD, side, phase);
            this.level = level;
        }
    }

    public static class PlayerTickEvent extends TickEvent {
        public final Player player;

        public PlayerTickEvent(Phase phase, Player player) {
            super(Type.PLAYER, player instanceof ServerPlayer ? EnvType.SERVER : EnvType.CLIENT, phase);
            this.player = player;
        }
    }

    public static class RenderTickEvent extends TickEvent {
        public final float renderTickTime;

        public RenderTickEvent(Phase phase, float renderTickTime) {
            super(Type.RENDER, EnvType.CLIENT, phase);
            this.renderTickTime = renderTickTime;
        }
    }
}
