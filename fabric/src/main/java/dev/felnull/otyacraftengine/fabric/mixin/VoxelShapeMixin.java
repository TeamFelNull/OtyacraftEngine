package dev.felnull.otyacraftengine.fabric.mixin;

import dev.felnull.otyacraftengine.block.IIkisugiVoxelShape;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Set;

@Mixin(VoxelShape.class)
public class VoxelShapeMixin implements IIkisugiVoxelShape {
    private Set<Edge> EDGES;

    @Override
    public Set<Edge> getEdges() {
        return EDGES;
    }

    @Override
    public void setEdges(Set<Edge> edges) {
        EDGES = edges;
    }
}
