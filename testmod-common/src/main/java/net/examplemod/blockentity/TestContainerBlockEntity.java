package net.examplemod.blockentity;

import dev.felnull.otyacraftengine.blockentity.OEBaseContainerBlockEntity;
import net.examplemod.block.TestBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TestContainerBlockEntity extends OEBaseContainerBlockEntity {
    private final NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);

    public TestContainerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TestBlockEntitys.TEST_CONTAINER_BLOCKENTITY.get(), blockPos, blockState);
    }

    @Override
    protected Component getDefaultName() {
        return TestBlocks.TEST_CONTAINER_BLOCK.get().getName();
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return null;
    }

    @Override
    public @NotNull NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void loadToUpdateTag(CompoundTag tag) {
        System.out.println("ｳｧｧ!!ｵﾚﾓｲｯﾁｬｳｩｩｩ!!!ｳｳｳｳｳｳｳｳｳｩｩｩｩｩｩｩｩｳｳｳｳｳｳｳｳ!ｲｨｨｲｨｨｨｲｲｲｨｲｲｲｲ");
        super.loadToUpdateTag(tag);
    }
}
