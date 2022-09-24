package dev.felnull.otyacraftengine.natives.fnjl;

import dev.felnull.otyacraftengine.natives.fnjl.impl.FNJLNativesWrapperImpl;

import java.io.File;
import java.nio.file.Path;

public interface FNJLNativesWrapper {
    static FNJLNativesWrapper getInstance() {
        var ins = FNJLNativesWrapperImpl.INSTANCE;
        ins.init();
        return ins;
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
