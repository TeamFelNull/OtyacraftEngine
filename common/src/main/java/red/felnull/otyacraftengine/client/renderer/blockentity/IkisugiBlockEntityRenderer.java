package red.felnull.otyacraftengine.client.renderer.blockentity;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class IkisugiBlockEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {
    private final BlockEntityRendererProvider.Context context;

    public IkisugiBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    public BlockEntityRendererProvider.Context getContext() {
        return context;
    }
}
