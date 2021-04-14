package red.felnull.otyacraftengine.fabric.mixin;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.WorldData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import red.felnull.otyacraftengine.api.event.OEEventHooks;

import java.util.Map;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Shadow
    @Final
    protected WorldData worldData;

    @Shadow
    @Final
    private Map<ResourceKey<Level>, ServerLevel> levels;

    @Shadow
    public abstract Iterable<ServerLevel> getAllLevels();

    @Inject(method = "createLevels", at = @At("TAIL"))
    private void createLevels(ChunkProgressListener chunkProgressListener, CallbackInfo ci) {
        WorldGenSettings worldGenSettings = worldData.worldGenSettings();
        for (Map.Entry<ResourceKey<LevelStem>, LevelStem> entry : worldGenSettings.dimensions().entrySet()) {
            ResourceKey<LevelStem> registrykey = entry.getKey();
            OEEventHooks.onWorldLoad(levels.get(registrykey));
        }
    }

    @Inject(method = "stopServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;getAllLevels()Ljava/lang/Iterable;", ordinal = 1))
    private void stopServer(CallbackInfo ci) {
        for (ServerLevel serverworld1 : this.getAllLevels()) {
            if (serverworld1 != null) {
                OEEventHooks.onWorldUnload(serverworld1);
            }
        }
    }
}
