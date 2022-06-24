package dev.felnull.otyacraftengine.entity;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import dev.felnull.fnjl.util.FNStringUtil;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.util.OEUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlayerInfoManager {
    private static final PlayerInfoManager INSTANCE = new PlayerInfoManager();
    private static final String NAME_BY_UUID_URL = "https://sessionserver.mojang.com/session/minecraft/profile/%s";
    private static final String UUID_BY_NAME_URL = "https://api.mojang.com/users/profiles/minecraft/%s";
    private final Map<UUID, Optional<String>> NAME_BY_UUID_CACHE = new HashMap<>();
    private final Map<String, Optional<UUID>> UUID_BY_NAME_CACHE = new HashMap<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor(new BasicThreadFactory.Builder().namingPattern(OtyacraftEngine.getModName() + "-Player info loader thread").daemon(true).build());

    public static PlayerInfoManager getInstance() {
        return INSTANCE;
    }

    public void clear() {
        synchronized (NAME_BY_UUID_CACHE) {
            NAME_BY_UUID_CACHE.clear();
        }
        synchronized (UUID_BY_NAME_CACHE) {
            UUID_BY_NAME_CACHE.clear();
        }
    }

    public void clear(@NotNull GameProfile profile) {
        synchronized (NAME_BY_UUID_CACHE) {
            NAME_BY_UUID_CACHE.remove(profile.getId());
        }
        synchronized (UUID_BY_NAME_CACHE) {
            UUID_BY_NAME_CACHE.remove(profile.getName());
        }
    }

    @NotNull
    public Optional<UUID> getUUIDByName(@NotNull String name) {
        return getJson(String.format(UUID_BY_NAME_URL, name)).map(jo -> {
            if (jo.has("id") && jo.get("id").isJsonPrimitive()) {
                var jp = jo.getAsJsonPrimitive("id");
                if (jp.isString()) return FNStringUtil.getUUIDFromNoHyphenStringNonThrow(jp.getAsString());
            }
            return null;
        });
    }

    @NotNull
    public Optional<String> getNameByUUID(@NotNull UUID uuid) {
        return getJson(String.format(NAME_BY_UUID_URL, uuid)).map(jo -> {
            if (jo.has("name") && jo.get("name").isJsonPrimitive()) {
                var jp = jo.getAsJsonPrimitive("name");
                if (jp.isString()) return jp.getAsString();
            }
            return null;
        });
    }

    @NotNull
    private synchronized Optional<JsonObject> getJson(@NotNull String url) {
        try {
            return Optional.ofNullable(OEUtils.getURLJson(new URL(url)));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @NotNull
    public CompletableFuture<Optional<String>> getNameByUUIDAsync(@NotNull UUID uuid) {
        return CompletableFuture.supplyAsync(() -> getNameByUUID(uuid), executorService);
    }

    @NotNull
    public CompletableFuture<Optional<UUID>> getUUIDByNameAsync(@NotNull String name) {
        return CompletableFuture.supplyAsync(() -> getUUIDByName(name), executorService);
    }

    @NotNull
    public Optional<UUID> getUUIDByNameCached(@NotNull String name) {
        synchronized (UUID_BY_NAME_CACHE) {
            return UUID_BY_NAME_CACHE.computeIfAbsent(name, k -> getUUIDByName(name));
        }
    }

    @NotNull
    public Optional<String> getNameByUUIDCached(@NotNull UUID uuid) {
        synchronized (NAME_BY_UUID_CACHE) {
            return NAME_BY_UUID_CACHE.computeIfAbsent(uuid, k -> getNameByUUID(uuid));
        }
    }

    @NotNull
    public CompletableFuture<Optional<String>> getNameByUUIDCachedAsync(@NotNull UUID uuid) {
        synchronized (NAME_BY_UUID_CACHE) {
            if (NAME_BY_UUID_CACHE.containsKey(uuid))
                return CompletableFuture.completedFuture(NAME_BY_UUID_CACHE.get(uuid));
            return CompletableFuture.supplyAsync(() -> getNameByUUIDCached(uuid), executorService);
        }
    }

    @NotNull
    public CompletableFuture<Optional<UUID>> getUUIDByNameCachedAsync(@NotNull String name) {
        synchronized (UUID_BY_NAME_CACHE) {
            if (UUID_BY_NAME_CACHE.containsKey(name))
                return CompletableFuture.completedFuture(UUID_BY_NAME_CACHE.get(name));
            return CompletableFuture.supplyAsync(() -> getUUIDByNameCached(name), executorService);
        }
    }
}
