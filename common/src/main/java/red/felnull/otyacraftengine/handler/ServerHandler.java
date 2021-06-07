package red.felnull.otyacraftengine.handler;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.event.SimpleMessageEvent;
import red.felnull.otyacraftengine.api.event.WorldEvent;
import red.felnull.otyacraftengine.data.WorldDataManager;
import red.felnull.otyacraftengine.data.WorldShareTextureManager;

public class ServerHandler {
    public static void onLoad(WorldEvent.Load e) {
        if (!e.getWorld().isClientSide()) {
            WorldDataManager.getInstance().load(e.getWorld().getServer());
        }
    }

    public static void onUnload(WorldEvent.Unload e) {
        if (!e.getWorld().isClientSide()) {
            WorldDataManager.getInstance().unload();
        }
    }

    public static void onSave(WorldEvent.Save e) {
        if (!e.getWorld().isClientSide()) {
            WorldDataManager.getInstance().save(e.getWorld().getServer());
        }
    }

    public static void onSimpleMessage(SimpleMessageEvent.Server e) {
        if (e.getLocation().equals(new ResourceLocation(OtyacraftEngine.MODID, "worldshareupload"))) {
            WorldShareTextureManager manager = WorldShareTextureManager.getInstance();
            CompoundTag tag = e.getData();
            if (e.getId() == 0) {
                manager.start(e.getPlayer().getGameProfile().getId(), tag.getUUID("Id"), tag.getInt("Length"));
            } else if (e.getId() == 1) {
                manager.receiveBuffStart(e.getPlayer().getGameProfile().getId(), tag.getUUID("Id"), tag.getInt("Num"), tag.getByteArray("Hash"));
            }
        }
    }
}
