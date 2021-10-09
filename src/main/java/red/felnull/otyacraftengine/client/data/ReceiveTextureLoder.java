package red.felnull.otyacraftengine.client.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.ResponseSender;
import red.felnull.otyacraftengine.client.config.ClientConfig;
import red.felnull.otyacraftengine.client.util.IKSGClientUtil;
import red.felnull.otyacraftengine.client.util.IKSGTextureUtil;
import red.felnull.otyacraftengine.packet.PacketHandler;
import red.felnull.otyacraftengine.packet.ReceiveTextureHashCheckMessage;
import red.felnull.otyacraftengine.util.IKSGFileLoadUtil;
import red.felnull.otyacraftengine.util.IKSGPathUtil;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class ReceiveTextureLoder {
    public static final Path CASH_PATH = IKSGPathUtil.getOEReceiveTexturesPath();

    private static ReceiveTextureLoder INSTANCE;
    public final Map<String, String> INDEX_UUID = new HashMap<>();
    public final Map<String, ResourceLocation> PICTUER_RECEIVE_LOCATION = new HashMap<>();
    private final Map<String, String> INDEX = new HashMap<>();
    public final Map<ResourceLocation, Map<String, Long>> LAST_UPDATE = new HashMap<>();

    @OnlyIn(Dist.CLIENT)

    public static void init() {
        INSTANCE = new ReceiveTextureLoder();
        Timer timer = new Timer();
        TimerTask hashCheckRegularlyTask = new TimerTask() {
            public void run() {
                instance().hashCheckRegularly(false);
            }
        };
        timer.scheduleAtFixedRate(hashCheckRegularlyTask, 0, 10 * 1000);
    }

    public static ReceiveTextureLoder instance() {
        return INSTANCE;
    }

    @OnlyIn(Dist.CLIENT)
    public void requestTextuerSend(String indexnaxme, ResourceLocation location, String name) {
        CompoundNBT tag = new CompoundNBT();
        tag.putString("location", location.toString());
        tag.putString("index", indexnaxme);
        ResponseSender.sendToServer(new ResourceLocation(OtyacraftEngine.MODID, "textuerrequest"), 0, name, tag);
    }


    @OnlyIn(Dist.CLIENT)
    public String getIndexContainLocation(String string) {
        if (INDEX.containsKey(string))
            return INDEX.get(string);
        return null;
    }

    @OnlyIn(Dist.CLIENT)
    public void setNotFind(String index) {
        PICTUER_RECEIVE_LOCATION.put(index, IKSGTextureUtil.TEXTUER_NOTFINED);
    }

    @OnlyIn(Dist.CLIENT)
    public void requestedTextuerReceive(String uuid, String name) {
        if (!INDEX_UUID.containsKey(uuid))
            return;
        String indexname = INDEX_UUID.get(uuid);
        INDEX.put(indexname, name);
        ResourceLocation inmap = IKSGTextureUtil.getPictureImageTexture(IKSGFileLoadUtil.fileBytesReader(CASH_PATH.resolve("cash").resolve(name)));
        PICTUER_RECEIVE_LOCATION.put(indexname, inmap);
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
    public void updateTextuerClient(ResourceLocation location, String name) {
        String WORLDNAME_AND_PATH = IKSGClientUtil.getCurrentWorldUUID() + ":" + location.toString() + ":" + name;
        if (!CASH_PATH.resolve("index.json").toFile().exists())
            return;
        if (!INDEX.containsKey(WORLDNAME_AND_PATH) || !CASH_PATH.resolve("cash").resolve(INDEX.get(WORLDNAME_AND_PATH)).toFile().exists()) {
            PICTUER_RECEIVE_LOCATION.remove(WORLDNAME_AND_PATH);
            return;
        }
        IKSGFileLoadUtil.deleteFile(CASH_PATH.resolve("cash").resolve(INDEX.get(WORLDNAME_AND_PATH)));
        INDEX.remove(WORLDNAME_AND_PATH);
        PICTUER_RECEIVE_LOCATION.remove(WORLDNAME_AND_PATH);
    }

    @OnlyIn(Dist.CLIENT)
    public void writeClientIndex() {
        File index = CASH_PATH.resolve("index.json").toFile();
        IKSGFileLoadUtil.createFolder(CASH_PATH);
        try (Writer writer = new FileWriter(index)) {
            Gson gsonb = new GsonBuilder().create();
            gsonb.toJson(INDEX, writer);
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
                INDEX.putAll(gson.fromJson(jsonReader, INDEX.getClass()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void hashCheckRegularly(boolean all) {
        if (OtyacraftEngine.proxy.getMinecraft().player != null) {
            Map<ResourceLocation, Map<String, Integer>> checkblemap = new HashMap<>();
            if (!all) {
                PICTUER_RECEIVE_LOCATION.entrySet().stream().filter((n) -> n.getKey().split(":")[0].equals(IKSGClientUtil.getCurrentWorldUUID().toString())).filter(n -> {
                            String[] str = n.getKey().split(":");
                            ResourceLocation location = new ResourceLocation(str[1], str[2]);
                            return isRecentlyUpdateble(location, str[3]);
                        }
                ).forEach((n) -> {
                   try {
                       String[] str = n.getKey().split(":");
                       ResourceLocation location = new ResourceLocation(str[1], str[2]);
                       if (!checkblemap.containsKey(location))
                           checkblemap.put(location, new HashMap<>());
                       int sha = 0;
                       if (!n.getValue().equals(IKSGTextureUtil.TEXTUER_NOTFINED)) {
                           File texFile = CASH_PATH.resolve("cash").resolve(INDEX.get(n.getKey())).toFile();
                           if (texFile.exists()) {
                               try {
                                   sha = IKSGFileLoadUtil.getCheckSum(texFile);
                               } catch (Exception ex) {
                                   ex.printStackTrace();
                               }
                           }
                       }
                       checkblemap.get(location).put(str[3], sha);
                   }catch (Exception ignored){
                   }
                });
            } else {
                INDEX.entrySet().stream().filter((n) -> n.getKey().split(":")[0].equals(IKSGClientUtil.getCurrentWorldUUID().toString())).forEach((n) -> {
                    String[] str = n.getKey().split(":");
                    ResourceLocation location = new ResourceLocation(str[1], str[2]);
                    if (!checkblemap.containsKey(location))
                        checkblemap.put(location, new HashMap<>());
                    int sha = 0;
                    File texFile = CASH_PATH.resolve("cash").resolve(n.getValue()).toFile();
                    if (texFile.exists())
                        sha = texFile.hashCode();
                    checkblemap.get(location).put(str[3], sha);
                });
            }
            PacketHandler.INSTANCE.sendToServer(new ReceiveTextureHashCheckMessage(checkblemap));
        }
    }

    public void updateLastTextuerTime(ResourceLocation location, String name) {
        if (!LAST_UPDATE.containsKey(location))
            LAST_UPDATE.put(location, new HashMap<>());
        LAST_UPDATE.get(location).put(name, System.currentTimeMillis());
    }

    public boolean isRecentlyUpdateble(ResourceLocation location, String name) {
        if (LAST_UPDATE.containsKey(location) && LAST_UPDATE.get(location).containsKey(name))
            return System.currentTimeMillis() - LAST_UPDATE.get(location).get(name) < 3 * 60 * 1000;
        return false;
    }
}
