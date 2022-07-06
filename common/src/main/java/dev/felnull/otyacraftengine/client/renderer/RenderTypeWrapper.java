package dev.felnull.otyacraftengine.client.renderer;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;

public class RenderTypeWrapper {
    public static RenderType create(String string, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, RenderType.CompositeState state) {
        return RenderType.create(string, format, mode, bufferSize, affectsCrumbling, sortOnUpload, state);
    }
}
