package net.examplemod.block;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.examplemod.ExampleMod;
import net.examplemod.item.TestCreativeTab;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import java.util.function.Function;
import java.util.function.Supplier;

public class TestBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ExampleMod.MODID, Registry.BLOCK_REGISTRY);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ExampleMod.MODID, Registry.ITEM_REGISTRY);
    public static final RegistrySupplier<Block> TEST_SHAPE_BLOCK = register("test_shape_block", () -> new TestShapeBlock(BlockBehaviour.Properties.of(Material.METAL)));
    public static final RegistrySupplier<Block> TEST_SHAPE_DIRECTION_BLOCK = register("test_shape_direction_block", () -> new TestShapeDirectionBlock(BlockBehaviour.Properties.of(Material.METAL)));
    public static final RegistrySupplier<Block> TEST_CONTAINER_BLOCK = register("test_container_block", () -> new TestContainerBlock(BlockBehaviour.Properties.of(Material.METAL)));

    private static RegistrySupplier<Block> register(String name, Supplier<Block> block) {
        return register(name, block, n -> new BlockItem(n, new Item.Properties().tab(TestCreativeTab.TEST_TAB)));
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
}
