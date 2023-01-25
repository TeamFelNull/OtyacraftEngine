package dev.felnull.otyacraftenginetest;

import dev.felnull.otyacraftenginetest.block.TestBlocks;
import dev.felnull.otyacraftenginetest.blockentity.TestBlockEntitys;
import dev.felnull.otyacraftenginetest.handler.CommonHandler;
import dev.felnull.otyacraftenginetest.item.TestItems;
import dev.felnull.otyacraftenginetest.server.handler.ServerHandler;

public class OtyacraftEngineTest {
    public static final String MODID = "otyacraftenginetest";

    public static void init() {
        TestItems.init();
        TestBlocks.init();
        TestBlockEntitys.init();

        CommonHandler.init();
        ServerHandler.init();
    }
}
