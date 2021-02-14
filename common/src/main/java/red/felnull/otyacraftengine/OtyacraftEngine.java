package red.felnull.otyacraftengine;

import red.felnull.otyacraftengine.block.TestBlock;

public class OtyacraftEngine {
    public static final String MODID = "otyacraftengine";

    public static void init() {
        test();
    }

    private static void test() {
        TestBlock.init();
    }
}
