package dev.felnull.otyacraftengine.explatform;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class OEDataGenExpectPlatform {
    @ExpectPlatform
    public static boolean isDataGenerating() {
        throw new AssertionError();
    }
}
