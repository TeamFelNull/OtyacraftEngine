package red.felnull.otyacraftengine.client.data;

import net.minecraft.util.ResourceLocation;
import red.felnull.otyacraftengine.util.IKSGPathUtil;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class URLImageTextureLoder {
    public static final Path CASH_PATH = IKSGPathUtil.getOEURLImageTexturesPath();
    private static URLImageTextureLoder INSTANCE;

    public final Map<String, ResourceLocation> PICTUER_URL_LOCATION = new HashMap<>();

    public static URLImageTextureLoder instance() {
        return INSTANCE;
    }

    public static void init() {
        INSTANCE = new URLImageTextureLoder();
    }
}
