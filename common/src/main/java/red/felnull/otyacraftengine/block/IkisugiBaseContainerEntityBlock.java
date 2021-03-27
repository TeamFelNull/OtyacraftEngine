package red.felnull.otyacraftengine.block;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.Nullable;
import red.felnull.otyacraftengine.blockentity.IkisugiContainerBlockEntity;
import red.felnull.otyacraftengine.util.IKSGItemUtil;

import java.util.List;

public abstract class IkisugiBaseContainerEntityBlock extends IkisugiBaseEntityBlock {
    public static final ResourceLocation CONTENTS = new ResourceLocation("contents");

    protected IkisugiBaseContainerEntityBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        if (itemStack.hasCustomHoverName()) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof IkisugiContainerBlockEntity && blockEntity.getType() == getBlockEntityType()) {
                ((IkisugiContainerBlockEntity) blockEntity).setCustomName(itemStack.getHoverName());
            }
        }
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof IkisugiContainerBlockEntity && blockEntity.getType() == getBlockEntityType()) {
            IkisugiContainerBlockEntity icbe = (IkisugiContainerBlockEntity) blockEntity;
            if (!level.isClientSide) {
                ItemStack itemStack = new ItemStack(blockState.getBlock());
                if (!icbe.isAllEmpty()) {
                    CompoundTag compoundTag = icbe.saveToTag(new CompoundTag());
                    if (!compoundTag.isEmpty()) {
                        itemStack.addTagElement("BlockEntityTag", compoundTag);
                    }
                }
                if (icbe.hasCustomName()) {
                    itemStack.setHoverName(icbe.getCustomName());
                }

                if (!player.isCreative() || !icbe.isAllEmpty()) {
                    IKSGItemUtil.spawnItemEntity(itemStack, level, (double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.5D, (double) blockPos.getZ() + 0.5D);
                }
            }
        }
        super.playerWillDestroy(level, blockPos, blockState, player);
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootContext.Builder builder) {
        BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof IkisugiContainerBlockEntity && blockEntity.getType() == getBlockEntityType()) {
            IkisugiContainerBlockEntity icbe = (IkisugiContainerBlockEntity) blockEntity;
            builder = builder.withDynamicDrop(CONTENTS, (lootContext, consumer) -> {
                for (int i = 0; i < icbe.getContainerSize(); ++i) {
                    consumer.accept(icbe.getItem(i));
                }
            });
        }
        return super.getDrops(blockState, builder);
    }
}
