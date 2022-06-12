package dev.felnull.otyacraftengine.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.felnull.fnjl.util.FNURLUtil;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * URL関係のユーティリティ
 *
 * @author MORIMORI0317
 */
public class OEURLUtil {
    private static final Gson GSON = new Gson();

    /**
     * URLからJsonを取得
     *
     * @param url URL
     * @return Json
     * @throws IOException 例外
     */
    public static JsonObject getJson(URL url) throws IOException {
        try (Reader reader = new InputStreamReader(new BufferedInputStream(FNURLUtil.getStream(url)))) {
            return GSON.fromJson(reader, JsonObject.class);
        }
    }

    /**
     * 非同期でJsonを取得
     *
     * @param url          URL
     * @param jsonConsumer Json
     * @return 処理結果
     */
    public static CompletableFuture<Void> getJsonAsync(URL url, Consumer<JsonObject> jsonConsumer) {
        return FNURLUtil.getStreamAsync(url, n -> {
            if (n == null) {
                jsonConsumer.accept(null);
            } else {
                try (Reader reader = new InputStreamReader(new BufferedInputStream(n))) {
                    jsonConsumer.accept(GSON.fromJson(reader, JsonObject.class));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
