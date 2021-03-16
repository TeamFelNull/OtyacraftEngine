package red.felnull.otyacraftengine.impl.fabric;

import me.shedaniel.architectury.fluid.FluidStack;
import me.shedaniel.architectury.utils.Fraction;
import net.fabricmc.fabric.api.biome.v1.OverworldClimate;
import net.fabricmc.fabric.impl.biome.InternalBiomeData;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.TagCollection;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.IOEIntegration;
import red.felnull.otyacraftengine.util.IKSGBiomeUtil;
import red.felnull.otyacraftengine.util.IKSGBlockEntityUtil;

import java.util.List;
import java.util.Optional;

public class OEExpectPlatformImpl {
    public static List<IOEIntegration> getIntegrations() {
        return FabricLoader.getInstance().getEntrypoints(OtyacraftEngine.MODID, IOEIntegration.class);
    }

    public static TagCollection<Fluid> getFluidAllTags() {
        return FluidTags.HELPER.getAllTags();
    }

    public static TagCollection<GameEvent> getGameEventAllTags() {
        return GameEventTags.HELPER.getAllTags();
    }

    public static final Fraction BUCKET_VOLUME = Fraction.ofWhole(1000);

    public static Optional<FluidStack> getFluidContained(ItemStack container) {
        Item item = container.getItem();
        if (item instanceof BucketItem) {
            return Optional.of(FluidStack.create(((BucketItem) item).content, BUCKET_VOLUME));
        }
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
}
