package dev.felnull.otyacraftengine.blockentity;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.block.TestBlock;
import dev.felnull.otyacraftengine.inventory.TestMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TestBlockEntity extends OEBaseContainerBlockEntity {
    private static final NonNullList<ItemStack> ITEMS = NonNullList.withSize(0, ItemStack.EMPTY);
    public static RegistrySupplier<BlockEntityType<TestBlockEntity>> TEST_BLOCKENTITY;
    private float roted;
    private float oldRoted;

    public TestBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TEST_BLOCKENTITY.get(), blockPos, blockState);
    }

    public static void init() {
        DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES_REGISTER = DeferredRegister.create(OtyacraftEngine.MODID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);
        TEST_BLOCKENTITY = BLOCK_ENTITY_TYPES_REGISTER.register("test_block_entity", () -> BlockEntityType.Builder.of(TestBlockEntity::new, TestBlock.TEST_BLOCK.get()).build(null));
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

    @Override
    public @NotNull NonNullList<ItemStack> getItems() {
        return ITEMS;
    }

    @Override
    protected Component getDefaultName() {
        return TestBlock.TEST_BLOCK.get().getName();
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory) {
        return new TestMenu(i, inventory, this, getBlockPos());
    }
}
