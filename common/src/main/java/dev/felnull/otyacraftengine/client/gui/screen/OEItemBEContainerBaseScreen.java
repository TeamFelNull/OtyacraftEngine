package dev.felnull.otyacraftengine.client.gui.screen;

import dev.felnull.otyacraftengine.inventory.OEItemBEBaseMenu;
import dev.felnull.otyacraftengine.item.location.PlayerItemLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class OEItemBEContainerBaseScreen<T extends OEItemBEBaseMenu> extends OEContainerBaseScreen<T> implements IInstructionItemScreen, IInstructionBEScreen {

    public OEItemBEContainerBaseScreen(T abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }

    public BlockEntity getBlockEntity() {
        return mc.level.getBlockEntity(getBlockPos());
    }

    public boolean isBlock() {
        return getMenu().isBlock();
    }

    public ItemStack getItem() {
        return getMenu().getItemStack(mc.player);
    }

    public BlockPos getBlockPos() {
        return getMenu().getPos();
    }

    public PlayerItemLocation getItemLocation() {
        return menu.getLocation();
    }

    @Override
    public void instruction(String name, int num, CompoundTag data) {
        if (isBlock()) {
            IInstructionBEScreen.instructionBlockEntity(this, getBlockEntity(), name, num, data);
        } else {
            IInstructionItemScreen.instructionItem(this, getItem(), getItemLocation(), name, num, data);
        }
    }
}