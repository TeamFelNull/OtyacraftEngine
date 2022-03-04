package dev.felnull.otyacraftengine;

import dev.felnull.otyacraftengine.block.TestBlock;
import dev.felnull.otyacraftengine.blockentity.TestBlockEntity;
import dev.felnull.otyacraftengine.handler.TestCommonHandler;
import dev.felnull.otyacraftengine.inventory.TestMenu;
import dev.felnull.otyacraftengine.item.TestItem;
import dev.felnull.otyacraftengine.server.handler.TestServerHandler;

public class TestInit {
    public static void init() {
        TestServerHandler.init();
        TestCommonHandler.init();
        TestItem.init();
        TestBlock.init();
        TestBlockEntity.init();
        TestMenu.init();
    }
}
