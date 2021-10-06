package red.felnull.otyacraftengine.util;

import dev.felnull.fnjl.util.FNColorUtil;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
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
        return FNColorUtil.getApproximateColorObject(n, TextFormattingColors, TextFormatting::getColor);
    }

    @Deprecated
    public static TextFormatting convertTextFormattingFromColorCode(String hex) {
        return convertTextFormattingFromColorCode(Integer.parseInt(hex, 16));
    }

    public static int[] convertRGBFromColorCode(int n) {
        int[] rgb = new int[3];
        rgb[0] = FNColorUtil.getRed(n);
        rgb[1] = FNColorUtil.getGreen(n);
        rgb[2] = FNColorUtil.getBlue(n);
        return rgb;
    }

    @Deprecated
    public static int[] convertRGBFromColorCode(String hex) {
        return convertRGBFromColorCode(Integer.parseInt(hex, 16));
    }

    @Deprecated
    public static int difference(String color1, String color2) {
        return (int) FNColorUtil.getColorDistance(Integer.parseInt(color1, 16), Integer.parseInt(color2, 16));
    }

    @Deprecated
    public static String convertColorCodeFromRGB(int r, int g, int b) {
        return FNColorUtil.getStringHexColor(FNColorUtil.getHexColor(r, g, b));
    }

    public static String average(String... color) {
        List<int[]> clorcord = new ArrayList<>();
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
