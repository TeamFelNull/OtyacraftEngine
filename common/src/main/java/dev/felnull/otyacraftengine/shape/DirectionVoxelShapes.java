package dev.felnull.otyacraftengine.shape;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.VoxelShape;

public record DirectionVoxelShapes(VoxelShape north, VoxelShape south, VoxelShape east, VoxelShape west) {
    public VoxelShape getShape(Direction direction) {
        return switch (direction) {
            case SOUTH -> south;
            case EAST -> east;
            case WEST -> west;
            case NORTH -> north;
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        };
    }
}
