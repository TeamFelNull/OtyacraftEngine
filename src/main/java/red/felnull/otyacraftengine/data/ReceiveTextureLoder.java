package red.felnull.otyacraftengine.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.DataSendReceiverManager;
import red.felnull.otyacraftengine.api.ResponseSender;
import red.felnull.otyacraftengine.api.registries.OERegistries;
import red.felnull.otyacraftengine.client.config.ClientConfig;
import red.felnull.otyacraftengine.client.util.ClientUtil;
import red.felnull.otyacraftengine.client.util.TextureUtil;
import red.felnull.otyacraftengine.util.FileLoadHelper;
import red.felnull.otyacraftengine.util.PathUtil;
import red.felnull.otyacraftengine.util.ServerHelper;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ReceiveTextureLoder {
    private static ReceiveTextureLoder INSTANCE;
    public static final Path CASH_PATH = Paths.get("receivetextures");

    public Map<String, String> CLIENT_INDEX_UUID = new HashMap<String, String>();

    public Map<String, ResourceLocation> PICTUER_RECEIVE_LOCATION = new HashMap<String, ResourceLocation>();

    public static ReceiveTextureLoder instance() {
        return INSTANCE;
    }

    public static void init() {
        INSTANCE = new ReceiveTextureLoder();
    }

    @OnlyIn(Dist.CLIENT)
    public String getIndexContainLocation(String string) {
        try {
            if (CASH_PATH.resolve("index.json").toFile().exists()) {
                InputStreamReader reader = new InputStreamReader(new FileInputStream(CASH_PATH.resolve("index.json").toFile()));
                JsonReader jsonReader = new JsonReader(reader);
                Gson gson = new Gson();
                Map<String, String> map = new HashMap<String, String>();
                map.putAll(gson.fromJson(jsonReader, map.getClass()));
                if (map.containsKey(string)) {
                    return map.get(string);
                }
            }
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    public void requestTextuerSend(String indexnaxme, ResourceLocation location, String name) {
        CompoundNBT tag = new CompoundNBT();
        tag.putString("location", location.toString());
        tag.putString("index", indexnaxme);
        ResponseSender.sendToServer(new ResourceLocation(OtyacraftEngine.MODID, "textuerrequest"), 0, name, tag);
    }

    public void requestedTextuerSendServer(String indexnaxme, ServerPlayerEntity player, ResourceLocation location, String name) {
        Path ImagePath = null;
        if (OERegistries.TEXTUER_SEND_PATH.containsKey(location)) {
            ImagePath = PathUtil.getWorldSaveDataPath().resolve(OERegistries.TEXTUER_SEND_PATH.get(location)).resolve(name);
        }
        if (ImagePath != null && ImagePath.toFile().exists()) {
            byte[] data = FileLoadHelper.fileBytesReader(ImagePath);
            String id = DataSendReceiverManager.instance().sendToClient(player, new ResourceLocation(OtyacraftEngine.MODID, "textuerrequest"), UUID.randomUUID().toString(), data);
            CompoundNBT tag = new CompoundNBT();
            tag.putString("index", indexnaxme);
            ResponseSender.sendToClient(player, new ResourceLocation(OtyacraftEngine.MODID, "textuerrequest"), 0, id, tag);
        } else {
            ResponseSender.sendToClient(player, new ResourceLocation(OtyacraftEngine.MODID, "textuerrequest"), 2, indexnaxme, new CompoundNBT());
        }
    }

    private static final ResourceLocation TEXTUER_NOTFINED = new ResourceLocation(OtyacraftEngine.MODID, "textures/gui/textuer_not_find.png");

    @OnlyIn(Dist.CLIENT)
    public void setNotFind(String index) {
        PICTUER_RECEIVE_LOCATION.put(index, TEXTUER_NOTFINED);
    }

    @OnlyIn(Dist.CLIENT)
    public void requestedTextuerReceive(String uuid, String name) {

        if (!CLIENT_INDEX_UUID.containsKey(uuid))
            return;

        String indexname = CLIENT_INDEX_UUID.get(uuid);

        if (!CASH_PATH.resolve("index.json").toFile().exists()) {
            FileLoadHelper.createFolder(CASH_PATH);
            try (Writer writer = new FileWriter(CASH_PATH.resolve("index.json").toFile())) {
                Map<String, String> map = new HashMap<String, String>();
                Gson gsonb = new GsonBuilder().create();
                map.put(indexname, name);
                gsonb.toJson(map, writer);
            } catch (Exception e) {
            }
        } else {
            try {
                InputStreamReader reader = new InputStreamReader(new FileInputStream(CASH_PATH.resolve("index.json").toFile()));
                JsonReader jsonReader = new JsonReader(reader);
                Gson gson = new Gson();
                Map<String, String> map = new HashMap<String, String>();
                map.putAll(gson.fromJson(jsonReader, map.getClass()));
                map.put(indexname, name);
                try (Writer writer = new FileWriter(CASH_PATH.resolve("index.json").toFile())) {
                    Gson gsonb = new GsonBuilder().create();
                    gsonb.toJson(map, writer);
                } catch (Exception e) {
                }
            } catch (Exception e) {
            }
        }
        ResourceLocation inmap = TextureUtil.getPictureImageTexture(FileLoadHelper.fileBytesReader(CASH_PATH.resolve("cash").resolve(name)));
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
            Map<String, String> map = new HashMap<String, String>();
            map.putAll(gson.fromJson(jsonReader, map.getClass()));
            File[] cfiles = ReceiveTextureLoder.CASH_PATH.resolve("cash").toFile().listFiles();
            Arrays.stream(cfiles).filter(n -> !map.containsValue(n.getName())).forEach(n -> FileLoadHelper.deleteFile(n));
        } catch (Exception e) {
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void updateTextuerClient(ResourceLocation location, String name) {

        String WORLDNAME_AND_PATH = ClientUtil.getCurrentWorldName() + ":" + location.toString() + ":" + name;

        if (!CASH_PATH.resolve("index.json").toFile().exists())
            return;

        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(CASH_PATH.resolve("index.json").toFile()));
            JsonReader jsonReader = new JsonReader(reader);
            Gson gson = new Gson();
            Map<String, String> map = new HashMap<String, String>();
            map.putAll(gson.fromJson(jsonReader, map.getClass()));

            if (!map.containsKey(WORLDNAME_AND_PATH) || !ReceiveTextureLoder.CASH_PATH.resolve("cash").resolve(map.get(WORLDNAME_AND_PATH)).toFile().exists()) {
                PICTUER_RECEIVE_LOCATION.remove(WORLDNAME_AND_PATH);
                return;
            }
            FileLoadHelper.deleteFile(ReceiveTextureLoder.CASH_PATH.resolve("cash").resolve(map.get(WORLDNAME_AND_PATH)));

            map.remove(WORLDNAME_AND_PATH);

            try (Writer writer = new FileWriter(CASH_PATH.resolve("index.json").toFile())) {
                Gson gsonb = new GsonBuilder().create();
                gsonb.toJson(map, writer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        PICTUER_RECEIVE_LOCATION.remove(WORLDNAME_AND_PATH);

    }

    public void updateTextuerServer(ResourceLocation location, String name) {
        ServerHelper.getOnlinePlayers().forEach(n -> updateTextuerServer(n, location, name));
    }

    public void updateTextuerServer(ServerPlayerEntity player, ResourceLocation location, String name) {
        CompoundNBT tag = new CompoundNBT();
        tag.putString("name", name);
        ResponseSender.sendToClient(player, new ResourceLocation(OtyacraftEngine.MODID, "textuerrequest"), 1, location.toString(), tag);
    }
}
