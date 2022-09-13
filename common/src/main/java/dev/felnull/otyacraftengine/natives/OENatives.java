package dev.felnull.otyacraftengine.natives;

import dev.felnull.otyacraftengine.natives.impl.OENativesImpl;

import java.nio.file.Path;

public interface OENatives {
    static OENatives getInstance() {
        return OENativesImpl.INSTANCE;
    }

    Path getFontFolder();

    Path getMyPicturesFolder();

    Path getMyVideoFolder();

    Path getMyMusicFolder();

    Path getDesktopFolder();

    boolean isSupportSpecialFolder();

    String getSystemFont();

    boolean isSupportSystemFont();
}
