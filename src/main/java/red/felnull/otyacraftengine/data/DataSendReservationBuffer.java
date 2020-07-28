package red.felnull.otyacraftengine.data;

import net.minecraft.util.ResourceLocation;

public class DataSendReservationBuffer {
    public ResourceLocation location;
    public String name;
    public byte[] data;

    public DataSendReservationBuffer(ResourceLocation location, String name, byte[] data) {
        this.location = location;
        this.name = name;
        this.data = data;
    }
}
