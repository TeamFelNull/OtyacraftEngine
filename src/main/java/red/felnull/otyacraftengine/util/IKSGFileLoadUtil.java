package red.felnull.otyacraftengine.util;

import dev.felnull.fnjl.util.FNDataUtil;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import red.felnull.otyacraftengine.OtyacraftEngine;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class IKSGFileLoadUtil {
    @Deprecated
    public static void createFolder(Path path) {
        if (path == null)
            return;
        path.toFile().mkdirs();
    }

    @Deprecated
    public static byte[] fileBytesReader(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            OtyacraftEngine.LOGGER.error("Failed to Read the File : " + e.getLocalizedMessage());
            return null;
        }
    }

    @Deprecated
    public static boolean deleteFile(Path path) {
        return deleteFile(path.toFile());
    }

    @Deprecated
    public static boolean deleteFile(File file) {
        return file.delete();
    }

    public static void fileNBTWriter(CompoundNBT nbt, Path path) {
        createFolder(path.getParent());
        try {
            FileOutputStream stream = new FileOutputStream(path.toFile());
            CompressedStreamTools.writeCompressed(nbt, stream);
        } catch (IOException e) {
            OtyacraftEngine.LOGGER.error("Failed to Write the NBT File : " + e.getLocalizedMessage());
        }
    }

    public static CompoundNBT fileNBTReader(Path path) {
        FileInputStream stream;

        if (!path.toFile().exists())
            return new CompoundNBT();

        try {
            stream = new FileInputStream(path.toFile());
            return CompressedStreamTools.readCompressed(stream);
        } catch (IOException e) {
            OtyacraftEngine.LOGGER.error("Failed to Read the NBT File : " + e.getLocalizedMessage());
        }
        return null;
    }

    public static void txtWriter(Map<String, String> map, Path path) {
        createFolder(path.getParent());
        try {
            FileWriter fw = new FileWriter(path.toString(), false);
            PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
            map.forEach((key, value) -> pw.println(key + ":" + value));

            pw.close();
        } catch (IOException e) {
            OtyacraftEngine.LOGGER.error("Failed to Write the Txt File : " + e.getLocalizedMessage());
        }
    }

    public static void txtWriter(String text, Path path) {
        createFolder(path.getParent());
        try {
            FileWriter fw = new FileWriter(path.toString(), false);
            PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
            pw.println(text);
            pw.close();
        } catch (IOException e) {
            OtyacraftEngine.LOGGER.error("Failed to Write the Txt File : " + e.getLocalizedMessage());
        }
    }

    public static void txtReader(Map<String, String> map, Path path) {
        map.clear();
        try {
            FileReader re = new FileReader(path.toString());
            BufferedReader bre = new BufferedReader(re);
            String st;
            while ((st = bre.readLine()) != null) {
                try {
                    String[] fruit = st.split(":", 0);
                    map.put(fruit[0], fruit[1]);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (IOException e) {
            OtyacraftEngine.LOGGER.error("Failed to Read the Txt File : " + e.getLocalizedMessage());
        }
    }

    @Deprecated
    public static byte[] getCheckSumByte(File file) throws IOException, NoSuchAlgorithmException {
        InputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;
        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);
        fis.close();
        return complete.digest();
    }

    public static int getCheckSum(File file) throws IOException, NoSuchAlgorithmException {
        return ByteBuffer.wrap(getCheckSumByte(file)).getInt();
    }

    @Deprecated
    public static void fileBytesWriter(byte[] bytedatas, Path path) {
        createFolder(path.getParent());
        try {
            Files.write(path, bytedatas);
        } catch (IOException e) {
            OtyacraftEngine.LOGGER.error("Failed to Write the File : " + e.getLocalizedMessage());
        }
    }

    @Deprecated
    public static void fileCopyWriter(Path src, Path target, CopyOption... options) throws IOException {
        Files.copy(src, target, options);
    }

    @Deprecated
    public static void fileURLWriter(URL url, Path target, CopyOption... options) throws IOException {
        fileInputStreamWriter(url.openStream(), target, options);
    }

    @Deprecated
    public static void fileInputStreamWriter(InputStream stream, Path target, CopyOption... options) throws IOException {
        Files.copy(new BufferedInputStream(stream), target, options);
    }

    @Deprecated
    public static void fileBytesWriterProgress(byte[] data, Path target, IProgressListener listener) throws IOException {
        fileInputStreamWriterProgress(new ByteArrayInputStream(data), target, data.length, listener);
    }

    @Deprecated
    public static void fileCopyWriterProgress(Path src, Path target, IProgressListener listener) throws IOException {
        FNDataUtil.fileCopyToProgress(src.toFile(), target.toFile(), n -> listener.progressListener((float) n.getProgress()));
    }

    @Deprecated
    public static void fileURLWriterProgress(URL url, Path target, IProgressListener listener) throws IOException {
        FNDataUtil.fileDownloadToProgress(url, target.toFile(), n -> listener.progressListener((float) n.getProgress()));
    }

    @Deprecated
    public static void fileInputStreamWriterProgress(InputStream streama, Path target, long size, IProgressListener listener) throws IOException {
        FNDataUtil.fileWriteToProgress(streama, size, target.toFile(), writeProgressListener -> listener.progressListener((float) writeProgressListener.getProgress()));
    }

    @Deprecated
    public interface IProgressListener {
        void progressListener(float progress);
    }
}
