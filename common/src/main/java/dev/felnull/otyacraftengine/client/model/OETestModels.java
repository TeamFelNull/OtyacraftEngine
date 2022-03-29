package dev.felnull.otyacraftengine.client.model;

import dev.felnull.otyacraftengine.OtyacraftEngine;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class OETestModels {
    public static final ResourceLocation ORIGIN = new ResourceLocation(OtyacraftEngine.MODID, "debug/origin");
    public static final ResourceLocation XYZ_AXIS = new ResourceLocation(OtyacraftEngine.MODID, "debug/xyz_axis");

    public static void init(Consumer<ResourceLocation> add) {
        add.accept(ORIGIN);
        add.accept(XYZ_AXIS);
    }
}
