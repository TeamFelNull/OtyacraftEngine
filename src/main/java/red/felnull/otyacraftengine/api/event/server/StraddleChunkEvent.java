package red.felnull.otyacraftengine.api.event.server;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class StraddleChunkEvent extends PlayerEvent {
    private ChunkPos beforePos;
    private ChunkPos afterPos;

    public StraddleChunkEvent(PlayerEntity player, ChunkPos bPos, ChunkPos aPos) {
        super(player);
        this.afterPos = aPos;
        this.beforePos = bPos;
    }

    public ChunkPos getAfterPos() {
        return afterPos;
    }

    public ChunkPos getBeforePos() {
        return beforePos;
    }
}
