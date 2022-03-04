package dev.felnull.otyacraftengine.client.gui.components.base;

import dev.felnull.otyacraftengine.client.gui.TextureSpecifyLocation;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class OEBaseImageWidget extends OEBaseWidget {
    @NotNull
    protected TextureSpecifyLocation texture;

    public OEBaseImageWidget(int x, int y, int width, int height, @NotNull Component message, @NotNull TextureSpecifyLocation texture) {
        this(x, y, width, height, null, message, texture);
    }

    public OEBaseImageWidget(int x, int y, int width, int height, @Nullable String widgetTypeName, @NotNull Component message, @NotNull TextureSpecifyLocation texture) {
        super(x, y, width, height, widgetTypeName, message);
        this.texture = texture;
    }

    public @NotNull TextureSpecifyLocation getTexture() {
        return texture;
    }

    public void setTexture(@NotNull TextureSpecifyLocation texture) {
        this.texture = texture;
    }
}
