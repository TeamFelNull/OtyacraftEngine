package dev.felnull.otyacraftengine.util;

import dev.felnull.otyacraftengine.OtyacraftEngine;

import java.nio.file.Path;
import java.nio.file.Paths;

public class OEPaths {
    public static Path getClientOEFolderPath() {
        return Paths.get(OtyacraftEngine.MODID);
    }
}
