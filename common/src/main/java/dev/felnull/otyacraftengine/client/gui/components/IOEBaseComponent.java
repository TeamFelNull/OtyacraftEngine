package dev.felnull.otyacraftengine.client.gui.components;

import dev.felnull.otyacraftengine.OtyacraftEngine;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public interface IOEBaseComponent {
    Minecraft mc = Minecraft.getInstance();
    ResourceLocation WIDGETS = new ResourceLocation(OtyacraftEngine.MODID, "textures/gui/widgets.png");
}
