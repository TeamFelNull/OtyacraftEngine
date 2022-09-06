package dev.felnull.otyacraftengine.natives;

import dev.felnull.otyacraftengine.natives.impl.FNJLNativesWrapperImpl;

import java.io.File;
import java.nio.file.Path;

public interface FNJLNativesWrapper {
    static FNJLNativesWrapper getInstance() {
        return FNJLNativesWrapperImpl.INSTANCE;
    }

    Path getFontFolder();

    Path getMyPicturesFolder();

    Path getMyVideoFolder();

    Path getMyMusicFolder();

    Path getDesktopFolder();

    boolean isSupportSpecialFolder();

    boolean isSupportSystemFont();

    String getSystemFont();

    boolean isSupportNativeFileChooser();

    File[] openNativeFileChooser(String title, Path initDir, FNJLNFileChooserFlagWrapper flagWrapper, FNJLNFileChooserFilterWrapper... filters);
}
