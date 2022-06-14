package dev.felnull.otyacraftengine.shape.bundle;

import net.minecraft.world.phys.shapes.VoxelShape;

public interface VoxelShapeBundle<T> {
    VoxelShape getShape(T value);
}
