package red.felnull.otyacraftengine.data;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import red.felnull.otyacraftengine.api.registries.OERegistries;
import red.felnull.otyacraftengine.util.IKSGFileLoadUtil;
import red.felnull.otyacraftengine.util.IKSGPathUtil;
import red.felnull.otyacraftengine.util.IKSGPlayerUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WorldDataManager {
    public static final Logger LOGGER = LogManager.getLogger(WorldDataManager.class);

    private static WorldDataManager INSTANCE;

    public final Map<ResourceLocation, CompoundNBT> WORLD_DATA = new HashMap<>();
    public final Map<String, Map<ResourceLocation, CompoundNBT>> PLAYER_DATA = new HashMap<>();

    public final Map<ResourceLocation, CompoundNBT> CLIENT_PLAYER_DATA = new HashMap<>();

    public static WorldDataManager instance() {
        return INSTANCE;
    }

    public static void init() {
        INSTANCE = new WorldDataManager();
    }

    /*
        public void sync(MinecraftServer ms) {
            for (String pl : ms.getPlayerList().getOnlinePlayerNames()) {
                ServerPlayerEntity spe = ms.getPlayerList().getPlayerByUsername(pl);
                for (Map.Entry<ResourceLocation, CompoundNBT> ent : PLAYER_DATA.get(IKSGPlayerUtil.getUUID(spe)).entrySet()) {
                    PlayerWorldData data = OERegistries.PLAYER_WORLD_DATA.get(ent.getKey());
                    if (data.isClientSincble()) {
                        PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> spe), new ClientPlayerDataSyncMessage(ent.getKey(), ent.getValue()));
                    }
                }
            }
        }
    */
    public void load(MinecraftServer ms, ServerPlayerEntity player) {
        if (player == null) {
            LOGGER.debug("loading data");
            for (Map.Entry<ResourceLocation, WorldData> rege : OERegistries.WORLD_DATA.entrySet()) {
                CompoundNBT tag = null;
                if (IKSGPathUtil.getWorldSaveDataPath().resolve(rege.getValue().getSavedFolderPath()).toFile().exists()) {
                    tag = IKSGFileLoadUtil.fileNBTReader(IKSGPathUtil.getWorldSaveDataPath().resolve(rege.getValue().getSavedFolderPath()));
                } else {
                    tag = new CompoundNBT();
                }
                WORLD_DATA.put(rege.getKey(), initialNBT(tag, rege.getValue().getInitialNBT(new CompoundNBT())));
            }
        } else {
            LOGGER.debug("loading " + player.getName().getString() + " data");
            if (!PLAYER_DATA.containsKey(IKSGPlayerUtil.getUUID(player))) {
                PLAYER_DATA.put(IKSGPlayerUtil.getUUID(player), new HashMap<>());
            }
            for (Map.Entry<ResourceLocation, PlayerWorldData> rege : OERegistries.PLAYER_WORLD_DATA.entrySet()) {
                CompoundNBT tag = null;
                if (IKSGPathUtil.getWorldSaveDataPath().resolve(rege.getValue().getSavedFolderPath()).resolve(IKSGPlayerUtil.getUUID(player) + ".dat").toFile().exists()) {
                    tag = IKSGFileLoadUtil.fileNBTReader(IKSGPathUtil.getWorldSaveDataPath().resolve(rege.getValue().getSavedFolderPath()).resolve(IKSGPlayerUtil.getUUID(player) + ".dat"));
                } else {
                    tag = new CompoundNBT();
                }
                PLAYER_DATA.get(IKSGPlayerUtil.getUUID(player)).put(rege.getKey(), initialNBT(tag, rege.getValue().getInitialNBT(new CompoundNBT())));
            }
        }
    }

    private CompoundNBT initialNBT(CompoundNBT tag, CompoundNBT intedtag) {

        for (String intags : intedtag.keySet()) {
            if (!tag.contains(intags)) {
                tag.put(intags, Objects.requireNonNull(intedtag.get(intags)));
            } else if (tag.contains(intags, 10) && intedtag.contains(intags, 10)) {
                tag.put(intags, initialNBT(tag.getCompound(intags), intedtag.getCompound(intags)));
            }
        }

        return tag;
    }

    public void save(MinecraftServer ms, ServerPlayerEntity player) {
        if (player == null) {
            LOGGER.debug("saveing data");
            for (Map.Entry<ResourceLocation, CompoundNBT> ent : WORLD_DATA.entrySet()) {
                WorldData data = OERegistries.WORLD_DATA.get(ent.getKey());
                IKSGFileLoadUtil.fileNBTWriter(ent.getValue(), IKSGPathUtil.getWorldSaveDataPath().resolve(data.getSavedFolderPath()));
            }
            for (String pl : ms.getPlayerList().getOnlinePlayerNames()) {
                ServerPlayerEntity spe = ms.getPlayerList().getPlayerByUsername(pl);
                for (Map.Entry<ResourceLocation, CompoundNBT> ent : PLAYER_DATA.get(IKSGPlayerUtil.getUUID(spe)).entrySet()) {
                    PlayerWorldData data = OERegistries.PLAYER_WORLD_DATA.get(ent.getKey());
                    IKSGFileLoadUtil.fileNBTWriter(ent.getValue(), IKSGPathUtil.getWorldSaveDataPath().resolve(data.getSavedFolderPath()).resolve(IKSGPlayerUtil.getUUID(spe) + ".dat"));
                }
            }
        } else {
            LOGGER.debug("saveing " + player.getName().getString() + " data");
            for (Map.Entry<ResourceLocation, CompoundNBT> ent : PLAYER_DATA.get(IKSGPlayerUtil.getUUID(player)).entrySet()) {
                PlayerWorldData data = OERegistries.PLAYER_WORLD_DATA.get(ent.getKey());
                IKSGFileLoadUtil.fileNBTWriter(ent.getValue(), IKSGPathUtil.getWorldSaveDataPath().resolve(data.getSavedFolderPath()).resolve(IKSGPlayerUtil.getUUID(player) + ".dat"));
            }
        }
    }

    public void unload(MinecraftServer ms, ServerPlayerEntity player) {
        if (player == null) {
            LOGGER.debug("unloding data");
            WORLD_DATA.clear();
            PLAYER_DATA.clear();
        } else {
            LOGGER.debug("unloding " + IKSGPlayerUtil.getUserName(player) + " data");
            if (PLAYER_DATA.containsKey(IKSGPlayerUtil.getUUID(player))) {
                PLAYER_DATA.get(IKSGPlayerUtil.getUUID(player)).clear();
            }
        }
    }

    public void unloadClient() {
        CLIENT_PLAYER_DATA.clear();
    }

    public CompoundNBT getPlayerWorldData(PlayerEntity player, ResourceLocation location, boolean isClientSide) {
        return getPlayerData(IKSGPlayerUtil.getUUID(player), location, isClientSide);
    }

    public CompoundNBT getWorldData(ResourceLocation location) {
        return WORLD_DATA.get(location);
    }

    public CompoundNBT getPlayerData(String uuid, ResourceLocation location, boolean isClientSide) {

        if (isClientSide) {
            return CLIENT_PLAYER_DATA.get(location);
        } else {
            if (!PLAYER_DATA.containsKey(uuid)) {
                return null;
            }
            return PLAYER_DATA.get(uuid).get(location);
        }
    }
}
