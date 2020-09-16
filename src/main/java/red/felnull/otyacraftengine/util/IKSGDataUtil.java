package red.felnull.otyacraftengine.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class IKSGDataUtil {
    public static byte[] gzZipping(byte[] data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gos = new GZIPOutputStream(baos);
        gos.write(data);
        gos.close();
        baos.close();
        return baos.toByteArray();
    }

    public static byte[] gzUnZipping(byte[] data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        GZIPInputStream gis = new GZIPInputStream(bais);
        int lin;
        byte[] bff = new byte[1024];
        while ((lin = gis.read(bff)) > 0) {
            baos.write(bff, 0, lin);
        }
        gis.close();
        bais.close();
        baos.close();
        return baos.toByteArray();
    }
}
