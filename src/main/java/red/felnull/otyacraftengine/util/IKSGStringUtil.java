package red.felnull.otyacraftengine.util;

import dev.felnull.fnjl.util.FNStringUtil;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.util.regex.Pattern;

public class IKSGStringUtil {
    @Deprecated
    public static String getIntArryString(int[] ints) {
        return StringUtils.join(ints, ":");
    }

    public static String getIntsToString(int[] ints) {
        char[] chars = new char[ints.length];
        for (int i = 0; i < ints.length; i++) {
            chars[i] = (char) ints[i];
        }
        return String.valueOf(chars);
    }

    @Deprecated
    public static String getTimeStamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.toString();
    }

    @Deprecated
    public static String slipString(String name, boolean left, int slipc) {
        String st = name;
        for (int c = 0; c < slipc; c++) {
            st = slipString(st, left);
        }
        return st;
    }

    @Deprecated
    public static String getExtension(String name) {
        return FNStringUtil.getExtension(name);
    }

    @Deprecated
    public static String deleteExtension(String name) {

        String[] filenames = name.split(Pattern.quote("."));

        if (filenames.length == 1)
            return name;

        return name.substring(0, name.length() - filenames[filenames.length - 1].length() - 1);
    }

    @Deprecated
    public static String slipString(String name, boolean left) {

        StringBuilder outst = new StringBuilder();

        char[] oc = name.toCharArray();
        if (left) {
            for (int c = 0; c < oc.length; c++) {

                if (c != oc.length - 1)
                    outst.append(oc[c + 1]);
                else
                    outst.append(oc[0]);
            }
        } else {
            for (int c = -1; c < oc.length; c++) {

                if (c == -1) {
                    outst.append(oc[oc.length - 1]);
                } else {
                    if (c != oc.length - 1)
                        outst.append(oc[c]);
                }
            }
        }
        return outst.toString();
    }

    @Deprecated
    public static String characterLimit(Minecraft mc, String st, int leth) {
        return characterLimit(mc, st, leth, true);

    }

    @Deprecated
    public static String characterLimit(Minecraft mc, String st, int leth, boolean isdot) {

        if (mc.fontRenderer.getStringWidth(st) <= leth)
            return st;

        int stle = leth - (isdot ? mc.fontRenderer.getStringWidth("...") : 0);

        String sto = "";

        char[] sc = st.toCharArray();

        for (char value : sc) {
            String stm = sto;
            sto += value;
            if (mc.fontRenderer.getStringWidth(sto) >= stle) {

                return isdot ? stm + "..." : stm;
            }
        }

        return sto;
    }

    @Deprecated
    public static String getByteDisplay(int bytec) {
        return FNStringUtil.getByteDisplay(bytec);
    }

    public static String deleteLastZero(float f) {

        if (String.valueOf(f).endsWith("0")) {

            if (String.valueOf(f).charAt(String.valueOf(f).length() - 2) == '.') {
                return String.valueOf(f).substring(0, String.valueOf(f).length() - 2);
            }

            return String.valueOf(f).substring(0, String.valueOf(f).length() - 1);
        }

        return String.valueOf(f);

    }

    @Deprecated
    public static String getPercentage(int all, int cont) {

        return Math.round(((float) cont / (float) all) * 100) + " %";
    }

    public static String getTimeDisplay(long milisec) {

        long byou = milisec / 1000;
        long hun = byou / 60;

        if (hun < 60) {

            return hun + ":" + getZeroOnNumber(2, (int) (byou - hun * 60));
        }

        long zikan = hun / 60;

        return zikan + ":" + getZeroOnNumber(2, (int) (hun - zikan * 60)) + ":"
                + getZeroOnNumber(2, (int) (byou - hun * 60));
    }

    public static String getZeroOnNumber(int zerocont, int num) {

        StringBuilder out = new StringBuilder();

        String numst = String.valueOf(num);

        for (int c = 0; c < zerocont - numst.length(); c++) {
            out.append("0");
        }

        out.append(numst);

        return out.toString();
    }

    public static String cutForBack(String text, int num) {
        return text.substring(0, text.length() - num);
    }

    public static String cutForFront(String text, int num) {
        return text.substring(num);
    }
}
