package dev.felnull.otyacraftengine.client.gui.components.base;

import dev.felnull.otyacraftengine.client.gui.TextureSpecify;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public abstract class OEBaseImageWidget extends OEBaseWidget {
    @NotNull
    protected TextureSpecify texture;

    public OEBaseImageWidget(int x, int y, int width, int height, @NotNull Component message, @NotNull TextureSpecify texture) {
        this(x, y, width, height, null, message, texture);
    }

    public OEBaseImageWidget(int x, int y, int width, int height, @Nullable String widgetTypeName, @NotNull Component message, @NotNull TextureSpecify texture) {
        super(x, y, width, height, widgetTypeName, message);
        this.texture = texture;
    }

    public @NotNull TextureSpecify getTexture() {
        return texture;
    }

    public void setTexture(@NotNull TextureSpecify texture) {
        this.texture = texture;
    }
}
