package dev.felnull.otyacraftengine.impl;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import javax.annotation.Nullable;

public class OEPatchouliExpectPlatform {
    @ExpectPlatform
    public static void openBookGUI(ServerPlayer player, ResourceLocation location) {
        throw new AssertionError();
    }


    @Nullable
    @ExpectPlatform
    public static ResourceLocation getOpenBookGui() {
        throw new AssertionError();
    }
}
