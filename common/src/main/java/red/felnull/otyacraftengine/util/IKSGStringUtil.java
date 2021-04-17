package red.felnull.otyacraftengine.util;

import java.util.UUID;

public class IKSGStringUtil {
    private static final UUID NONE_UUID = UUID.fromString("552c4109-2565-bf5f-db3a-d3e749d57e54");

    public static String decodeUTFEscapeSequence(String unicode) {
        String[] codeStrs = unicode.split("\\\\u");
        int[] codePoints = new int[codeStrs.length - 1];
        for (int i = 0; i < codePoints.length; i++) {
            codePoints[i] = Integer.parseInt(codeStrs[i + 1], 16);
        }
        return new String(codePoints, 0, codePoints.length);
    }

    public static String encodeUTFEscapeSequence(String original) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < original.length(); i++) {
            sb.append(String.format("\\u%04X", Character.codePointAt(original, i)));
        }
        return sb.toString();
    }

    public static UUID fromString(String stUUID) {
        try {
            return UUID.fromString(stUUID);
        } catch (Exception ex) {
            return NONE_UUID;
        }
    }

    public static UUID getNoneUUID() {
        return NONE_UUID;
    }
}
