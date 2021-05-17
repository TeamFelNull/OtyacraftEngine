package red.felnull.otyacraftengine.client.gui.components;

import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;
import java.util.function.Supplier;

public interface IIkisugiSubtitle {
    void overrideRefresh(Component component, Supplier<Vec3> vec3,long time);

    UUID getID();

    void setID(UUID id);

    void setTime(long time);

    void setDynamicLocation(Supplier<Vec3> location);
}
