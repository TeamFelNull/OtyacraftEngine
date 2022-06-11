package dev.felnull.otyacraftengine.debug;

public class OtyacraftEngineDebug {
    private static final OtyacraftEngineDebug INSTANCE = new OtyacraftEngineDebug();

    public static OtyacraftEngineDebug getInstance() {
        return INSTANCE;
    }

    public boolean isShowTagInTooltip() {
        return true;
    }

    public boolean isShowModNameInTooltip() {
        return true;
    }
}
