package net.examplemod;

import net.examplemod.block.TestBlocks;
import net.examplemod.blockentity.TestBlockEntitys;
import net.examplemod.item.TestItems;
import net.examplemod.server.handler.ServerHandler;

public class ExampleMod {
    public static final String MODID = "examplemod";

    public static void init() {
        TestItems.init();
        TestBlocks.init();
        TestBlockEntitys.init();
        ServerHandler.init();
    }
}
