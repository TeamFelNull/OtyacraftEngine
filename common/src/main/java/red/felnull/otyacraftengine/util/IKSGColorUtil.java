package red.felnull.otyacraftengine.util;

import net.minecraft.util.FastColor;

import java.awt.*;

public class IKSGColorUtil {
    public static int getRed(int color) {
        return (color >> 16) & 0xFF;
    }

    public static int getGreen(int color) {
        return (color >> 8) & 0xFF;
    }

    public static int getBlue(int color) {
        return color & 0xFF;
    }

    public static int getAlpha(int color) {
        return (color >> 24) & 0xFF;
    }

    public static int toSRGB(int colorCode) {
        Color c = new Color(colorCode);
        return FastColor.ARGB32.color(c.getAlpha(), c.getRed(), c.getGreen(), c.getBlue());
    }

    public static String toStrCode(int n) {
        String hex = Integer.toHexString(n);
        return "0".repeat(Math.max(0, 6 - hex.length())) + hex;
    }

}
