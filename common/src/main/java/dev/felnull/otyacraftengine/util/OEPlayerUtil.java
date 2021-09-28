package dev.felnull.otyacraftengine.util;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class OEPlayerUtil {
    private static final String UUID_PLAYER_URL = "https://sessionserver.mojang.com/session/minecraft/profile/%s";

    @Nullable
    public static String getNameByUUID(UUID uuid) {
        try {
            JsonObject jo = OEURLUtils.getJson(new URL(String.format(UUID_PLAYER_URL, uuid.toString())));
            return jo.get("name").getAsString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public static CompletableFuture<Void> getNameByUUIDAsync(UUID id, Consumer<String> name) {
        try {
            return OEURLUtils.getJsonAsync(new URL(String.format(UUID_PLAYER_URL, id.toString())), n -> {
                String na = null;
                try {
                    na = n.get("name").getAsString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                name.accept(na);
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(null);
    }

}
