package dev.felnull.otyacraftengine.natives;

import dev.felnull.fnjl.os.OSs;
import dev.felnull.fnjln.FNNativesFileChooser;
import dev.felnull.fnjln.FNNativesFont;
import dev.felnull.fnjln.FNNativesSpecialFolder;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFWNativeWin32;

import java.io.File;
import java.nio.file.Path;

public class FNJLNativesWrapperOld {
    private static final Minecraft mc = Minecraft.getInstance();

    public static Path getFontFolder() {
        return FNNativesSpecialFolder.getFonts();
    }

    public static Path getMyPicturesFolder() {
        return FNNativesSpecialFolder.getMyPictures();
    }

    public static Path getMyVideoFolder() {
        return FNNativesSpecialFolder.getMyVideo();
    }

    public static Path getMyMusicFolder() {
        return FNNativesSpecialFolder.getMyMusic();
    }

    public static Path getDesktopFolder() {
        return FNNativesSpecialFolder.getDesktop();
    }

    public static boolean isSupportSpecialFolder() {
        return FNNativesSpecialFolder.isSupport();
    }

    public static boolean isSupportSystemFont() {
        return FNNativesFont.isSupport();
    }

    public static String getSystemFont() {
        return FNNativesFont.getSystemFontName();
    }

    public static boolean isSupportNativeFileChooser() {
        return FNNativesFileChooser.isSupport();
    }

    public static File[] openNativeFileChooser(String title, Path initDir, FileChooserFlagWrapperOld flagWrapper, FileChooserFilterWrapper... filters) {
        FNNativesFileChooser.Filter[] nfilters = null;
        if (filters != null) {
            nfilters = new FNNativesFileChooser.Filter[filters.length];
            for (int i = 0; i < filters.length; i++) {
                nfilters[i] = filters[i].filter;
            }
        }
        var nfc = new FNNativesFileChooser(title, flagWrapper != null ? flagWrapper.flag : null, nfilters);
        nfc.setInitialDirectory(initDir);
        if (OSs.isWindows())
            return nfc.openWindow(GLFWNativeWin32.glfwGetWin32Window(mc.getWindow().getWindow()));
        return nfc.openWindow();
    }

    public static class FileChooserFlagWrapperOld {
        private final FNNativesFileChooser.Flag flag = new FNNativesFileChooser.Flag();

        public FileChooserFlagWrapperOld allowMultiSelect(boolean allow) {
            this.flag.allowMultiSelect(allow);
            return this;
        }

        public FileChooserFlagWrapperOld explorer(boolean explorer) {
            this.flag.explorer(explorer);
            return this;
        }

        public FileChooserFlagWrapperOld creatEPrompt(boolean creatEPrompt) {
            this.flag.creatEPrompt(creatEPrompt);
            return this;
        }

        public FileChooserFlagWrapperOld fileMustExist(boolean fileMustExist) {
            this.flag.fileMustExist(fileMustExist);
            return this;
        }

        public FileChooserFlagWrapperOld hideReadOnly(boolean hideReadOnly) {
            this.flag.hideReadOnly(hideReadOnly);
            return this;
        }

        public FileChooserFlagWrapperOld nodeReferenceLinks(boolean nodeReferenceLinks) {
            this.flag.nodeReferenceLinks(nodeReferenceLinks);
            return this;
        }

        public FileChooserFlagWrapperOld readOnly(boolean readOnly) {
            this.flag.readOnly(readOnly);
            return this;
        }
    }

    public static class FileChooserFilterWrapper {
        private final FNNativesFileChooser.Filter filter;

        public FileChooserFilterWrapper(String description, String... extension) {
            this.filter = new FNNativesFileChooser.Filter(description, extension);
        }
    }
}