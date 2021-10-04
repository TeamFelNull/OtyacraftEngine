package red.felnull.otyacraftengine.util;

import dev.felnull.fnjl.util.FNColorUtil;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class IKSGColorUtil {
    public static TextFormatting[] TextFormattingColors = {
            TextFormatting.BLACK,
            TextFormatting.DARK_BLUE,
            TextFormatting.DARK_GREEN,
            TextFormatting.DARK_AQUA,
            TextFormatting.DARK_RED,
            TextFormatting.DARK_PURPLE,
            TextFormatting.GOLD,
            TextFormatting.GRAY,
            TextFormatting.DARK_GRAY,
            TextFormatting.BLUE,
            TextFormatting.GREEN,
            TextFormatting.AQUA,
            TextFormatting.RED,
            TextFormatting.LIGHT_PURPLE,
            TextFormatting.YELLOW,
            TextFormatting.WHITE};

    @Deprecated
    public static String convertIntegerFromColorCode(int n) {
        return FNColorUtil.getStringHexColor(n);
    }

    public static TextFormatting convertTextFormattingFromColorCode(int n) {
        int[] cols = new int[TextFormattingColors.length];
        for (int i = 0; i < TextFormattingColors.length; i++) {
            cols[i] = TextFormattingColors[i].getColor();
        }
        int col = FNColorUtil.getApproximateColor(n, cols);
        return TextFormatting.AQUA;
    }

    public static TextFormatting convertTextFormattingFromColorCode(String hex) {
        Comparator<TextFormatting> compiler = Comparator.comparingInt(n -> difference(convertIntegerFromColorCode(n.getColor()), hex));
        return Arrays.stream(TextFormattingColors).min(compiler).get();
    }

    public static int[] convertRGBFromColorCode(String hex) {
        char[] ch = hex.toCharArray();
        char[] r = {ch[0], ch[1]};
        char[] g = {ch[2], ch[3]};
        char[] b = {ch[4], ch[5]};
        int[] rgb = new int[3];
        rgb[0] = Integer.parseInt(new String(r), 16);
        rgb[1] = Integer.parseInt(new String(g), 16);
        rgb[2] = Integer.parseInt(new String(b), 16);
        return rgb;
    }

    public static int difference(String color1, String color2) {
        int[] rgb1 = convertRGBFromColorCode(color1);
        int[] rgb2 = convertRGBFromColorCode(color2);
        int rd = IKSGMath.differenceInt(rgb1[0], rgb2[0]);
        int gd = IKSGMath.differenceInt(rgb1[1], rgb2[1]);
        int bd = IKSGMath.differenceInt(rgb1[2], rgb2[2]);
        return IKSGMath.averageInt(rd, gd, bd);
    }

    public static int[] convertRGBFromColorCode(int n) {
        String hex = convertIntegerFromColorCode(n);
        return convertRGBFromColorCode(hex);
    }

    public static String convertColorCodeFromRGB(int r, int g, int b) {

        String hex = "";
        if (convertIntegerFromColorCode(r).toCharArray().length != 1)
            hex += String.valueOf(convertIntegerFromColorCode(r).toCharArray());
        else
            hex += "0" + String.valueOf(convertIntegerFromColorCode(r).toCharArray());

        if (convertIntegerFromColorCode(g).toCharArray().length != 1)
            hex += String.valueOf(convertIntegerFromColorCode(g).toCharArray());
        else
            hex += "0" + String.valueOf(convertIntegerFromColorCode(g).toCharArray());

        if (convertIntegerFromColorCode(b).toCharArray().length != 1)
            hex += String.valueOf(convertIntegerFromColorCode(b).toCharArray());
        else
            hex += "0" + String.valueOf(convertIntegerFromColorCode(b).toCharArray());

        return hex;
    }

    public static String average(String... color) {
        List<int[]> clorcord = new ArrayList<int[]>();
        for (String cl : color) {
            clorcord.add(convertRGBFromColorCode(cl));
        }
        int ar = 0;
        int ag = 0;
        int ab = 0;
        for (int[] r : clorcord) {
            ar += r[0];
        }
        ar /= clorcord.size();

        for (int[] g : clorcord) {
            ag += g[1];
        }
        ag /= clorcord.size();

        for (int[] b : clorcord) {
            ab += b[2];
        }
        ab /= clorcord.size();

        return convertColorCodeFromRGB(ar, ag, ab);
    }
}
