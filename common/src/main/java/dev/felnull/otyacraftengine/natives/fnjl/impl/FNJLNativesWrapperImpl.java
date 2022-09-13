package dev.felnull.otyacraftengine.natives.fnjl.impl;

import dev.felnull.fnjl.os.OSs;
import dev.felnull.fnjl.util.FNDataUtil;
import dev.felnull.fnjln.FNNativesFileChooser;
import dev.felnull.fnjln.FNNativesFont;
import dev.felnull.fnjln.FNNativesSpecialFolder;
import dev.felnull.fnjln.FelNullJavaLibraryNatives;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.natives.fnjl.FNJLNFileChooserFilterWrapper;
import dev.felnull.otyacraftengine.natives.fnjl.FNJLNFileChooserFlagWrapper;
import dev.felnull.otyacraftengine.natives.fnjl.FNJLNativesWrapper;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFWNativeWin32;

import java.io.File;
import java.nio.file.Path;

public class FNJLNativesWrapperImpl implements FNJLNativesWrapper {
    public static final FNJLNativesWrapper INSTANCE = new FNJLNativesWrapperImpl();

    public static void init() {
        var file = new File(OtyacraftEngine.MODID, "fnjln");
        FNDataUtil.wishMkdir(file);
        FelNullJavaLibraryNatives.init(file.toPath());
    }

    @Override
    public Path getFontFolder() {
        return FNNativesSpecialFolder.getFonts();
    }

    @Override
    public Path getMyPicturesFolder() {
        return FNNativesSpecialFolder.getMyPictures();
    }

    @Override
    public Path getMyVideoFolder() {
        return FNNativesSpecialFolder.getMyVideo();
    }

    @Override
    public Path getMyMusicFolder() {
        return FNNativesSpecialFolder.getMyMusic();
    }

    @Override
    public Path getDesktopFolder() {
        return FNNativesSpecialFolder.getDesktop();
    }

    @Override
    public boolean isSupportSpecialFolder() {
        return FNNativesSpecialFolder.isSupport();
    }

    @Override
    public boolean isSupportSystemFont() {
        return FNNativesFont.isSupport();
    }

    @Override
    public String getSystemFont() {
        return FNNativesFont.getSystemFontName();
    }

    @Override
    public boolean isSupportNativeFileChooser() {
        return FNNativesFileChooser.isSupport();
    }

    @Override
    public File[] openNativeFileChooser(String title, Path initDir, FNJLNFileChooserFlagWrapper flagWrapper, FNJLNFileChooserFilterWrapper... filters) {
        FNNativesFileChooser.Filter[] nfilters = null;
        if (filters != null) {
            nfilters = new FNNativesFileChooser.Filter[filters.length];
            for (int i = 0; i < filters.length; i++) {
                nfilters[i] = new FNNativesFileChooser.Filter(filters[i].getDescription(), filters[i].getExtension());
            }
        }
        var nfc = new FNNativesFileChooser(title, flagWrapper != null ? convert(flagWrapper) : null, nfilters);
        nfc.setInitialDirectory(initDir);
        if (OSs.isWindows())
            return nfc.openWindow(GLFWNativeWin32.glfwGetWin32Window(Minecraft.getInstance().getWindow().getWindow()));
        return nfc.openWindow();
    }

    private FNNativesFileChooser.Flag convert(FNJLNFileChooserFlagWrapper flagWrapper) {
        var n = new FNNativesFileChooser.Flag();
        n.creatEPrompt(flagWrapper.isCreatEPrompt());
        n.explorer(flagWrapper.isExplorer());
        n.fileMustExist(flagWrapper.isFileMustExist());
        n.hideReadOnly(flagWrapper.isHideReadOnly());
        n.allowMultiSelect(flagWrapper.isMultiSelect());
        n.nodeReferenceLinks(flagWrapper.isNodeReferenceLinks());
        n.readOnly(flagWrapper.isReadOnly());
        return n;
    }
}
