package dev.felnull.otyacraftengine.impl.fabric;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;
import vazkii.patchouli.api.PatchouliAPI;

public class OEPatchouliExpectPlatformImpl {
    public static void openBookGUI(ServerPlayer player, ResourceLocation location) {
        PatchouliAPI.get().openBookGUI(player, location);
    }

    @Nullable
    public static ResourceLocation getOpenBookGui() {
        return PatchouliAPI.get().getOpenBookGui();
    }
}
