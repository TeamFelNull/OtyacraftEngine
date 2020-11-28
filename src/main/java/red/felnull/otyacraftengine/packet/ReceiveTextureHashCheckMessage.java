package red.felnull.otyacraftengine.packet;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ReceiveTextureHashCheckMessage {
    public Map<ResourceLocation, Map<String, Integer>> checkblemap;

    public ReceiveTextureHashCheckMessage(Map<ResourceLocation, Map<String, Integer>> checkblemap) {
        this.checkblemap = checkblemap;
    }

    public static ReceiveTextureHashCheckMessage decodeMessege(PacketBuffer buffer) {
        Map<ResourceLocation, Map<String, Integer>> maps = new HashMap<>();
        CompoundNBT tag = buffer.readCompoundTag();
        tag.keySet().forEach(n -> {
            Map<String, Integer> hmap = new HashMap<>();
            CompoundNBT htag = tag.getCompound(n);
            htag.keySet().forEach(n2 -> hmap.put(n2, htag.getInt(n2)));
            maps.put(new ResourceLocation(n), hmap);
        });
        return new ReceiveTextureHashCheckMessage(maps);
    }

    public static void encodeMessege(ReceiveTextureHashCheckMessage messege, PacketBuffer buffer) {
        CompoundNBT tag = new CompoundNBT();
        messege.checkblemap.forEach((n, m) -> {
            CompoundNBT htag = new CompoundNBT();
            m.forEach((n2, m2) -> htag.putInt(n2, m2));
            tag.put(n.toString(), htag);
        });
        buffer.writeCompoundTag(tag);
    }
}
