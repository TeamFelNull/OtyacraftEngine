package dev.felnull.otyacraftengine.util;

import dev.felnull.fnjl.os.OSs;
import dev.felnull.fnjln.FNNativeFileChooser;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFWNativeWin32;

import java.io.File;
import java.nio.file.Path;

public class FNJLNativeWrapper {
    private static final Minecraft mc = Minecraft.getInstance();

    public static boolean isSupportNativeFileChooser() {
        return FNNativeFileChooser.isSupport();
    }

    public static File[] openNativeFileChooser(String title, Path initDir, FileChooserFlagWrapper flagWrapper, FileChooserFilterWrapper... filters) {
        FNNativeFileChooser.Filter[] nfilters = null;
        if (filters != null) {
            nfilters = new FNNativeFileChooser.Filter[filters.length];
            for (int i = 0; i < filters.length; i++) {
                nfilters[i] = filters[i].filter;
            }
        }
        var nfc = new FNNativeFileChooser(title, flagWrapper != null ? flagWrapper.flag : null, nfilters);
        nfc.setInitialDirectory(initDir);
        if (OSs.isWindows())
            return nfc.openWindow(GLFWNativeWin32.glfwGetWin32Window(mc.getWindow().getWindow()));
        return nfc.openWindow();
    }

    public static class FileChooserFlagWrapper {
        private final FNNativeFileChooser.Flag flag = new FNNativeFileChooser.Flag();

        public FileChooserFlagWrapper allowMultiSelect(boolean allow) {
            this.flag.allowMultiSelect(allow);
            return this;
        }

        public FileChooserFlagWrapper explorer(boolean explorer) {
            this.flag.explorer(explorer);
            return this;
        }

        public FileChooserFlagWrapper creatEPrompt(boolean creatEPrompt) {
            this.flag.creatEPrompt(creatEPrompt);
            return this;
        }

        public FileChooserFlagWrapper fileMustExist(boolean fileMustExist) {
            this.flag.fileMustExist(fileMustExist);
            return this;
        }

        public FileChooserFlagWrapper hideReadOnly(boolean hideReadOnly) {
            this.flag.hideReadOnly(hideReadOnly);
            return this;
        }

        public FileChooserFlagWrapper nodeReferenceLinks(boolean nodeReferenceLinks) {
            this.flag.nodeReferenceLinks(nodeReferenceLinks);
            return this;
        }

        public FileChooserFlagWrapper readOnly(boolean readOnly) {
            this.flag.readOnly(readOnly);
            return this;
        }
    }

    public static class FileChooserFilterWrapper {
        private final FNNativeFileChooser.Filter filter;

        public FileChooserFilterWrapper(String description, String... extension) {
            this.filter = new FNNativeFileChooser.Filter(description, extension);
        }
    }
}