package dev.felnull.otyacraftengine.client.loader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.felnull.fnjl.util.FNStringUtil;
import dev.felnull.fnjl.util.FNURLUtil;
import dev.felnull.otyacraftengine.util.OEPlayerUtil;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class PlayerInfoManager {
    private static final PlayerInfoManager INSTANCE = new PlayerInfoManager();
    private static final Gson GSON = new Gson();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Map<UUID, String> NAME_BY_UUID_CASH = new HashMap<>();
    private final List<UUID> LOADING_NAME_BY_UUID = new ArrayList<>();
    private final Map<String, UUID> UUID_BY_NAME_CASH = new HashMap<>();
    private final List<String> LOADING_UUID_BY_NAME = new ArrayList<>();

    public static PlayerInfoManager getInstance() {
        return INSTANCE;
    }

    public Optional<UUID> getUUIDByName(@NotNull String name) {
        if (UUID_BY_NAME_CASH.containsKey(name))
            return Optional.ofNullable(UUID_BY_NAME_CASH.get(name));
        if (LOADING_UUID_BY_NAME.contains(name))
            return Optional.empty();
        LOADING_UUID_BY_NAME.add(name);
        load(String.format(OEPlayerUtil.UUID_BY_NAME_URL, name), n -> {
            if (n != null) {
                UUID uuid = null;
                try {
                    JsonObject jo = GSON.fromJson(n, JsonObject.class);
                    uuid = FNStringUtil.fromNoHyphenStringToUUID(jo.get("id").getAsString());
                } catch (Exception ignored) {
                }
                UUID_BY_NAME_CASH.put(name, uuid);
            } else {
                UUID_BY_NAME_CASH.put(name, null);
            }
            LOADING_UUID_BY_NAME.remove(name);
        });
        return Optional.empty();
    }

    public Optional<String> getNameByUUID(@NotNull UUID uuid) {
        if (NAME_BY_UUID_CASH.containsKey(uuid))
            return Optional.ofNullable(NAME_BY_UUID_CASH.get(uuid));
        if (LOADING_NAME_BY_UUID.contains(uuid))
            return Optional.empty();
        LOADING_NAME_BY_UUID.add(uuid);
        load(String.format(OEPlayerUtil.NAME_BY_UUID_URL, uuid), n -> {
            if (n != null) {
                String name = null;
                try {
                    JsonObject jo = GSON.fromJson(n, JsonObject.class);
                    name = jo.get("name").getAsString();
                } catch (Exception ignored) {
                }
                NAME_BY_UUID_CASH.put(uuid, name);
            } else {
                NAME_BY_UUID_CASH.put(uuid, null);
            }
            LOADING_NAME_BY_UUID.remove(uuid);
        });
        return Optional.empty();
    }

    public Future<String> load(String url, Consumer<String> ret) {
        return executorService.submit(() -> {
            String res = null;
            try {
                res = FNURLUtil.getResponse(new URL(url));
            } catch (Exception ignored) {
            }
            ret.accept(res);
            return res;
        });
    }

    public void reload() {
        executorService.shutdown();
        executorService.shutdownNow();

        NAME_BY_UUID_CASH.clear();
        LOADING_NAME_BY_UUID.clear();
        UUID_BY_NAME_CASH.clear();
        LOADING_UUID_BY_NAME.clear();

        executorService = Executors.newSingleThreadExecutor();
    }
    //429
    //java.io.IOException: Server returned HTTP response code: 429 for URL:
}
