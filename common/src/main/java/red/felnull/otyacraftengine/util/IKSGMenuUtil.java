package red.felnull.otyacraftengine.util;

import dev.architectury.registry.menu.MenuRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class IKSGMenuUtil {
    public static void openMenu(ServerPlayer player, MenuProvider provider, BlockPos pos, int inventorySize) {
        MenuRegistry.openExtendedMenu(player, provider, n -> {
            n.writeBlockPos(pos);
            n.writeInt(inventorySize);
        });
    }

    public static <T extends AbstractContainerMenu> MenuType<T> createMenuType(IKSGMenuFactory<T> factory) {
        return MenuRegistry.ofExtended((id, inventory, buf) -> factory.create(id, inventory, buf.readBlockPos(), new SimpleContainer(buf.readInt())));
    }

    public interface IKSGMenuFactory<T extends AbstractContainerMenu> {
        T create(int id, Inventory inventory, BlockPos pos, Container container);
    }
}
