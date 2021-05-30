package red.felnull.otyacraftengine.impl.fabric;

import dev.architectury.fluid.FluidStack;
import net.fabricmc.fabric.api.biome.v1.OverworldClimate;
import net.fabricmc.fabric.impl.biome.InternalBiomeData;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import red.felnull.otyacraftengine.util.IKSGBiomeUtil;
import red.felnull.otyacraftengine.util.IKSGBlockEntityUtil;

import java.util.List;
import java.util.Optional;

public class OEExpectPlatformImpl {
    public static TagCollection<Fluid> getFluidAllTags() {
        return FluidTags.HELPER.getAllTags();
    }

    public static TagCollection<GameEvent> getGameEventAllTags() {
        return GameEventTags.HELPER.getAllTags();
    }

    public static final long BUCKET_VOLUME = 1000;

    public static Optional<FluidStack> getFluidContained(ItemStack container) {
        return Optional.empty();
    }

    public static void addOverworldContinentalBiome(ResourceKey<Biome> biome, IKSGBiomeUtil.BiomeType type, double weight) {
        InternalBiomeData.addOverworldContinentalBiome(getClimate(type), biome, weight);
    }

    private static OverworldClimate getClimate(IKSGBiomeUtil.BiomeType type) {
        switch (type) {
            case SNOWY:
                return OverworldClimate.SNOWY;
            case COOL:
                return OverworldClimate.COOL;
            case TEMPERATE:
                return OverworldClimate.TEMPERATE;
            case DRY:
                return OverworldClimate.DRY;
        }
        return OverworldClimate.TEMPERATE;
    }

    public static <T extends BlockEntity> BlockEntityType.Builder<T> craeteBlockEntityTypeBuilder(IKSGBlockEntityUtil.IKSGBlockEntitySupplier<? extends T> blockEntitySupplier, Block... blocks) {
        return BlockEntityType.Builder.of(blockEntitySupplier::create, blocks);
    }

    public static boolean isBlockEntity(Block block) {
        return block instanceof EntityBlock;
    }

    public static SoundEvent getEmptySound(FluidStack stack) {
        return stack.getFluid().is(FluidTags.LAVA) ? SoundEvents.BUCKET_EMPTY_LAVA : SoundEvents.BUCKET_EMPTY;
    }

    public static boolean canNotIncompleteFluidItem(ItemStack stack) {
        return false;
    }

    public static ItemStack getEmptyFluidItem(ItemStack stack) {
        return ItemStack.EMPTY;
    }

    public static int getFluidItemMaxAmont(ItemStack stack) {
        return 0;
    }

    public static Optional<ItemStack> getFilledNotIncompleteFluidItem(ItemStack stack, Fluid fluid) {
        return Optional.empty();
    }

    public static <T> List<T> getModEntrypoints(Class<T> type, String key, Class<?> anotation) {
        return FabricLoader.getInstance().getEntrypoints(key, type);
    }

    public static Tag.Named<Item> bindItemTag(String name) {
        return ItemTags.bind(name);
    }

    public static Tag.Named<Block> bindBlockTag(String name) {
        return BlockTags.bind(name);
    }

    public static Tag.Named<EntityType<?>> bindEntityTypeTag(String name) {
        return EntityTypeTags.bind(name);
    }

    public static Tag.Named<Fluid> bindFluidTag(String name) {
        return FluidTags.bind(name);
    }

    public static Tag.Named<GameEvent> bindGameEventTag(String name) {
        return GameEventTags.bind(name);
    }
}
