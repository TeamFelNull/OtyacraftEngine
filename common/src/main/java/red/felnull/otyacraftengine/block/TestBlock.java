package red.felnull.otyacraftengine.block;

import me.shedaniel.architectury.registry.DeferredRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.blockentity.TestBlockEntity;
import red.felnull.otyacraftengine.item.TestTankBlockItem;
import red.felnull.otyacraftengine.util.IKSGVoxelShapeUtil;

public class TestBlock extends IkisugiBaseEntityBlock {
    private static final VoxelShape BASE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);
    private static final VoxelShape X_LEG1 = Block.box(3.0D, 4.0D, 4.0D, 13.0D, 5.0D, 12.0D);
    private static final VoxelShape X_LEG2 = Block.box(4.0D, 5.0D, 6.0D, 12.0D, 10.0D, 10.0D);
    private static final VoxelShape X_TOP = Block.box(0.0D, 10.0D, 3.0D, 16.0D, 16.0D, 13.0D);
    private static final VoxelShape X_AXIS_AABB = IKSGVoxelShapeUtil.uniteBox(BASE, X_LEG1, X_LEG2, X_TOP);

    public static void init() {
        DeferredRegister<Block> MOD_BLOCKS_REGISTER = DeferredRegister.create(OtyacraftEngine.MODID, Registry.BLOCK_REGISTRY);
        DeferredRegister<Item> MOD_BLOCKITEMS_REGISTER = DeferredRegister.create(OtyacraftEngine.MODID, Registry.ITEM_REGISTRY);
        TEST_BLOCK = new TestBlock(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(1, 10f));
        TEST_TANK_BLOCK = new TestTankBlock(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(1, 10f));

        MOD_BLOCKS_REGISTER.register("test_block", () -> TEST_BLOCK);
        MOD_BLOCKITEMS_REGISTER.register("test_block", () -> new BlockItem(TEST_BLOCK, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

        MOD_BLOCKS_REGISTER.register("test_tank_block", () -> TEST_TANK_BLOCK);
        MOD_BLOCKITEMS_REGISTER.register("test_tank_block", () -> new TestTankBlockItem(TEST_TANK_BLOCK, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

        MOD_BLOCKS_REGISTER.register();
        MOD_BLOCKITEMS_REGISTER.register();

    }

    public TestBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    private static final VoxelShape TEST_AABB = IKSGVoxelShapeUtil.getShapeFromResource(new ResourceLocation(OtyacraftEngine.MODID, "output_shapes"));

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return TEST_AABB;
    }

    public static Block TEST_BLOCK;
    public static Block TEST_TANK_BLOCK;

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TestBlockEntity(blockPos, blockState);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return TestBlockEntity.TEST_BLOCKENTITY;
    }
}
