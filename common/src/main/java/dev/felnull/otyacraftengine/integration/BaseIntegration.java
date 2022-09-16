package dev.felnull.otyacraftengine.integration;

import dev.architectury.platform.Platform;
import dev.felnull.otyacraftengine.util.OEDataGenUtils;

public abstract class BaseIntegration {
    abstract public String getModId();

    abstract public boolean isConfigEnabled();

    public boolean isEnable() {
        return Platform.isModLoaded(getModId()) && isConfigEnabled();
    }

    public boolean isEnableAddElement() {
        return OEDataGenUtils.isDataGenerating() || isEnable();
    }
}
