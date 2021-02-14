package red.felnull.otyacraftengine;

import red.felnull.otyacraftengine.block.TestBlock;
import red.felnull.otyacraftengine.item.TestItem;

public class OtyacraftEngine {
    public static final String MODID = "otyacraftengine";

    public static void init() {
        test();
    }

    private static void test() {
        TestBlock.init();
        TestItem.init();
    }
}
