package red.felnull.otyacraftengine.fluid;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

public class FluidData {
    private final Supplier<Fluid> sourceFluid;
    private final Supplier<FlowingFluid> flowingFluid;
    private final Supplier<Item> bucketItem;
    private final Supplier<Block> liquidBlock;

    public FluidData(Supplier<Fluid> sourceFluid, Supplier<FlowingFluid> flowingFluid, Supplier<Item> bucketItem, Supplier<Block> liquidBlock) {
        this.sourceFluid = sourceFluid;
        this.flowingFluid = flowingFluid;
        this.bucketItem = bucketItem;
        this.liquidBlock = liquidBlock;
    }

    public Fluid getSourceFluid() {
        return sourceFluid.get();
    }

    public FlowingFluid getFlowingFluid() {
        return flowingFluid.get();
    }

    public Item getBucketItem() {
        return bucketItem.get();
    }

    public Block getLiquidBlock() {
        return liquidBlock.get();
    }
}
