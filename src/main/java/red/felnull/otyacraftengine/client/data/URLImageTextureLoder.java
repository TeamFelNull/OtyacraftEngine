package red.felnull.otyacraftengine.client.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import dev.felnull.fnjl.util.FNDataUtil;
import dev.felnull.fnjl.util.FNURLUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import red.felnull.otyacraftengine.client.config.ClientConfig;
import red.felnull.otyacraftengine.client.util.IKSGTextureUtil;
import red.felnull.otyacraftengine.util.IKSGFileLoadUtil;
import red.felnull.otyacraftengine.util.IKSGPathUtil;
import red.felnull.otyacraftengine.util.IKSGPictuerUtil;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class URLImageTextureLoder {

    public static final Path CASH_PATH = IKSGPathUtil.getOEURLImageTexturesPath();
    private static URLImageTextureLoder INSTANCE;
    public final Map<URLImageData, ResourceLocation> PICTUER_URL_LOCATION = new HashMap<>();
    private final Map<URLImageData, String> INDEX = new HashMap<>();

    public static URLImageTextureLoder instance() {
        return INSTANCE;
    }

    public static void init() {
        INSTANCE = new URLImageTextureLoder();
    }

    @OnlyIn(Dist.CLIENT)
    public void downloadTextuer(URLImageData urldata) {
        TextuerDownloadThread tdt = new TextuerDownloadThread(urldata);
        tdt.start();
    }

    @OnlyIn(Dist.CLIENT)
    public String getIndexContainLocation(URLImageData string) {
        if (INDEX.containsKey(string))
            return INDEX.get(string);
        return null;
    }

    @OnlyIn(Dist.CLIENT)
    public void writeClientIndex() {
        File index = CASH_PATH.resolve("index.json").toFile();
        IKSGFileLoadUtil.createFolder(CASH_PATH);
        try (Writer writer = new FileWriter(index)) {
            Gson gsonb = new GsonBuilder().create();
            JsonObject jo = new JsonObject();
            INDEX.forEach((n, m) -> {
                jo.add(m, n.toJsonObject());
            });
            gsonb.toJson(jo, writer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void deleteUnnecessaryCash() {
        if (!ClientConfig.DeleteUnnecessaryTextureCash.get() || !CASH_PATH.resolve("index.json").toFile().exists())
            return;
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(CASH_PATH.resolve("index.json").toFile()));
            JsonReader jsonReader = new JsonReader(reader);
            Gson gson = new Gson();
            Map<String, String> map = new HashMap<>(gson.fromJson(jsonReader, HashMap.class));
            File[] cfiles = CASH_PATH.resolve("cash").toFile().listFiles();
            Arrays.stream(Objects.requireNonNull(cfiles)).filter(n -> !map.containsValue(n.getName())).forEach(IKSGFileLoadUtil::deleteFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void readClientIndex() {
        File index = CASH_PATH.resolve("index.json").toFile();
        if (index.exists()) {
            try {
                InputStreamReader reader = new InputStreamReader(new FileInputStream(index));
                JsonReader jsonReader = new JsonReader(reader);
                Gson gson = new Gson();
                INDEX.clear();
                JsonObject jo = gson.fromJson(jsonReader, JsonObject.class);
                jo.entrySet().forEach(n -> {
                    if (n.getValue() instanceof JsonObject)
                        INDEX.put(URLImageData.ofJsonObject((JsonObject) n.getValue()), n.getKey());
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static class URLImageData {
        private final String URL;
        private final int width;
        private final int height;

        public URLImageData(String URL, int width, int height) {
            this.URL = URL;
            this.width = width;
            this.height = height;
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

        public String getURL() {
            return URL;
        }

        public boolean isDecidedSuze() {
            return width > 0 && height > 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            URLImageData that = (URLImageData) o;
            return width == that.width && height == that.height && Objects.equals(URL, that.URL);
        }

        @Override
        public int hashCode() {
            return Objects.hash(URL, width, height);
        }

        public JsonObject toJsonObject() {
            JsonObject jo = new JsonObject();
            jo.addProperty("url", URL);
            jo.addProperty("width", width);
            jo.addProperty("height", height);
            return jo;
        }

        public static URLImageData ofJsonObject(JsonObject json) {
            return new URLImageData(json.get("url").getAsString(), json.get("width").getAsInt(), json.get("height").getAsInt());
        }
    }

    public static class TextuerDownloadThread extends Thread {
        private final URLImageData urldata;

        public TextuerDownloadThread(URLImageData urldata) {
            this.urldata = urldata;
        }

        @Override
        public void run() {
            String name = UUID.randomUUID().toString();
            Path texPath = CASH_PATH.resolve("cash").resolve(name);
            try {
                byte[] data = FNDataUtil.streamToByteArray(FNURLUtil.getStream(new URL(urldata.getURL())));
                if (urldata.isDecidedSuze())
                    data = IKSGPictuerUtil.resize(data, urldata.getWidth(), urldata.getHeight());
                IKSGFileLoadUtil.createFolder(texPath.getParent());
                Files.write(texPath, data);
            } catch (Exception e) {
                e.printStackTrace();
                URLImageTextureLoder.instance().PICTUER_URL_LOCATION.put(urldata, IKSGTextureUtil.TEXTUER_NOTFINED);
                return;
            }
            URLImageTextureLoder.instance().INDEX.put(urldata, name);
            ResourceLocation inmap = IKSGTextureUtil.getPictureImageTexture(IKSGFileLoadUtil.fileBytesReader(texPath));
            URLImageTextureLoder.instance().PICTUER_URL_LOCATION.put(urldata, inmap);
        }
    }
}
