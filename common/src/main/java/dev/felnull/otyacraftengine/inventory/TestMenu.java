package dev.felnull.otyacraftengine.inventory;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.util.OEMenuUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;

public class TestMenu extends OEBEBaseMenu {
    public static RegistrySupplier<MenuType<TestMenu>> TEST_MENU;

    public static void init() {
        var MENUS = DeferredRegister.create(OtyacraftEngine.MODID, Registry.MENU_REGISTRY);
        TEST_MENU = MENUS.register("test_menu", () -> OEMenuUtil.createMenuType((i, inventory, blockPos, container) -> new TestMenu(i, inventory, container, blockPos)));
        MENUS.register();
    }

    public TestMenu(int windowId, Inventory playerInventory, Container container, BlockPos pos) {
        super(TEST_MENU.get(), windowId, playerInventory, container, pos, 8, 93);
    }

    @Override
    protected void setSlot() {

    }
}
