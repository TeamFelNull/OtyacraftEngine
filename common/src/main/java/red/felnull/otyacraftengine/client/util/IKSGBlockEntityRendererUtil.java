package red.felnull.otyacraftengine.client.util;

import me.shedaniel.architectury.registry.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class IKSGBlockEntityRendererUtil {
    public static <T extends BlockEntity> void registerRenderer(BlockEntityType<T> tileEntityType, BlockEntityRendererProvider<T> provider) {
        BlockEntityRenderers.registerRenderer(tileEntityType, provider);
    }
}
