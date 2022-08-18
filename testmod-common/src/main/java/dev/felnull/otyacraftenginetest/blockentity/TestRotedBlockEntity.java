package dev.felnull.otyacraftenginetest.blockentity;

import dev.felnull.otyacraftengine.blockentity.OEBaseBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TestRotedBlockEntity extends OEBaseBlockEntity {
    private int roted;
    private int rotedOld;
    private int speed;

    public TestRotedBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TestBlockEntitys.TEST_ROTED_BLOCKENTITY.get(), blockPos, blockState);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, TestRotedBlockEntity blockEntity) {
        blockEntity.rotedOld = blockEntity.roted;
        blockEntity.roted += Math.max((int) ((float) blockEntity.speed / 100f), 0);
        blockEntity.speed -= 1;

        blockEntity.baseAfterTick();
    }

    public int getRoted() {
        return roted;
    }

    public int getRotedOld() {
        return rotedOld;
    }

    @Override
    public boolean isSyncUpdate() {
        return true;
    }

    public void addSpeed(int num) {
        speed += num;
        syncToClient();
    }

    @Override
    public void saveToUpdateTag(CompoundTag tag) {
        super.saveToUpdateTag(tag);
        tag.putInt("Speed", speed);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("Roted", roted);
        tag.putInt("Speed", speed);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Roted", Tag.TAG_INT))
            this.roted = tag.getInt("Roted");
        this.speed = tag.getInt("Speed");
    }
}
