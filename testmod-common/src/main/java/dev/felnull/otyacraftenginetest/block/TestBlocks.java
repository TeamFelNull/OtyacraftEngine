package dev.felnull.otyacraftenginetest.block;

import dev.architectury.extensions.injected.InjectedItemPropertiesExtension;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.felnull.otyacraftenginetest.OtyacraftEngineTest;
import dev.felnull.otyacraftenginetest.item.TestCreativeTab;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import java.util.function.Function;
import java.util.function.Supplier;

public class TestBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(OtyacraftEngineTest.MODID, Registries.BLOCK);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(OtyacraftEngineTest.MODID, Registries.ITEM);

    public static final RegistrySupplier<Block> TEST_BLOCK = register("test_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL)));
    public static final RegistrySupplier<Block> TEST_SHAPE_BLOCK = register("test_shape_block", () -> new TestShapeBlock(BlockBehaviour.Properties.of(Material.METAL)));
    public static final RegistrySupplier<Block> TEST_SHAPE_DIRECTION_BLOCK = register("test_shape_direction_block", () -> new TestShapeDirectionBlock(BlockBehaviour.Properties.of(Material.METAL)));
    public static final RegistrySupplier<Block> TEST_CONTAINER_BLOCK = register("test_container_block", () -> new TestContainerBlock(BlockBehaviour.Properties.of(Material.METAL)));
    public static final RegistrySupplier<Block> TEST_ROTED_BLOCK = register("test_roted_block", () -> new TestRotedBlock(BlockBehaviour.Properties.of(Material.METAL)));

    private static RegistrySupplier<Block> register(String name, Supplier<Block> block) {
        return register(name, block, n -> new BlockItem(n, tabWrap(new Item.Properties())));
    }

    private static RegistrySupplier<Block> register(String name, Supplier<Block> block, Function<Block, Item> blockItem) {
        var blockr = BLOCKS.register(name, block);
        BLOCK_ITEMS.register(name, () -> blockItem.apply(blockr.get()));
        return blockr;
    }

    public static void init() {
        BLOCKS.register();
        BLOCK_ITEMS.register();
    }

    private static Item.Properties tabWrap(Item.Properties properties) {
        return ((InjectedItemPropertiesExtension) properties).arch$tab(TestCreativeTab.TEST_TAB);
    }
}
