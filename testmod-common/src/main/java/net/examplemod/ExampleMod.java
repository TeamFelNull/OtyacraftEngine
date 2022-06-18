package net.examplemod;

import net.examplemod.block.TestBlocks;
import net.examplemod.item.TestItems;

public class ExampleMod {
    public static final String MODID = "examplemod";

    public static void init() {
        TestItems.init();
        TestBlocks.init();
    }
}
