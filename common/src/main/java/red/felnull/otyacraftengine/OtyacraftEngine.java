package red.felnull.otyacraftengine;

import red.felnull.otyacraftengine.api.OtyacraftEngineAPI;
import red.felnull.otyacraftengine.api.register.OERegistries;
import red.felnull.otyacraftengine.block.TestBlock;
import red.felnull.otyacraftengine.impl.OEExpectPlatform;
import red.felnull.otyacraftengine.item.TestItem;

public class OtyacraftEngine {
    public static final String MODID = "otyacraftengine";

    public static void init() {
        OtyacraftEngineAPI api = new OtyacraftEngineAPI(OEExpectPlatform.getIntegrations());
        OERegistries.init(api);
        test();
    }

    /**
     * OEのテスト用アイテムやブロックの追加等
     * リリースする際は必ず呼ばないようにすること
     *
     * @author MORIMORI0317
     * @since 2.0
     */
    private static void test() {
        TestBlock.init();
        TestItem.init();
    }
}
