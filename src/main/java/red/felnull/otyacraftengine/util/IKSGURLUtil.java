package red.felnull.otyacraftengine.util;

import dev.felnull.fnjl.util.FNURLUtil;

import java.io.IOException;
import java.net.URL;

public class IKSGURLUtil {
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36";

    public static String getURLResponse(String url) throws IOException {
        return FNURLUtil.getResponse(new URL(url));
    }
}
