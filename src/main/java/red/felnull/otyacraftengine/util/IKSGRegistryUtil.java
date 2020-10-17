package red.felnull.otyacraftengine.util;

import com.mojang.datafixers.types.Type;
import net.minecraft.block.Block;
import net.minecraft.block.ComposterBlock;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameRules;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

public class IKSGRegistryUtil {

    public static <T extends ContainerType<?>> T createContainerType(IContainerFactory<?> factory, ResourceLocation location) {
        return (T) IForgeContainerType.create(factory).setRegistryName(location);
    }

    public static <T extends TileEntityType<?>> T craeteTileEntityType(Supplier<? extends TileEntity> factoryIn, ResourceLocation location, Block... blocks) {
        return (T) TileEntityType.Builder.create(factoryIn, blocks).build((Type) null).setRegistryName(location);
    }

    public static void registedTileEntityType(IForgeRegistry<TileEntityType<?>> r, Supplier<? extends TileEntity> factoryIn, TileEntityType<?> te, ResourceLocation location, Block... blocks) {
        te = craeteTileEntityType(factoryIn, location, blocks);
        r.register(te);
    }

    public static void registedContainerType(IForgeRegistry<ContainerType<?>> r, ContainerType<?> type, IContainerFactory<?> factory, ResourceLocation location) {
        type = (ContainerType<?>) IForgeContainerType.create(factory).setRegistryName(location);
        r.register(type);
    }

    public static GameRules.RuleKey<GameRules.BooleanValue> registryGameRule(String name, GameRules.Category category, boolean defalt) {
        return GameRules.func_234903_a_(name, category, GameRules.BooleanValue.create(defalt));
    }

    public static GameRules.RuleKey<GameRules.IntegerValue> registryGameRule(String name, GameRules.Category category, int defalt) {
        return GameRules.func_234903_a_(name, category, GameRules.IntegerValue.create(defalt));
    }

    public static void registerCompostable(float cont, IItemProvider item) {
        ComposterBlock.registerCompostable(cont, item);
    }
}
