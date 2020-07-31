package red.felnull.otyacraftengine.data;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.registries.OERegistries;
import red.felnull.otyacraftengine.util.FileLoadHelper;
import red.felnull.otyacraftengine.util.PathUtil;
import red.felnull.otyacraftengine.util.PlayerHelper;

import java.util.HashMap;
import java.util.Map;

public class WorldDataManager {
    private static WorldDataManager INSTANCE;

    public Map<ResourceLocation, CompoundNBT> WORLD_DATA = new HashMap<ResourceLocation, CompoundNBT>();
    public Map<String, Map<ResourceLocation, CompoundNBT>> PLAYER_DATA = new HashMap<String, Map<ResourceLocation, CompoundNBT>>();

    public Map<ResourceLocation, CompoundNBT> CLIENT_PLAYER_DATA = new HashMap<ResourceLocation, CompoundNBT>();

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
                for (Map.Entry<ResourceLocation, CompoundNBT> ent : PLAYER_DATA.get(PlayerHelper.getUUID(spe)).entrySet()) {
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
            OtyacraftEngine.LOGGER.info("loading data");
            for (Map.Entry<ResourceLocation, WorldData> rege : OERegistries.WORLD_DATA.entrySet()) {
                CompoundNBT tag = null;
                if (PathUtil.getWorldSaveDataPath().resolve(rege.getValue().getSavedFolderPath()).toFile().exists()) {
                    tag = FileLoadHelper.fileNBTReader(PathUtil.getWorldSaveDataPath().resolve(rege.getValue().getSavedFolderPath()));
                } else {
                    tag = new CompoundNBT();
                }
                WORLD_DATA.put(rege.getKey(), initialNBT(tag, rege.getValue().getInitialNBT(new CompoundNBT())));
            }
        } else {
            OtyacraftEngine.LOGGER.info("loading " + player.getName().getString() + " data");
            if (!PLAYER_DATA.containsKey(PlayerHelper.getUUID(player))) {
                PLAYER_DATA.put(PlayerHelper.getUUID(player), new HashMap<ResourceLocation, CompoundNBT>());
            }
            for (Map.Entry<ResourceLocation, PlayerWorldData> rege : OERegistries.PLAYER_WORLD_DATA.entrySet()) {
                CompoundNBT tag = null;
                if (PathUtil.getWorldSaveDataPath().resolve(rege.getValue().getSavedFolderPath()).resolve(PlayerHelper.getUUID(player) + ".dat").toFile().exists()) {
                    tag = FileLoadHelper.fileNBTReader(PathUtil.getWorldSaveDataPath().resolve(rege.getValue().getSavedFolderPath()).resolve(PlayerHelper.getUUID(player) + ".dat"));
                } else {
                    tag = new CompoundNBT();
                }
                PLAYER_DATA.get(PlayerHelper.getUUID(player)).put(rege.getKey(), initialNBT(tag, rege.getValue().getInitialNBT(new CompoundNBT())));
            }
        }
    }

    private CompoundNBT initialNBT(CompoundNBT tag, CompoundNBT intedtag) {

        for (String intags : intedtag.keySet()) {
            if (!tag.contains(intags)) {
                tag.put(intags, intedtag.get(intags));
            } else if (tag.contains(intags, 10) && intedtag.contains(intags, 10)) {
                tag.put(intags, initialNBT(tag.getCompound(intags), intedtag.getCompound(intags)));
            }
        }

        return tag;
    }

    public void save(MinecraftServer ms, ServerPlayerEntity player) {
        if (player == null) {
            OtyacraftEngine.LOGGER.info("saveing data");
            for (Map.Entry<ResourceLocation, CompoundNBT> ent : WORLD_DATA.entrySet()) {
                WorldData data = OERegistries.WORLD_DATA.get(ent.getKey());
                FileLoadHelper.fileNBTWriter(ent.getValue(), PathUtil.getWorldSaveDataPath().resolve(data.getSavedFolderPath()));
            }
            for (String pl : ms.getPlayerList().getOnlinePlayerNames()) {
                ServerPlayerEntity spe = ms.getPlayerList().getPlayerByUsername(pl);
                for (Map.Entry<ResourceLocation, CompoundNBT> ent : PLAYER_DATA.get(PlayerHelper.getUUID(spe)).entrySet()) {
                    PlayerWorldData data = OERegistries.PLAYER_WORLD_DATA.get(ent.getKey());
                    FileLoadHelper.fileNBTWriter(ent.getValue(), PathUtil.getWorldSaveDataPath().resolve(data.getSavedFolderPath()).resolve(PlayerHelper.getUUID(spe) + ".dat"));
                }
            }
        } else {
            OtyacraftEngine.LOGGER.info("saveing " + player.getName().getString() + " data");
            for (Map.Entry<ResourceLocation, CompoundNBT> ent : PLAYER_DATA.get(PlayerHelper.getUUID(player)).entrySet()) {
                PlayerWorldData data = OERegistries.PLAYER_WORLD_DATA.get(ent.getKey());
                FileLoadHelper.fileNBTWriter(ent.getValue(), PathUtil.getWorldSaveDataPath().resolve(data.getSavedFolderPath()).resolve(PlayerHelper.getUUID(player) + ".dat"));
            }
        }
    }

    public void unload(MinecraftServer ms, ServerPlayerEntity player) {
        if (player == null) {
            OtyacraftEngine.LOGGER.info("unloding data");
            WORLD_DATA.clear();
            PLAYER_DATA.clear();
        } else {
            OtyacraftEngine.LOGGER.info("unloding " + PlayerHelper.getUserName(player) + " data");
            if (PLAYER_DATA.containsKey(PlayerHelper.getUUID(player))) {
                PLAYER_DATA.get(PlayerHelper.getUUID(player)).clear();
            }
        }
    }

    public void unloadClient() {
        CLIENT_PLAYER_DATA.clear();
    }

    public CompoundNBT getPlayerWorldData(PlayerEntity player, ResourceLocation location, boolean isClientSide) {
        return getPlayerData(PlayerHelper.getUUID(player), location, isClientSide);
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
