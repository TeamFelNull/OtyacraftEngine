package red.felnull.otyacraftengine.impl;

import me.shedaniel.architectury.ExpectPlatform;
import red.felnull.otyacraftengine.api.IOEIntegration;

import java.util.List;

public class OEExpectPlatform {
    @ExpectPlatform
    public static List<IOEIntegration> getIntegrations() {
        throw new AssertionError();
    }
}
