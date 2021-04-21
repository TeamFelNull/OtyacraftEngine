package red.felnull.otyacraftengine.api.event;

import net.fabricmc.api.EnvType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.storage.ServerLevelData;

import java.util.Collections;
import java.util.List;

public class OEEventHooks {
    public static void onWorldLoad(LevelAccessor level) {
        OEEventBus.post(new WorldEvent.Load(level));
    }

    public static void onWorldUnload(LevelAccessor level) {
        OEEventBus.post(new WorldEvent.Unload(level));
    }

    public static void onWorldSave(LevelAccessor level) {
        OEEventBus.post(new WorldEvent.Save(level));
    }

    public static List<MobSpawnSettings.SpawnerData> getPotentialSpawns(LevelAccessor level, MobCategory type, BlockPos pos, List<MobSpawnSettings.SpawnerData> oldList) {
        WorldEvent.PotentialSpawns event = new WorldEvent.PotentialSpawns(level, type, pos, oldList);
        if (OEEventBus.post(event))
            return Collections.emptyList();
        return event.getList();
    }

    public static boolean onCreateWorldSpawn(Level level, ServerLevelData settings) {
        return OEEventBus.post(new WorldEvent.CreateSpawnPosition(level, settings));
    }


    public static void onPlayerPreTick(Player player) {
        OEEventBus.post(new TickEvent.PlayerTickEvent(TickEvent.Phase.START, player));
    }

    public static void onPlayerPostTick(Player player) {
        OEEventBus.post(new TickEvent.PlayerTickEvent(TickEvent.Phase.END, player));
    }

    public static void onPreWorldTick(Level level) {
        OEEventBus.post(new TickEvent.WorldTickEvent(EnvType.SERVER, TickEvent.Phase.START, level));
    }

    public static void onPostWorldTick(Level level) {
        OEEventBus.post(new TickEvent.WorldTickEvent(EnvType.SERVER, TickEvent.Phase.END, level));
    }

    public static void onPreServerTick() {
        OEEventBus.post(new TickEvent.ServerTickEvent(TickEvent.Phase.START));
    }

    public static void onPostServerTick() {
        OEEventBus.post(new TickEvent.ServerTickEvent(TickEvent.Phase.END));
    }

}
