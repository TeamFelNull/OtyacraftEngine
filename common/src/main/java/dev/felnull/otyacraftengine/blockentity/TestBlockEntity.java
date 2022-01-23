package dev.felnull.otyacraftengine.blockentity;

import dev.architectury.hooks.block.BlockEntityHooks;
import dev.architectury.registry.registries.DeferredRegister;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.block.TestBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TestBlockEntity extends OEBaseBlockEntity {
    public static BlockEntityType<TestBlockEntity> TEST_BLOCKENTITY;
    private float roted;
    private float oldRoted;

    public TestBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TEST_BLOCKENTITY, blockPos, blockState);
    }

    public static void init() {
        DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES_REGISTER = DeferredRegister.create(OtyacraftEngine.MODID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);
        TEST_BLOCKENTITY = BlockEntityHooks.builder(TestBlockEntity::new, TestBlock.TEST_BLOCK).build(null);
        BLOCK_ENTITY_TYPES_REGISTER.register("test_block_entity", () -> TEST_BLOCKENTITY);
        BLOCK_ENTITY_TYPES_REGISTER.register();
    }

    public float getOldRoted() {
        return oldRoted;
    }

    public float getRoted() {
        return roted;
    }

    public void test() {
        sync();
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, TestBlockEntity blockEntity) {
        blockEntity.oldRoted = blockEntity.roted;
        blockEntity.roted += 3;

        if (!level.isClientSide()) {
            blockEntity.sync();
        }
    }

    @Override
    public CompoundTag getSyncData(ServerPlayer player, CompoundTag tag) {
        //  tag.putFloat("roted", roted);
        //   tag.putFloat("oldRoted", oldRoted);
        return tag;
    }

    @Override
    public void onSync(CompoundTag tag) {
        // this.roted = tag.getFloat("roted");
        //   this.oldRoted = tag.getFloat("oldRoted");
    }
}
