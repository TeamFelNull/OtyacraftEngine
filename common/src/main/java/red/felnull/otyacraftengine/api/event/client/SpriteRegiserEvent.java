package red.felnull.otyacraftengine.api.event.client;

import net.minecraft.resources.ResourceLocation;
import red.felnull.otyacraftengine.api.event.OEEvent;

import java.util.ArrayList;
import java.util.List;

public class SpriteRegiserEvent extends OEEvent {
    private final List<ResourceLocation> REG_TEXTUERS = new ArrayList<>();

    public void registSprite(ResourceLocation location) {
        REG_TEXTUERS.add(location);
    }

    public List<ResourceLocation> getRegistTextuers() {
        return REG_TEXTUERS;
    }
}
