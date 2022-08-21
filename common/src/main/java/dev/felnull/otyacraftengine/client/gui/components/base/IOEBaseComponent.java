package dev.felnull.otyacraftengine.client.gui.components.base;

import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.gui.IOEBaseGUI;
import net.minecraft.resources.ResourceLocation;

public interface IOEBaseComponent extends IOEBaseGUI {
    ResourceLocation WIDGETS = new ResourceLocation(OtyacraftEngine.MODID, "textures/gui/widgets.png");
}
