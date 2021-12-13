package dev.felnull.otyacraftengine.blockentity;

import dev.architectury.hooks.block.BlockEntityHooks;
import dev.architectury.registry.registries.DeferredRegister;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.block.TestBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TestBlockEntity extends OEBaseBlockEntity {

    public static BlockEntityType<TestBlockEntity> TEST_BLOCKENTITY;

    public TestBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TEST_BLOCKENTITY, blockPos, blockState);
    }

    public static void init() {
        DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES_REGISTER = DeferredRegister.create(OtyacraftEngine.MODID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);
        TEST_BLOCKENTITY = BlockEntityHooks.builder(TestBlockEntity::new, TestBlock.TEST_BLOCK).build(null);
        BLOCK_ENTITY_TYPES_REGISTER.register("test_block_entity", () -> TEST_BLOCKENTITY);
        BLOCK_ENTITY_TYPES_REGISTER.register();
    }

    public void test() {
        sync();
    }
}
