package dev.felnull.otyacraftengine.client.renderer.texture;

import net.minecraft.util.ProgressListener;

public interface TextureLoadProgress {

    int getTotal();

    int getComplete();

    default float getParent() {
        return (float) getComplete() / (float) getTotal();
    }

    String getStateName();
}
