package dev.felnull.otyacraftengine.integration;

import dev.architectury.platform.Platform;
import dev.felnull.otyacraftengine.impl.OEPatchouliExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class PatchouliWrapper {
    public static final String MODID = "patchouli";

    public static boolean isIntegrable() {
        return Platform.isModLoaded(MODID);
    }

    public static void openBookGUI(ServerPlayer player, ResourceLocation location) {
        OEPatchouliExpectPlatform.openBookGUI(player, location);
    }

    public static @NotNull ResourceLocation getOpenBookGui() {
        return OEPatchouliExpectPlatform.getOpenBookGui();
    }
}
