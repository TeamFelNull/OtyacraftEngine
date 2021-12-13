package dev.felnull.otyacraftengine.client.gui.subtitle;

import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

public interface IDynamicSubtitle {
    void setDynamicLocation(Supplier<Vec3> location);

    Supplier<Vec3> getDynamicLocation();
}
