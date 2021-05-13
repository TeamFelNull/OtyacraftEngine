package red.felnull.otyacraftengine.client.gui.components;

import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;
import java.util.function.Supplier;

public interface IIkisugiSubtitle {
    void overrideRefresh(Component component, Vec3 vec3);

    void overrideRefresh(Component component, Supplier<Vec3> vec3);

    UUID getID();

    void setID(UUID id);

    Supplier<Vec3> getDynamicLocation();

    void setDynamicLocation(Supplier<Vec3> location);
}
