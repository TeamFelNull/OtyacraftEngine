package dev.felnull.otyacraftengine.mixin;

import dev.felnull.otyacraftengine.shape.IkisugiVoxelShape;
import dev.felnull.otyacraftengine.shape.VoxelEntry;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(VoxelShape.class)
public class VoxelShapeMixin implements IkisugiVoxelShape {
    private VoxelEntry[] renderEdges;

    @Override
    public VoxelEntry[] getRenderEdges() {
        return renderEdges;
    }

    @Override
    public void setRenderEdges(VoxelEntry... edges) {
        renderEdges = edges;
    }
}