package dev.felnull.otyacraftengine.impl.client.forge;

import dev.felnull.otyacraftengine.integration.BetterTaskbarIntegration;
import net.minecraft.client.gui.screens.Screen;
import net.morimori0317.bettertaskbar.BetterTaskbarAPI;

import java.nio.file.Path;
import java.util.function.Function;

public class OEMODIntegrationClientExpectPlatformImpl {
    public static Path getBTLibraryFolderPath() {
        if (BetterTaskbarIntegration.isModLoaded())
            return BetterTaskbarAPI.getInstance().getLibraryFolderPath();
        return null;
    }

    public static boolean isBTSupport() {
        if (BetterTaskbarIntegration.isModLoaded())
            return BetterTaskbarAPI.getInstance().isSupport();
        return false;
    }

    public static boolean setBTProgress(int comp, int total) {
        if (BetterTaskbarIntegration.isModLoaded())
            return BetterTaskbarAPI.getInstance().setProgress(comp, total);
        return false;
    }

    public static boolean setBTProgress(double par) {
        if (BetterTaskbarIntegration.isModLoaded())
            return BetterTaskbarAPI.getInstance().setProgress(par);
        return false;
    }

    public static boolean setBTState(BetterTaskbarIntegration.State state) {
        if (BetterTaskbarIntegration.isModLoaded())
            return BetterTaskbarAPI.getInstance().setState(toBTState(state));
        return false;
    }

    public static void registerBTProgressScreenPar(Function<Screen, Double> progress) {
        if (BetterTaskbarIntegration.isModLoaded())
            BetterTaskbarAPI.getInstance().registerProgressScreenPar(progress);
    }

    public static void registerBTProgressScreen(Function<Screen, BetterTaskbarIntegration.ProgressTotal> progress) {
        if (BetterTaskbarIntegration.isModLoaded())
            BetterTaskbarAPI.getInstance().registerProgressScreen(n -> toBTProgressTotal(progress.apply(n)));
    }

    private static BetterTaskbarAPI.State toBTState(BetterTaskbarIntegration.State state) {
        return switch (state) {
            case NO_PROGRESS -> BetterTaskbarAPI.State.NO_PROGRESS;
            case ERROR -> BetterTaskbarAPI.State.ERROR;
            case NOMAL -> BetterTaskbarAPI.State.NOMAL;
            case PAUSE -> BetterTaskbarAPI.State.PAUSE;
            case WAIT -> BetterTaskbarAPI.State.WAIT;
        };
    }

    private static BetterTaskbarAPI.ProgressTotal toBTProgressTotal(BetterTaskbarIntegration.ProgressTotal progressTotal) {
        return new BetterTaskbarAPI.ProgressTotal(progressTotal.complete(), progressTotal.total(), toBTState(progressTotal.state()));
    }
}
