package dev.felnull.otyacraftengine.integration;

import dev.architectury.platform.Platform;
import dev.felnull.otyacraftengine.impl.client.OEMODIntegrationClientExpectPlatform;
import net.minecraft.client.gui.screens.Screen;

import java.nio.file.Path;
import java.util.function.Function;

public class BetterTaskbarIntegration {
    public static boolean isModLoaded() {
        return Platform.isModLoaded("bettertaskbar");
    }

    public static Path getLibraryFolderPath() {
        return OEMODIntegrationClientExpectPlatform.getBTLibraryFolderPath();
    }

    public static boolean isSupport() {
        return OEMODIntegrationClientExpectPlatform.isBTSupport();
    }

    public static boolean setProgress(int comp, int total) {
        return OEMODIntegrationClientExpectPlatform.setBTProgress(comp, total);
    }

    public static boolean setProgress(double par) {
        return OEMODIntegrationClientExpectPlatform.setBTProgress(par);
    }

    public static boolean setState(State state) {
        return OEMODIntegrationClientExpectPlatform.setBTState(state);
    }

    public static void registerProgressScreenPar(Function<Screen, Double> progress) {
        OEMODIntegrationClientExpectPlatform.registerBTProgressScreenPar(progress);
    }

    public static void registerProgressScreen(Function<Screen, ProgressTotal> progress) {
        OEMODIntegrationClientExpectPlatform.registerBTProgressScreen(progress);
    }

    public static enum State {
        NO_PROGRESS,
        WAIT,
        ERROR,
        NOMAL,
        PAUSE;
    }

    public static record ProgressTotal(int complete, int total, State state) {
    }
}
