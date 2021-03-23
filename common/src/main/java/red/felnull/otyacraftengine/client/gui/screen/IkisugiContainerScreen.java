package red.felnull.otyacraftengine.client.gui.screen;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import red.felnull.otyacraftengine.inventory.IkisugiContainerMenu;

public abstract class IkisugiContainerScreen<T extends IkisugiContainerMenu> extends AbstractContainerScreen<T> implements IIkisugibleScreen, IInstructionBEScreen {
    public IkisugiContainerScreen(T abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }

    public BlockEntity getBlockEntity() {
        return getMinecraft().level.getBlockEntity(getBlockPos());
    }

    public BlockPos getBlockPos() {
        return getMenu().getPos();
    }

    @Override
    public BlockEntity getInstructionBlockEntity() {
        return getBlockEntity();
    }
}
