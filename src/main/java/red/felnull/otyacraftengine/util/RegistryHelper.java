package red.felnull.otyacraftengine.util;

import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

public class RegistryHelper {
    public static void registedTileEntityType(IForgeRegistry<TileEntityType<?>> r, Supplier<? extends TileEntity> factoryIn, TileEntityType<?> te, ResourceLocation location, Block... blocks) {
        te = TileEntityType.Builder.create(factoryIn, blocks).build(null).setRegistryName(location);
        r.register(te);
    }

    public static void registedContainerType(IForgeRegistry<ContainerType<?>> r, ContainerType<?> type, IContainerFactory<?> factory, ResourceLocation location) {
        type = (ContainerType<?>) IForgeContainerType.create(factory).setRegistryName(location);
        r.register(type);
    }
}
