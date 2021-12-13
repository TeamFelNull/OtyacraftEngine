package dev.felnull.otyacraftengine.impl.client;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.felnull.otyacraftengine.integration.BetterTaskbarIntegration;
import net.minecraft.client.gui.screens.Screen;

import java.nio.file.Path;
import java.util.function.Function;

public class OEMODIntegrationClientExpectPlatform {
    @ExpectPlatform
    public static Path getBTLibraryFolderPath() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isBTSupport() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean setBTProgress(int comp, int total) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean setBTProgress(double par) {
        return false;
    }

    @ExpectPlatform
    public static boolean setBTState(BetterTaskbarIntegration.State state) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerBTProgressScreenPar(Function<Screen, Double> progress) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerBTProgressScreen(Function<Screen, BetterTaskbarIntegration.ProgressTotal> progress) {
        throw new AssertionError();
    }
}
