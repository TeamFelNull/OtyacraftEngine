package dev.felnull.otyacraftengine.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.felnull.fnjl.util.FNURLUtil;
import dev.felnull.otyacraftengine.explatform.OEExpectPlatform;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 区別するほどでもないユーティリティ
 *
 * @author MORIMORI0317
 */
public class OEUtils {
    private static final Gson GSON = new Gson();

    /**
     * URLからJsonを取得
     *
     * @param url URL
     * @return Json
     * @throws IOException 例外
     */
    public static JsonObject getURLJson(URL url) throws IOException {
        try (Reader reader = new InputStreamReader(new BufferedInputStream(FNURLUtil.getStream(url)))) {
            return GSON.fromJson(reader, JsonObject.class);
        }
    }

    /**
     * 非同期でJsonを取得
     * 失敗時はnulが来ます
     *
     * @param url URL
     * @return 処理結果
     */
    public static CompletableFuture<JsonObject> getURLJsonAsync(URL url) {
        return FNURLUtil.getStreamAsync(url).thenApplyAsync(ret -> {
            if (ret == null) return null;
            try (Reader reader = new InputStreamReader(new BufferedInputStream(ret))) {
                return GSON.fromJson(reader, JsonObject.class);
            } catch (IOException ex) {
                return null;
            }
        });
    }

    /**
     * Forgeならアノテーションから、FabricならEntryPointを実装しているものを呼び出さす
     *
     * @param name            名前
     * @param annotationClass アノテーション
     * @param interfaceClass  インターフェース
     * @param <T>             型
     * @return 呼び出されているインスタンス一覧
     */
    public static <T> List<T> getCallPoints(@NotNull String name, @NotNull Class<?> annotationClass, @NotNull Class<T> interfaceClass) {
        return OEExpectPlatform.getCallPoints(name, annotationClass, interfaceClass);
    }
}
