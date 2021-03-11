package red.felnull.otyacraftengine.blockentity;

import me.shedaniel.architectury.registry.DeferredRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.block.TestBlock;
import red.felnull.otyacraftengine.util.IKSGBlockEntityUtil;

public class TestBlockEntity extends BlockEntity implements IClientSyncbleBlockEntity {
    public int currentValue;

    public static BlockEntityType<TestBlockEntity> TEST_BLOCKENTITY;

    public static void init() {
        DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES_REGISTER = DeferredRegister.create(OtyacraftEngine.MODID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);
        TEST_BLOCKENTITY = IKSGBlockEntityUtil.craeteBlockEntityType(TestBlockEntity::new, TestBlock.TEST_BLOCK);
        BLOCK_ENTITY_TYPES_REGISTER.register("test_block_entity", () -> TEST_BLOCKENTITY);
        BLOCK_ENTITY_TYPES_REGISTER.register();
    }


    public TestBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TEST_BLOCKENTITY, blockPos, blockState);
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, TestBlockEntity testblockentity) {
        testblockentity.currentValue++;
        if (testblockentity.currentValue > 360)
            testblockentity.currentValue = 0;

        setChanged(level, blockPos, blockState);

        testblockentity.syncble();
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.currentValue = tag.getInt("CVal");
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putInt("CVal", this.currentValue);
        return super.save(tag);
    }
}
