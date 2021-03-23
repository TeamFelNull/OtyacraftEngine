package red.felnull.otyacraftengine.inventory;

import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

public abstract class IkisugiContainerMenu extends AbstractContainerMenu {
    protected final BlockPos pos;

    protected IkisugiContainerMenu(@Nullable MenuType<?> menuType, int windowId, BlockPos pos) {
        super(menuType, windowId);
        this.pos = pos;
    }

    public BlockPos getPos() {
        return pos;
    }
}
