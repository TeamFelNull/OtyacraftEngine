package dev.felnull.otyacraftengine.client.debug;

public class OtyacraftEngineClientDebug {
    private static final OtyacraftEngineClientDebug INSTANCE = new OtyacraftEngineClientDebug();

    public static OtyacraftEngineClientDebug getInstance() {
        return INSTANCE;
    }

    public boolean isShowTagInTooltip() {
        return true;
    }

    public boolean isShowModNameInTooltip() {
        return true;
    }
}
