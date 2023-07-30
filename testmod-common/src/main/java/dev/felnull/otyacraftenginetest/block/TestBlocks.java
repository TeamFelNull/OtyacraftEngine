package dev.felnull.otyacraftenginetest.block;

import dev.architectury.extensions.injected.InjectedItemPropertiesExtension;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.felnull.otyacraftenginetest.OtyacraftEngineTest;
import dev.felnull.otyacraftenginetest.item.TestCreativeTabs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;
import java.util.function.Supplier;

public class TestBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(OtyacraftEngineTest.MODID, Registries.BLOCK);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(OtyacraftEngineTest.MODID, Registries.ITEM);

    public static final RegistrySupplier<Block> TEST_BLOCK = register("test_block", () -> new Block(BlockBehaviour.Properties.of()));
    public static final RegistrySupplier<Block> TEST_SHAPE_BLOCK = register("test_shape_block", () -> new TestShapeBlock(BlockBehaviour.Properties.of()));
    public static final RegistrySupplier<Block> TEST_SHAPE_DIRECTION_BLOCK = register("test_shape_direction_block", () -> new TestShapeDirectionBlock(BlockBehaviour.Properties.of()));
    public static final RegistrySupplier<Block> TEST_CONTAINER_BLOCK = register("test_container_block", () -> new TestContainerBlock(BlockBehaviour.Properties.of()));
    public static final RegistrySupplier<Block> TEST_ROTED_BLOCK = register("test_roted_block", () -> new TestRotedBlock(BlockBehaviour.Properties.of()));
    public static final RegistrySupplier<Block> TEST_HORIZONTAL_DIRECTIONAL_BLOCK = register("test_horizontal_directional_block", () -> new TestHorizontalDirectionalBlock(BlockBehaviour.Properties.of()));
    public static final RegistrySupplier<SlabBlock> TEST_SLAB_BLOCK = register("test_slab_block", () -> new SlabBlock(BlockBehaviour.Properties.of()));
    public static final RegistrySupplier<TestStairBlock> TEST_STAIRS_BLOCK = register("test_stairs_block", () -> new TestStairBlock(TEST_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(TEST_BLOCK.get())));

    private static <T extends Block> RegistrySupplier<T> register(String name, Supplier<T> block) {
        return register(name, block, n -> new BlockItem(n, tabWrap(new Item.Properties())));
    }

    private static <T extends Block> RegistrySupplier<T> register(String name, Supplier<T> block, Function<T, Item> blockItem) {
        var blockr = BLOCKS.register(name, block);
        BLOCK_ITEMS.register(name, () -> blockItem.apply(blockr.get()));
        return blockr;
    }

    public static void init() {
        BLOCKS.register();
        BLOCK_ITEMS.register();
    }

    private static Item.Properties tabWrap(Item.Properties properties) {
        return ((InjectedItemPropertiesExtension) properties).arch$tab(TestCreativeTabs.TEST_TAB);
    }
}
