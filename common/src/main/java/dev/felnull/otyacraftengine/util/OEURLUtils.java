package dev.felnull.otyacraftengine.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.felnull.fnjl.util.FNURLUtil;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class OEURLUtils {
    private static final Gson GSON = new Gson();

    public static JsonObject getJson(URL url) throws IOException {
        return GSON.fromJson(new InputStreamReader(FNURLUtil.getStream(url), StandardCharsets.UTF_8), JsonObject.class);
    }

    public static CompletableFuture<Void> getJsonAsync(URL url, Consumer<JsonObject> jsonConsumer) {
        return FNURLUtil.getStreamAsync(url, n -> {
            try {
                jsonConsumer.accept(n == null ? null : GSON.fromJson(new InputStreamReader(FNURLUtil.getStream(url), StandardCharsets.UTF_8), JsonObject.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
