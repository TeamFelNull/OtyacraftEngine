package dev.felnull.otyacraftengine.block;

import dev.architectury.registry.registries.DeferredRegister;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.blockentity.TestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class TestBlock extends HorizontalDirectionalEntityBlock {

    public TestBlock(Properties properties) {
        super(properties);
    }

    public static Block TEST_BLOCK;

    public static void init() {
        DeferredRegister<Block> BLOCK_REG = DeferredRegister.create(OtyacraftEngine.MODID, Registry.BLOCK_REGISTRY);
        DeferredRegister<Item> ITEM_REG = DeferredRegister.create(OtyacraftEngine.MODID, Registry.ITEM_REGISTRY);
        TEST_BLOCK = new TestBlock(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL));
        BLOCK_REG.register("test_block", () -> TEST_BLOCK);
        ITEM_REG.register("test_block", () -> new BlockItem(TEST_BLOCK, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
        ITEM_REG.register();
        BLOCK_REG.register();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TestBlockEntity(blockPos, blockState);
    }

    /*
        @Override
        public RenderShape getRenderShape(BlockState blockState) {
            return RenderShape.MODEL;
        }
    */
    private static final VoxelShape shape = Block.box(3.0D, 10.0D, 0.0D, 13.0D, 16.0D, 16.0D);

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return shape;
    }
}
