package dev.felnull.otyacraftengine.client.gui.screen;

import dev.felnull.otyacraftengine.inventory.OEBEBaseMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class OEBEContainerBaseScreen<T extends OEBEBaseMenu> extends OEContainerBaseScreen<T> implements IInstructionBEScreen {

    public OEBEContainerBaseScreen(T abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }

    public BlockEntity getBlockEntity() {
        return mc.level.getBlockEntity(getBlockPos());
    }

    public BlockPos getBlockPos() {
        return getMenu().getPos();
    }

    @Override
    public void instruction(String name, int num, CompoundTag data) {
        IInstructionBEScreen.instructionBlockEntity(this, getBlockEntity(), name, num, data);
    }
}
