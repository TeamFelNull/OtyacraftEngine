package red.felnull.otyacraftengine.impl;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.fluid.FluidStack;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import red.felnull.otyacraftengine.util.IKSGBiomeUtil;
import red.felnull.otyacraftengine.util.IKSGBlockEntityUtil;

import java.util.List;
import java.util.Optional;

public class OEExpectPlatform {
    @ExpectPlatform
    public static TagCollection<Fluid> getFluidAllTags() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static TagCollection<GameEvent> getGameEventAllTags() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Optional<FluidStack> getFluidContained(ItemStack container) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addOverworldContinentalBiome(ResourceKey<Biome> biome, IKSGBiomeUtil.BiomeType type, double weight) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends BlockEntity> BlockEntityType.Builder<T> craeteBlockEntityTypeBuilder(IKSGBlockEntityUtil.IKSGBlockEntitySupplier<? extends T> blockEntitySupplier, Block... blocks) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isBlockEntity(Block block) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static SoundEvent getEmptySound(FluidStack stack) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean canNotIncompleteFluidItem(ItemStack stack) {
        throw new AssertionError();
    }


    @ExpectPlatform
    public static ItemStack getEmptyFluidItem(ItemStack stack) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int getFluidItemMaxAmont(ItemStack stack) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Optional<ItemStack> getFilledNotIncompleteFluidItem(ItemStack stack, Fluid fluid) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T> List<T> getModEntrypoints(Class<T> type, String key, Class<?> anotation) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Tag.Named<Item> bindItemTag(String name) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Tag.Named<Block> bindBlockTag(String name) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Tag.Named<EntityType<?>> bindEntityTypeTag(String name) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Tag.Named<Fluid> bindFluidTag(String name) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Tag.Named<GameEvent> bindGameEventTag(String name) {
        throw new AssertionError();
    }
}
