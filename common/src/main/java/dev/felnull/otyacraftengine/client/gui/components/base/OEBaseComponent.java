package dev.felnull.otyacraftengine.client.gui.components.base;

import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.gui.TextureRegion;
import dev.felnull.otyacraftengine.client.gui.OEBaseGUI;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface OEBaseComponent extends OEBaseGUI {
    ResourceLocation OE_WIDGETS = new ResourceLocation(OtyacraftEngine.MODID, "textures/gui/widgets.png");

    @NotNull
    TextureRegion getTexture();

    void setTexture(@NotNull TextureRegion texture);

    @Nullable
    String getWidgetTypeName();
}
