package red.felnull.otyacraftengine.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class IKSGDataUtil {

    public static InputStream zipGz(InputStream data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gos = new GZIPOutputStream(baos);
        gos.write(inputStreamToByteArray(data));
        gos.close();
        baos.close();
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public static InputStream unzipGz(InputStream data) throws IOException {
        return new GZIPInputStream(data);
    }

    public static byte[] inputStreamToByteArray(InputStream stream) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        while (true) {
            int len = stream.read(buffer);
            if (len < 0) {
                break;
            }
            bout.write(buffer, 0, len);
        }
        return bout.toByteArray();
    }
}
