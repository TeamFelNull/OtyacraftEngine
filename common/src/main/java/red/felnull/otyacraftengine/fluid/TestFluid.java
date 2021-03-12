package red.felnull.otyacraftengine.fluid;

import me.shedaniel.architectury.registry.DeferredRegister;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.block.OELiquidBlock;

public class TestFluid extends OEFluid {
    public static FlowingFluid TEST_FLUID;
    public static FlowingFluid TEST_FLOWING_FLUID;
    public static Item TEST_FLUID_BUCKET;
    public static Block TEST_FLUID_BLOCK;

    public TestFluid(boolean source) {
        super(() -> TEST_FLUID,() -> TEST_FLOWING_FLUID, () ->TEST_FLUID_BUCKET,() -> TEST_FLUID_BLOCK, source);
    }

    public static void init() {
        DeferredRegister<Fluid> MOD_FLUID_REGISTER = DeferredRegister.create(OtyacraftEngine.MODID, Registry.FLUID_REGISTRY);
        DeferredRegister<Item> MOD_ITEMS_REGISTER = DeferredRegister.create(OtyacraftEngine.MODID, Registry.ITEM_REGISTRY);
        DeferredRegister<Block> MOD_BLOCKS_REGISTER = DeferredRegister.create(OtyacraftEngine.MODID, Registry.BLOCK_REGISTRY);
        TEST_FLUID = new TestFluid(true);
        TEST_FLOWING_FLUID = new TestFluid(false);
        TEST_FLUID_BUCKET = new BucketItem(TEST_FLUID, new Item.Properties().tab(CreativeModeTab.TAB_MISC).craftRemainder(Items.BUCKET).stacksTo(1));
        TEST_FLUID_BLOCK = new OELiquidBlock(TEST_FLUID, BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).noDrops().noCollission().strength(1, 10f));
        MOD_FLUID_REGISTER.register("test_fluid", () -> TEST_FLUID);
        MOD_FLUID_REGISTER.register("test_flowing_fluid", () -> TEST_FLOWING_FLUID);
        MOD_FLUID_REGISTER.register();
        MOD_ITEMS_REGISTER.register("test_fluid_bucket", () -> TEST_FLUID_BUCKET);
        MOD_ITEMS_REGISTER.register();
        MOD_BLOCKS_REGISTER.register("test_fluid_block", () -> TEST_FLUID_BLOCK);
        MOD_BLOCKS_REGISTER.register();
    }
}
