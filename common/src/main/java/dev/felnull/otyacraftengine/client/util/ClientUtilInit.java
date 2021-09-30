package dev.felnull.otyacraftengine.client.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.felnull.otyacraftengine.util.OEPaths;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientUtilInit {
    private static final Gson GSON = new Gson();

    public static void init() {
        OEPaths.getClientOEFolderPath().resolve("url_textures").toFile().mkdirs();
        File urlSaveIndex = OEPaths.getClientOEFolderPath().resolve("url_textures_index.json").toFile();
        if (urlSaveIndex.exists()) {
            try {
                JsonObject jo = GSON.fromJson(new FileReader(urlSaveIndex), JsonObject.class);
                jo.entrySet().forEach((n) -> {
                    File file = OEPaths.getClientOEFolderPath().resolve("url_textures").resolve(n.getValue().getAsString()).toFile();
                    if (file.exists())
                        OETextureUtil.URL_FILENAME_INDEX.put(n.getKey(), n.getValue().getAsString());
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void clear() {
        OEClientUtil.PLAYER_PROFILES.clear();
        List<UUID> ids = new ArrayList<>(OETextureUtil.NATIVE_TEXTURES.keySet());
        for (UUID id : ids) {
            OETextureUtil.freeNativeTexture(id);
        }

        OETextureUtil.URL_TEXTURES_UUIDS.clear();

        File urlSaveIndex = OEPaths.getClientOEFolderPath().resolve("url_textures_index.json").toFile();
        JsonObject index = new JsonObject();
        OETextureUtil.URL_FILENAME_INDEX.forEach((n, m) -> {
            File file = OEPaths.getClientOEFolderPath().resolve("url_textures").resolve(m).toFile();
            if (file.exists())
                index.addProperty(n, m);
        });
        try {
            OEPaths.getClientOEFolderPath().resolve("url_textures").toFile().mkdirs();
            Files.writeString(urlSaveIndex.toPath(), index.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
