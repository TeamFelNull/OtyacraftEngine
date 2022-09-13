package dev.felnull.otyacraftengine.natives.impl;

import dev.felnull.otyacraftengine.natives.OENatives;
import dev.felnull.otyacraftengine.natives.fnjl.FNJLNativesWrapper;

import java.nio.file.Path;

public class OENativesImpl implements OENatives {
    public static final OENatives INSTANCE = new OENativesImpl();

    @Override
    public Path getFontFolder() {
        return FNJLNativesWrapper.getInstance().getFontFolder();
    }

    @Override
    public Path getMyPicturesFolder() {
        return FNJLNativesWrapper.getInstance().getMyPicturesFolder();
    }

    @Override
    public Path getMyVideoFolder() {
        return FNJLNativesWrapper.getInstance().getMyVideoFolder();
    }

    @Override
    public Path getMyMusicFolder() {
        return FNJLNativesWrapper.getInstance().getMyMusicFolder();
    }

    @Override
    public Path getDesktopFolder() {
        return FNJLNativesWrapper.getInstance().getDesktopFolder();
    }

    @Override
    public boolean isSupportSpecialFolder() {
        return FNJLNativesWrapper.getInstance().isSupportSpecialFolder();
    }

    @Override
    public String getSystemFont() {
        return FNJLNativesWrapper.getInstance().getSystemFont();
    }

    @Override
    public boolean isSupportSystemFont() {
        return FNJLNativesWrapper.getInstance().isSupportSystemFont();
    }

}
