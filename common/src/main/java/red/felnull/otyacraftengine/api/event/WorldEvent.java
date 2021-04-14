package red.felnull.otyacraftengine.api.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.storage.ServerLevelData;

import java.util.ArrayList;
import java.util.List;

public class WorldEvent extends OEEvent {
    private final LevelAccessor world;

    public WorldEvent(LevelAccessor world) {
        this.world = world;
    }

    public LevelAccessor getWorld() {
        return world;
    }

    public static class Load extends WorldEvent {
        public Load(LevelAccessor world) {
            super(world);
        }
    }

    public static class Unload extends WorldEvent {
        public Unload(LevelAccessor world) {
            super(world);
        }
    }

    public static class Save extends WorldEvent {
        public Save(LevelAccessor world) {
            super(world);
        }
    }

    public static class PotentialSpawns extends WorldEvent {
        private final MobCategory type;
        private final BlockPos pos;
        private final List<MobSpawnSettings.SpawnerData> list;

        public PotentialSpawns(LevelAccessor world, MobCategory type, BlockPos pos, List<MobSpawnSettings.SpawnerData> oldList) {
            super(world);
            this.pos = pos;
            this.type = type;
            if (oldList != null)
                this.list = new ArrayList<>(oldList);
            else
                this.list = new ArrayList<>();
        }

        public MobCategory getType() {
            return type;
        }

        public BlockPos getPos() {
            return pos;
        }

        public List<MobSpawnSettings.SpawnerData> getList() {
            return list;
        }
    }

    public static class CreateSpawnPosition extends WorldEvent {
        private final ServerLevelData settings;

        public CreateSpawnPosition(LevelAccessor world, ServerLevelData settings) {
            super(world);
            this.settings = settings;
        }

        public ServerLevelData getSettings() {
            return settings;
        }
    }
}
