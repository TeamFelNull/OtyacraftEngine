package dev.felnull.otyacraftengine.client.gui.screen;

import dev.felnull.otyacraftengine.inventory.OEItemBaseMenu;
import dev.felnull.otyacraftengine.item.location.IPlayerItemLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public abstract class OEItemContainerBaseScreen<T extends OEItemBaseMenu> extends OEContainerBaseScreen<T> implements IInstructionItemScreen {
    public OEItemContainerBaseScreen(T abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }

    public ItemStack getItem() {
        return getMenu().getItemStack(mc.player);
    }

    public IPlayerItemLocation getItemLocation() {
        return menu.getLocation();
    }

    @Override
    public void instruction(String name, int num, CompoundTag data) {
        IInstructionItemScreen.instructionItem(this, getItem(), getItemLocation(), name, num, data);
    }
}
